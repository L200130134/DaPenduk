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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseDialogFragment<D extends ViewDataBinding>
        extends BottomSheetDialogFragment {

    protected D binding;

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onCreatedBundle(@Nullable Bundle bundle);

    protected abstract void onCreatedView();

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);

        onCreatedBundle(getArguments());

        onCreatedView();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
