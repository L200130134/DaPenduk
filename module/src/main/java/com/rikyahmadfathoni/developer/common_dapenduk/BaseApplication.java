package com.rikyahmadfathoni.developer.common_dapenduk;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.rikyahmadfathoni.developer.common_dapenduk.network.ApiModule;
import com.rikyahmadfathoni.developer.common_dapenduk.network.AppComponent;
import com.rikyahmadfathoni.developer.common_dapenduk.network.DaggerAppComponent;

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return instance.getResources();
    }

    public RequestManager mRequestManager;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule()).build();

        mRequestManager = Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .circleCrop());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

}
