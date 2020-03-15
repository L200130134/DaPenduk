package com.rikyahmadfathoni.developer.common_dapenduk.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataFormResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.LoginResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.ApiRequestRepository;

public class ApiRequestViewModel extends ViewModel {

    private ApiRequestRepository apiRequestRepository;

    public ApiRequestViewModel(ApiRequestRepository requestRepository) {
        this.apiRequestRepository = requestRepository;
    }

    public MutableLiveData<DataFormResponse> getFormResponseLiveData() {
        return apiRequestRepository.getFormResponseLiveData();
    }

    public MutableLiveData<DataPendudukResponse> getLiveDataResponse() {
        return apiRequestRepository.getLiveDataResponse();
    }

    public LiveData<PagedList<DataPendudukModel>> getItemPagedList() {
        return apiRequestRepository.getItemPagedList();
    }

    public LiveData<PageKeyedDataSource<Integer, DataPendudukModel>> getLiveDataSource() {
        return apiRequestRepository.getLiveDataSource();
    }
    public MutableLiveData<LoginResponse> getLoginResponseLive() {
        return apiRequestRepository.getLoginResponseLive();
    }

    public void login(String username, String password) {
        apiRequestRepository.login(username, password);
    }

    public void logout() {
        apiRequestRepository.logout();
    }

    public boolean isLoggedIn() {
        return apiRequestRepository.isLoggedIn();
    }

    public void cancelRequest() {
        apiRequestRepository.cancelRequest();
    }

    public void setFilterData(String value) {
        apiRequestRepository.setFilterData(value);
    }

    public void resetFilterData() {
        apiRequestRepository.resetFilterData();
    }

    public void blockFilter(boolean blockFilter) {
        apiRequestRepository.blockFilter(blockFilter);
    }

    public void invalidateDataSource() {
        apiRequestRepository.invalidateDataSource();
    }

    public void deleteDataSource(DataPendudukModel dataPendudukModel) {
        apiRequestRepository.deleteDataSource(dataPendudukModel);
    }

    public void addDataSource(@NonNull DataPendudukModel dataPendudukModel) {
        apiRequestRepository.addDataSource(dataPendudukModel);
    }

    public void updateDataSource(@NonNull DataPendudukModel dataPendudukModel) {
        apiRequestRepository.updateDataSource(dataPendudukModel);
    }
}
