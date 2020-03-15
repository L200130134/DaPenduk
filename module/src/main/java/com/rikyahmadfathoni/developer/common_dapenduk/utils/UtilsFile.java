package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilsFile {

    public static String getPath(Uri uri) {

        try {
            Context context = BaseApplication.getInstance();

            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                String documentId = cursor.getString(0);
                documentId = documentId.substring(documentId.lastIndexOf(":") + 1);

                cursor.close();

                cursor = context.getContentResolver().query(
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null, MediaStore.Images.Media._ID + " = ? ", new String[]{documentId}, null);

                if (cursor != null) {

                    cursor.moveToFirst();
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();

                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getImagePath(Bitmap bitmap) {

        try {

            File outputDir = BaseApplication.getInstance().getCacheDir();

            File outputFile = File.createTempFile("prefix", ".jpg", outputDir);

            FileOutputStream out = new FileOutputStream(outputFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Uri getImageUri(Bitmap inImage) {

        String path = MediaStore.Images.Media.insertImage(
                BaseApplication.getInstance().getContentResolver(), inImage,
                "Title", null);

        return Uri.parse(path);
    }
}
