package com.rikyahmadfathoni.developer.common_dapenduk.repository.provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rikyahmadfathoni.developer.common_dapenduk.repository.ApiRequestRepository;
import com.rikyahmadfathoni.developer.common_dapenduk.viewmodel.ApiRequestViewModel;

import javax.inject.Inject;

@SuppressWarnings("unchecked")
public class ViewModelFactory implements ViewModelProvider.Factory {

    private ApiRequestRepository requestRepository;

    @Inject
    public ViewModelFactory(ApiRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ApiRequestViewModel.class)) {
            return (T) new ApiRequestViewModel(requestRepository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
