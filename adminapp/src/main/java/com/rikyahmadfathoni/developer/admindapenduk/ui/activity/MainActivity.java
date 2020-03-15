package com.rikyahmadfathoni.developer.admindapenduk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import com.rikyahmadfathoni.developer.admindapenduk.R;
import com.rikyahmadfathoni.developer.admindapenduk.ui.fragment.DetailsFragment;
import com.rikyahmadfathoni.developer.admindapenduk.viewmodel.LoginViewModel;
import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.builder.MyBundle;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseMainActivity;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;

public class MainActivity extends BaseMainActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;

    private String fragmentMode;

    public DataPendudukModel dataPendudukModel;

    @Override
    protected void initView() {
        initViewModel();
        initLoginListener();

        binding.fab.show();
        binding.fab.setOnClickListener(this);
    }

    private void initLoginListener() {
        apiRequestViewModel.getLoginResponseLive().observe(this, loginResponse -> {
            if (loginResponse != null) {
                if (loginResponse.isError() && loginResponse.isCompleted()) {
                    startAuthActivity();
                }
            }
        });
    }

    @Override
    protected boolean isAuthValid() {
        initViewModel();
        return apiRequestViewModel.isLoggedIn();
    }

    @Override
    protected void startAuthActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void initViewModel() {
        if (loginViewModel == null) {
            loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        }
    }

    @Override
    protected void onDataClicked(DataPendudukModel dataPendudukModel, int position) {
        addFragmentDetail(dataPendudukModel, Constants.FRAGMENT_MODE_ADMIN_EDIT);
    }

    @Override
    protected void onBackStackChanged(boolean containStack) {
        //apply toolbar simple animation
        TransitionManager.beginDelayedTransition(binding.toolbar);
        int menuRes = containStack ? R.menu.menu_edit : R.menu.menu_main;
        boolean hide = containStack && fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_ADD);
        setToolbarMenu(hide, menuRes);
        setToolbarTitle(containStack ? getTitle(fragmentMode) : getString(R.string.toolbar_title));
    }

    @Override
    protected boolean isAdmin() {
        return true;
    }

    @Override
    protected Integer getMenuRes() {
        return R.menu.menu_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        } else if (id == R.id.action_about) {
            UtilsDialog.showSnackbar(binding.fab, getString(R.string.app_about));
            return true;
        } else if (id == R.id.action_logout) {
            apiRequestViewModel.logout();
            return true;
        } else if (id == R.id.action_check) {
            UtilsDialog.showSnackbar(binding.fab, getString(R.string.message_save_data),
                    getString(R.string.action_button_save), null, v -> saveData());
            return true;
        } else if (id == R.id.action_delete) {
            UtilsDialog.showSnackbar(binding.fab, getString(R.string.message_delete_data),
                    getString(R.string.action_button_delete), null, v -> deleteData());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteData() {
        if (dataPendudukModel != null) {
            apiRequestViewModel.deleteDataSource(dataPendudukModel);
        }
    }

    private void saveData() {
        if (dataPendudukModel != null && fragmentMode != null) {
            if (fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_ADD)) {
                apiRequestViewModel.addDataSource(dataPendudukModel);
            } else if (fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_EDIT)) {
                apiRequestViewModel.updateDataSource(dataPendudukModel);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.fab) {
            addFragmentDetail(null, Constants.FRAGMENT_MODE_ADMIN_ADD);
        }
    }

    private String getTitle(String fragmentMode) {
        if (fragmentMode != null) {
            if (fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_ADD)) {
                return getString(R.string.toolbar_details_title_add);
            } else if (fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_EDIT)) {
                return getString(R.string.toolbar_details_title_edit);
            }
        }

        return getString(R.string.toolbar_details_title);
    }

    private void addFragmentDetail(@Nullable DataPendudukModel dataPendudukModel,
                                   @NonNull String fragmentMode) {

        this.fragmentMode = fragmentMode;
        this.dataPendudukModel = dataPendudukModel;

        Bundle bundle = MyBundle.Builder()
                .putParcelable(DataPendudukModel.TAG, dataPendudukModel)
                .putString(Constants.FRAGMENT_MODE_KEY, this.fragmentMode)
                .Build();

        addFragmentToContainer(DetailsFragment.newInstance(bundle), DetailsFragment.getSimpleName());
    }
}
