package com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.ApiRequestRepository;

public class PendudukDataSourceFactory extends DataSource.Factory<Integer, DataPendudukModel> {

    private MutableLiveData<DataPendudukResponse> liveDataResponse = new MutableLiveData<>();

    private MutableLiveData<PageKeyedDataSource<Integer, DataPendudukModel>> itemLiveDataSource = new MutableLiveData<>();

    private ApiRequestRepository apiRequestRepository;

    public PendudukDataSourceFactory(ApiRequestRepository apiRequestRepository) {
        this.apiRequestRepository = apiRequestRepository;
    }

    @NonNull
    @Override
    public DataSource<Integer, DataPendudukModel> create() {

        //getting our data source object
        PendudukDataSource pendudukDataSource = new PendudukDataSource(apiRequestRepository);

        //set live data
        pendudukDataSource.setLiveDataResponse(liveDataResponse);

        //posting the data source to get the values
        itemLiveDataSource.postValue(pendudukDataSource);

        //returning the data source
        return pendudukDataSource;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, DataPendudukModel>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

    public MutableLiveData<DataPendudukResponse> getLiveDataResponse() {
        return liveDataResponse;
    }
}
