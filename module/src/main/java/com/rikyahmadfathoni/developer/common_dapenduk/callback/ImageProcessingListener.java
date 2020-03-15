package com.rikyahmadfathoni.developer.common_dapenduk.callback;

import android.graphics.Bitmap;

public interface ImageProcessingListener {

    void onCompleted(String imagePath, Bitmap bitmap, Exception e);
}
