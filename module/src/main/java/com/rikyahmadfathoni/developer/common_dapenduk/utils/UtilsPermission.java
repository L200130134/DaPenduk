package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public final class UtilsPermission {

    public static boolean hasPermission(Context context, final String permission) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && PackageManager.PERMISSION_GRANTED == ActivityCompat
                .checkSelfPermission(context, permission);
    }

    public static boolean hasPermission(Context context, final String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermission(Activity activity, String permission, int reqCode) {
        final String[] permissions = new String[]{permission};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, permissions, reqCode);
        } else {
            //denied access
            ActivityCompat.requestPermissions(activity, permissions, reqCode);
        }
    }

    public void requestPermission(Activity activity, String[] permissions, int reqCode) {
        if (!hasPermission(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, reqCode);
        }
    }
}