package com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.network.ApiService;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.ApiRequestRepository;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsData;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsString;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukDataSource extends PageKeyedDataSource<Integer, DataPendudukModel> {

    private MutableLiveData<DataPendudukResponse> liveDataResponse = new MutableLiveData<>();

    private ApiRequestRepository apiRequestRepository;

    private ApiService apiService;

    /*
     * Beware, data source may recreate new object on invalidate
     * */
    @Inject
    public PendudukDataSource(ApiRequestRepository apiRequestRepository) {
        this.apiRequestRepository = apiRequestRepository;
        this.apiService = apiRequestRepository.getApiService();
    }

    void setLiveDataResponse(MutableLiveData<DataPendudukResponse> liveDataResponse) {
        this.liveDataResponse = liveDataResponse;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, DataPendudukModel> callback) {

        liveDataResponse.postValue(new DataPendudukResponse(false, true));
        load(callback, null, Constants.FIRST_PAGE);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, DataPendudukModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, DataPendudukModel> callback) {

        load(null, callback, params.key);
    }

    private void load(LoadInitialCallback<Integer, DataPendudukModel> callbackInitial,
                      LoadCallback<Integer, DataPendudukModel> callbackAfter,
                      int startData) {

        if (apiRequestRepository.getTextFilter() != null) {

            int totalDataSize = apiRequestRepository.getFilterModels().size();

            List<DataPendudukModel> modelsList = filterDataPendudukModel(
                    apiRequestRepository.getFilterModels(), apiRequestRepository.getTextFilter());

            boolean noItemOnNext = totalDataSize >= Constants.PAGE_SIZE;

            UtilsData.addUrlHost(modelsList);

            boolean loadCompleted = noItemOnNext;

            if (callbackInitial != null) {
                callbackInitial.onResult(modelsList, null,
                        noItemOnNext ? null : startData);
            } else if (callbackAfter != null) {
                Integer key = (startData >= totalDataSize) ? null : startData + Constants.PAGE_SIZE;

                callbackAfter.onResult(modelsList, key);

                if (key == null) {
                    loadCompleted = true;
                }
            }

            liveDataResponse.postValue(new DataPendudukResponse(
                    totalDataSize, modelsList, null, false, loadCompleted,
                    0, false));

            if (loadCompleted || modelsList.isEmpty()) {
                apiRequestRepository.resetFilterData();
            }

            return;
        }

        apiService.dataPendudukRequest(startData, Constants.PAGE_SIZE)
                .enqueue(new Callback<DataPendudukResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DataPendudukResponse> call,
                                           @NonNull Response<DataPendudukResponse> response) {

                        DataPendudukResponse dataResponse = response.body();

                        if (dataResponse != null) {

                            List<DataPendudukModel> dataPendudukModels = dataResponse.getDataPenduduk();

                            UtilsData.addUrlHost(dataPendudukModels);

                            Integer key = null;

                            if (callbackInitial != null) {
                                callbackInitial.onResult(dataPendudukModels,
                                        null, Constants.PAGE_SIZE);
                            } else if (callbackAfter != null) {
                                key = (startData >= dataResponse.getDataTotal())
                                        ? null : startData + Constants.PAGE_SIZE;
                                callbackAfter.onResult(dataPendudukModels, key);
                            }

                            liveDataResponse.setValue(new DataPendudukResponse(
                                    dataResponse.getDataTotal(), dataPendudukModels,
                                    null, false, key == null, 0, false));
                            return;
                        }

                        liveDataResponse.setValue(new DataPendudukResponse(
                                0, null, "Bad Response!",
                                true, true, 0, false));
                    }

                    @Override
                    public void onFailure(@NonNull Call<DataPendudukResponse> call,
                                          @NonNull Throwable t) {

                        liveDataResponse.setValue(new DataPendudukResponse(
                                0, null, t.getMessage(),
                                true, true, 0, false));
                    }
                });
    }

    private List<DataPendudukModel> filterDataPendudukModel(List<DataPendudukModel> models,
                                                            String value) {

        if (!UtilsString.isEmpty(value)) {

            List<DataPendudukModel> filteredList = new ArrayList<>();

            for (DataPendudukModel dataPendudukModel : models) {
                if (dataPendudukModel != null) {
                    String nama = dataPendudukModel.getNama();
                    String alamat = dataPendudukModel.getAlamat();
                    if (!UtilsString.isEmpty(nama)) {
                        if (nama.toLowerCase().contains(value)) {
                            filteredList.add(dataPendudukModel);
                        }
                    }
                    if (!UtilsString.isEmpty(alamat)) {
                        if (alamat.toLowerCase().contains(value)) {
                            filteredList.add(dataPendudukModel);
                        }
                    }
                }
            }

            return filteredList;
        }

        return models;
    }
}
