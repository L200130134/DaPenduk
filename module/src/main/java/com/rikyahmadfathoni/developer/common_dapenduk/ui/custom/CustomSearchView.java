package com.rikyahmadfathoni.developer.common_dapenduk.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

public class CustomSearchView extends SearchView {

    public CustomSearchView(Context context) {
        super(context);
        init(null);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

    }
}
