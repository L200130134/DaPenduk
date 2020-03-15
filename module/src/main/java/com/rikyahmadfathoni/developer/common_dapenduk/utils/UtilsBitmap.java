package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;

public class UtilsBitmap {

    public static Bitmap getBitmap(Uri imageUri) {
        try {
            return MediaStore.Images.Media.getBitmap(
                    BaseApplication.getInstance().getContentResolver(), imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width > maxSize || height > maxSize) {

            float bitmapRatio = (float) width / (float) height;

            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
