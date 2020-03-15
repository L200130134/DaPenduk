package com.rikyahmadfathoni.developer.common_dapenduk.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;
import com.rikyahmadfathoni.developer.common_dapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.adapter.DataListAdapter;
import com.rikyahmadfathoni.developer.common_dapenduk.databinding.ActivityMainBinding;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataFormResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.provider.ViewModelFactory;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.custom.CustomSearchView;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;
import com.rikyahmadfathoni.developer.common_dapenduk.viewmodel.ApiRequestViewModel;

import javax.inject.Inject;

public abstract class BaseMainActivity extends BaseActivity<ActivityMainBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    @MenuRes
    private Integer menuRes;

    private ProgressDialog progressDialog;

    protected DataListAdapter dataListAdapter;

    protected ApiRequestViewModel apiRequestViewModel;

    protected boolean hideToolbarMenu;

    protected MenuItem menuItem;

    protected abstract void initView();

    protected abstract boolean isAdmin();

    protected abstract void onDataClicked(DataPendudukModel dataPendudukModel, int position);

    protected abstract void onBackStackChanged(boolean containStack);

    @MenuRes
    protected abstract Integer getMenuRes();

    @Override
    protected final int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        ((BaseApplication) getApplication()).getAppComponent().doMainInjection(this);
        apiRequestViewModel = new ViewModelProvider(this, viewModelFactory).get(ApiRequestViewModel.class);
    }

    @Override
    protected void setIntentExtra(Intent intent) {
        //called before onCreateView
    }

    @Override
    protected final void onCreateView(Bundle savedInstanceState) {

        setToolbarTitle(getString(R.string.toolbar_title));

        setSupportActionBar(binding.toolbar);

        dataListAdapter = new DataListAdapter();
        binding.recyclerView.setAdapter(dataListAdapter);

        initView();
        initData();
        initListener();
    }

    private void initData() {

        apiRequestViewModel.getItemPagedList().observe(this, new Observer<PagedList<DataPendudukModel>>() {
            @Override
            public void onChanged(PagedList<DataPendudukModel> dataPendudukModels) {
                dataListAdapter.submitList(dataPendudukModels);
            }
        });

        apiRequestViewModel.getFormResponseLiveData().observe(this, new Observer<DataFormResponse>() {
            @Override
            public void onChanged(DataFormResponse dataFormResponse) {
                if (!dataFormResponse.isDataValid()) {
                    UtilsDialog.showSnackbar(binding.fab, dataFormResponse.getMessage());
                } else {
                    showProgressDialog("Proses", "Tunggu sebentar.");
                }
            }
        });

        apiRequestViewModel.getLiveDataResponse().observe(this, new Observer<DataPendudukResponse>() {
            @Override
            public void onChanged(DataPendudukResponse response) {

                dismissProgressDialog();

                boolean completed = response == null
                        || response.isLoadCompleted()
                        || response.isError();

                if (response == null) {
                    UtilsDialog.showSnackbar(binding.fab,
                            "Ada kesalahan, response tidak valid.");
                } else {
                    if (response.isOnexecute() && !response.isManipulated()) {
                        setRefresing(true);
                    }
                    if (!response.isError() && isAdmin()) {
                        if (response.isManipulated()) {
                            setRefresing(true);
                            apiRequestViewModel.invalidateDataSource();
                        }
                        onBackPressed(false);
                    }
                    UtilsDialog.showSnackbar(binding.fab, response.getMessage());
                }

                if (completed) {
                    setRefresing(false);
                }
            }
        });
    }

    private void initListener() {

        binding.swipeRefresh.setOnRefreshListener(this);
        dataListAdapter.setOnClickListener(this::onDataClicked);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                boolean containStack = getSupportFragmentManager().getBackStackEntryCount() > 0;

                if (containStack) {
                    binding.fab.hide();
                } else if (isAdmin()) {
                    binding.fab.show();
                }

                BaseMainActivity.this.onBackStackChanged(containStack);
            }
        });
    }

    @Override
    public final void onBackPressed() {
        onBackPressed(true);
    }

    public void setToolbarMenu(boolean hideToolbarMenu, @MenuRes Integer menuRes) {
        this.hideToolbarMenu = hideToolbarMenu;
        this.menuRes = menuRes;
        invalidateOptionsMenu();
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {

        if (!hideToolbarMenu) {
            Integer idMenu = menuRes != null ? menuRes : getMenuRes();

            if (idMenu != null) {
                getMenuInflater().inflate(idMenu, menu);
                menuItem = menu.findItem(R.id.action_search);

                if (menuItem != null) {
                    View view = menuItem.getActionView();

                    if (view instanceof CustomSearchView) {
                        CustomSearchView searchView = (CustomSearchView) menuItem.getActionView();
                        searchView.setQueryHint(getString(R.string.toolbar_text_search));

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                apiRequestViewModel.setFilterData(newText);
                                return true;
                            }
                        });
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public void onRefresh() {
        //prevent empty text called when search isCollapsed
        apiRequestViewModel.blockFilter(true);
        if (menuItem != null) {
            menuItem.collapseActionView();
        }
        apiRequestViewModel.invalidateDataSource();
    }

    @Override
    protected boolean isAuthValid() {
        return false;
    }

    @Override
    protected void startAuthActivity() {
    }

    private void onRefresh(boolean forceInvalidateDataSource) {

    }

    protected void setRefresing(boolean refresing) {
        runOnUiThread(() -> {
            binding.swipeRefresh.setRefreshing(refresing);
        });
    }

    @SuppressWarnings("SameParameterValue")
    protected void showProgressDialog(String title, String message) {
        runOnUiThread(() -> progressDialog = UtilsDialog.createProgressDialog(
                BaseMainActivity.this, title, message));
    }

    protected void dismissProgressDialog() {
        runOnUiThread(() -> {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        });
    }

    protected void onBackPressed(boolean exitApp) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int position = fragmentManager.getBackStackEntryCount();
        if (position > 0) {
            fragmentManager.popBackStack();
            setEnableHomeAction(false);
        } else if (exitApp) {
            super.onBackPressed();
        }
    }

    public void setToolbarTitle(@NonNull String title) {
        if (binding.toolbar != null) {
            binding.toolbar.setTitle(title);
        }
    }

    protected void setSubTitle(@NonNull String subtitle) {
        if (binding.toolbar != null) {
            binding.toolbar.setSubtitle(subtitle);
        }
    }

    protected void setEnableHomeAction(boolean isEnable) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isEnable);
        }
    }

    protected void addFragmentToContainer(@NonNull Fragment fragment, @NonNull String TAG) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment, TAG)
                .addToBackStack(TAG)
                .commit();

        setEnableHomeAction(true);
    }
}
