package com.rikyahmadfathoni.developer.dapenduk.ui.fragment;

import android.os.Bundle;

import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseDetailsFragment;

public class DetailsFragment extends BaseDetailsFragment {

    public static String getSimpleName() {
        return DetailsFragment.class.getSimpleName();
    }

    public static DetailsFragment newInstance(Bundle bundle) {

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void initView() {

    }
}
