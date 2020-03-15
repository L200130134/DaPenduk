package com.rikyahmadfathoni.developer.common_dapenduk.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<V extends ViewModel, D extends ViewDataBinding>
        extends Fragment {

    @SuppressWarnings("WeakerAccess")
    protected V viewModel;

    protected D binding;

    protected abstract Class<V> getViewModel();

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onCreatedBundle(@Nullable Bundle bundle);

    protected abstract void onCreatedView();

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(getViewModel());
    }

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);

        onCreatedBundle(getArguments());

        onCreatedView();

        return binding.getRoot();
    }

}
