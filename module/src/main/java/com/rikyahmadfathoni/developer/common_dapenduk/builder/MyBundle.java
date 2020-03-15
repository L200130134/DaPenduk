package com.rikyahmadfathoni.developer.common_dapenduk.builder;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyBundle {

    private Bundle bundle;

    public static MyBundle Builder() {
        return new MyBundle(new Bundle());
    }

    private MyBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public MyBundle putString(@Nullable String key, @Nullable String value) {
        bundle.putString(key, value);
        return this;
    }

    public MyBundle putBoolean(@Nullable String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public MyBundle putInt(@Nullable String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public MyBundle putParcelable(@Nullable String key, @Nullable Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public MyBundle putParcelableArrayList(@Nullable String key,
                                           @Nullable ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    public Bundle Build() {
        return bundle;
    }
}
