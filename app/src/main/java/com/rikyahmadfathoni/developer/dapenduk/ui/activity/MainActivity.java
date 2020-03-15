package com.rikyahmadfathoni.developer.dapenduk.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.transition.TransitionManager;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.builder.MyBundle;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseMainActivity;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;
import com.rikyahmadfathoni.developer.dapenduk.R;
import com.rikyahmadfathoni.developer.dapenduk.ui.fragment.DetailsFragment;

public class MainActivity extends BaseMainActivity {

    public DataPendudukModel dataPendudukModel;

    @Override
    protected void initView() {

        binding.fab.hide();
    }

    @Override
    protected boolean isAuthValid() {
        return true;
    }

    @Override
    protected void onDataClicked(DataPendudukModel dataPendudukModel, int position) {
        addFragmentDetail(dataPendudukModel);
    }

    @Override
    protected void onBackStackChanged(boolean containStack) {
        //apply toolbar simple animation
        TransitionManager.beginDelayedTransition(binding.toolbar);
        setToolbarMenu(containStack, getMenuRes());
        setToolbarTitle(containStack ? getString(R.string.toolbar_details_title) :
                getString(R.string.toolbar_title));
    }

    @Override
    protected boolean isAdmin() {
        return false;
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragmentDetail(@Nullable DataPendudukModel dataPendudukModel) {

        this.dataPendudukModel = dataPendudukModel;

        Bundle bundle = MyBundle.Builder()
                .putParcelable(DataPendudukModel.TAG, dataPendudukModel)
                .putString(Constants.FRAGMENT_MODE_KEY, Constants.FRAGMENT_MODE_DEFAULT)
                .Build();

        addFragmentToContainer(DetailsFragment.newInstance(bundle), DetailsFragment.getSimpleName());
    }
}
