package com.rikyahmadfathoni.developer.common_dapenduk.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.provider.ViewModelFactory;
import com.rikyahmadfathoni.developer.common_dapenduk.viewmodel.ApiRequestViewModel;

import javax.inject.Inject;

public abstract class BaseLoginActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    protected ApiRequestViewModel apiRequestViewModel;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        ((BaseApplication) getApplication()).getAppComponent().doLoginInjection(this);

        apiRequestViewModel = new ViewModelProvider(this, viewModelFactory).get(ApiRequestViewModel.class);
    }

    @Override
    protected boolean isAuthValid() {
        return true;
    }

    @Override
    protected void setIntentExtra(Intent intent) {

    }

    @Override
    protected final void startAuthActivity() {

    }
}
