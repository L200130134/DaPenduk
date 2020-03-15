package com.rikyahmadfathoni.developer.common_dapenduk.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity {

    protected D binding;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.onCreateActivity(savedInstanceState);

        if (isAuthValid()) {
            setIntentExtra(getIntent());
            this.binding = DataBindingUtil.setContentView(this, getLayoutRes());
            this.onCreateView(savedInstanceState);
        } else {
            startAuthActivity();
        }
    }

    protected abstract void onCreateActivity(Bundle savedInstanceState);

    protected abstract boolean isAuthValid();

    protected abstract void setIntentExtra(Intent intent);

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract void startAuthActivity();
}