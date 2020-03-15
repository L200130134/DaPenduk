package com.rikyahmadfathoni.developer.common_dapenduk.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.rikyahmadfathoni.developer.common_dapenduk.callback.ImageProcessingListener;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsBitmap;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsFile;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ImageProcessingTask {

    private ThreadPoolExecutor threadPoolExecutor;
    private ImageProcessingListener processingListener;
    private Uri imageUri;
    private File imageFile;
    private Bitmap bitmap;
    private int imageMaxSize;

    public ImageProcessingTask(ImageProcessingListener processingListener) {
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.processingListener = processingListener;
    }

    public ImageProcessingTask setImageUri(Uri imageUri, int imageMaxSize) {
        this.imageUri = imageUri;
        this.imageMaxSize = imageMaxSize;
        return this;
    }

    public ImageProcessingTask setImageFile(File imageFile, int imageMaxSize) {
        this.imageFile = imageFile;
        this.imageMaxSize = imageMaxSize;
        return this;
    }

    public ImageProcessingTask setBitmap(Bitmap bitmap, int imageMaxSize) {
        this.bitmap = bitmap;
        this.imageMaxSize = imageMaxSize;
        return this;
    }

    public void execute() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.submit(new RunTask(this));
        }
    }

    private static class RunTask implements Runnable {

        private ImageProcessingTask imageProcessingTask;

        RunTask(ImageProcessingTask imageProcessingTask) {
            this.imageProcessingTask = imageProcessingTask;
        }

        @Override
        public void run() {

            try {
                Bitmap bitmap = null;

                if (imageProcessingTask.imageFile != null) {
                    bitmap = BitmapFactory.decodeFile(imageProcessingTask.imageFile.getAbsolutePath());
                } else if (imageProcessingTask.bitmap != null) {
                    bitmap = imageProcessingTask.bitmap;
                } else if (imageProcessingTask.imageUri != null) {
                    bitmap = BitmapFactory.decodeFile(UtilsFile.getPath(imageProcessingTask.imageUri));
                }

                if (bitmap != null) {
                    Bitmap resizedBitmap = UtilsBitmap.getResizedBitmap(
                            bitmap, imageProcessingTask.imageMaxSize);

                    String outPath = UtilsFile.getImagePath(resizedBitmap);

                    if (outPath != null) {

                        if (imageProcessingTask.processingListener != null) {
                            imageProcessingTask.processingListener
                                    .onCompleted(outPath, resizedBitmap, null);
                        }
                        return;
                    }

                    if (imageProcessingTask.processingListener != null) {
                        imageProcessingTask.processingListener
                                .onCompleted(null, resizedBitmap, new Exception("File out path is null."));
                    }

                    return;
                }
                if (imageProcessingTask.processingListener != null) {
                    imageProcessingTask.processingListener
                            .onCompleted(null, null, new Exception("bitmap null object."));
                }
            } catch (Exception e) {
                if (imageProcessingTask.processingListener != null) {
                    imageProcessingTask.processingListener
                            .onCompleted(null, null, e);
                }
                e.printStackTrace();
            }
        }
    }
}
