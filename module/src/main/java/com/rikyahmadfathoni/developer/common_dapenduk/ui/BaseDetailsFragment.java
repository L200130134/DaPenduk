package com.rikyahmadfathoni.developer.common_dapenduk.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.adapter.DataDetailsAdapter;
import com.rikyahmadfathoni.developer.common_dapenduk.databinding.FragmentDetailsBinding;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.viewmodel.DetailsFragmentViewModel;

import java.util.List;

public abstract class BaseDetailsFragment extends BaseFragment<DetailsFragmentViewModel, FragmentDetailsBinding> {

    protected DataDetailsAdapter adapter;

    protected DataPendudukModel dataPendudukModel;

    protected String fragmentMode;

    protected abstract void initView();

    @Override
    protected final Class<DetailsFragmentViewModel> getViewModel() {

        return DetailsFragmentViewModel.class;
    }

    @Override
    protected final int getLayoutRes() {
        return R.layout.fragment_details;
    }

    @Override
    protected final void onCreatedBundle(@Nullable Bundle bundle) {

        if (bundle != null) {

            fragmentMode = bundle.getString(Constants.FRAGMENT_MODE_KEY);

            dataPendudukModel = bundle.getParcelable(DataPendudukModel.TAG);

        }

        if (fragmentMode == null) {
            fragmentMode = Constants.FRAGMENT_MODE_DEFAULT;
        }

        if (dataPendudukModel == null) {
            dataPendudukModel = new DataPendudukModel();
        }
    }

    @Override
    protected final void onCreatedView() {

        adapter = new DataDetailsAdapter();

        binding.recyclerView.setAdapter(adapter);

        initView();

        initData();
    }

    private void initData() {

        viewModel.getListDetailsLiveData().observe(this, new Observer<List<DataDetailsModel>>() {
            @Override
            public void onChanged(List<DataDetailsModel> dataDetailsModels) {

                adapter.submitList(dataDetailsModels);
            }
        });

        viewModel.setDataList(dataPendudukModel, fragmentMode);
    }
}
