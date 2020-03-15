package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.rikyahmadfathoni.developer.common_dapenduk.R;

public class UtilsDialog {

    private static final String LOG = "LOG : ";

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showSnackbar(View view, String message, String buttonText,
                                    @ColorRes Integer textColor, View.OnClickListener onClickListener) {
        try {
            if (view != null && !UtilsString.isEmpty(message)) {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction(buttonText, onClickListener)
                        .setActionTextColor(ContextCompat.getColor(view.getContext(),
                                textColor != null ? textColor : R.color.colorAccent))
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSnackbar(View view, String message) {

        if (!UtilsString.isEmpty(message)) {
            showSnackbar(view, message, null, R.color.colorAccent, null);
        }
    }


    public static void showLog(Class mClass, String message) {
        showLog(mClass.getSimpleName(), message, null);
    }

    public static void showLog(String TAG, String message, String throwable) {

        if (throwable != null) {
            Log.d(LOG + TAG, message, new Throwable(throwable));
        } else {
            Log.d(LOG + TAG, message);
        }
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      String title, String message) {
        return ProgressDialog.show(context,
                title, message, false, false);
    }
}
