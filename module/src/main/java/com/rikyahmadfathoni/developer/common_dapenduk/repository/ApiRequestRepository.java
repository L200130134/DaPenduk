package com.rikyahmadfathoni.developer.common_dapenduk.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataFormResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.LoginResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.network.ApiService;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource.PendudukDataSource;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource.PendudukDataSourceFactory;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsPreferences;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsRetrofit;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsString;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequestRepository {

    private ApiService apiService;

    private PendudukDataSourceFactory pendudukDataSourceFactory;

    private MutableLiveData<DataPendudukResponse> liveDataResponse;

    private LiveData<PagedList<DataPendudukModel>> itemPagedList;

    private LiveData<PageKeyedDataSource<Integer, DataPendudukModel>> liveDataSource;

    private MutableLiveData<DataFormResponse> formResponseLiveData = new MutableLiveData<>();

    private List<DataPendudukModel> filterModels = new ArrayList<>();

    private List<DataPendudukModel> lastDataModels = new ArrayList<>();

    private MutableLiveData<LoginResponse> loginResponseLive = new MutableLiveData<>();

    private Call<LoginResponse> loginRequest;

    private LoginResponse user;

    private UtilsPreferences preferences;

    private String textFilter;

    private boolean blockFilter;

    @Inject
    public ApiRequestRepository(ApiService apiService) {

        this.apiService = apiService;

        this.preferences = new UtilsPreferences();

        this.pendudukDataSourceFactory = new PendudukDataSourceFactory(this);

        liveDataSource = pendudukDataSourceFactory.getItemLiveDataSource();

        liveDataResponse = pendudukDataSourceFactory.getLiveDataResponse();

        itemPagedList = (new LivePagedListBuilder<>(pendudukDataSourceFactory,
                new PagedList.Config.Builder()
                        .setPrefetchDistance(5)
                        .setEnablePlaceholders(false)
                        .setPageSize(Constants.PAGE_SIZE).build())).build();
    }

    public ApiService getApiService() {
        return apiService;
    }

    /*
     * Live data and response
     * */
    public MutableLiveData<DataFormResponse> getFormResponseLiveData() {
        return formResponseLiveData;
    }

    public MutableLiveData<DataPendudukResponse> getLiveDataResponse() {
        return liveDataResponse;
    }

    public LiveData<PagedList<DataPendudukModel>> getItemPagedList() {
        return itemPagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, DataPendudukModel>> getLiveDataSource() {
        return liveDataSource;
    }

    public PendudukDataSourceFactory getPendudukDataSourceFactory() {
        return pendudukDataSourceFactory;
    }

    /*
     * Query data and update
     * */
    public void addDataSource(DataPendudukModel dataPendudukModel) {

        if (isDataValid(dataPendudukModel)) {

            System.out.println("response data foto : " + dataPendudukModel.getFotoPath());

            UtilsRetrofit.addDataPendudukRequest(apiService, dataPendudukModel, new Callback<DataPendudukResponse>() {
                @Override
                public void onResponse(@NonNull Call<DataPendudukResponse> call,
                                       @NonNull Response<DataPendudukResponse> response) {
                    DataPendudukResponse responseData = response.body();
                    if (responseData != null) {
                        responseData.setCodeRequest(Constants.REQUEST_CODE_QUERY_ADD);
                        responseData.setManipulated(true);
                    }
                    liveDataResponse.setValue(responseData);
                }

                @Override
                public void onFailure(@NonNull Call<DataPendudukResponse> call,
                                      @NonNull Throwable t) {

                    liveDataResponse.setValue(new DataPendudukResponse(0,
                            null, t.getLocalizedMessage(), true,
                            true, Constants.REQUEST_CODE_QUERY_ADD, true));
                }
            });
        }
    }

    public void updateDataSource(DataPendudukModel dataPendudukModel) {

        if (isDataValid(dataPendudukModel)) {

            System.out.println("response data foto : " + dataPendudukModel.getFotoPath());

            UtilsRetrofit.updateDataPendudukRequest(apiService, dataPendudukModel, new Callback<DataPendudukResponse>() {
                @Override
                public void onResponse(@NonNull Call<DataPendudukResponse> call,
                                       @NonNull Response<DataPendudukResponse> response) {

                    DataPendudukResponse responseData = response.body();
                    if (responseData != null) {
                        responseData.setCodeRequest(Constants.REQUEST_CODE_QUERY_UPDATE);
                        responseData.setManipulated(true);
                    }
                    liveDataResponse.setValue(responseData);
                }

                @Override
                public void onFailure(@NonNull Call<DataPendudukResponse> call,
                                      @NonNull Throwable t) {

                    liveDataResponse.setValue(new DataPendudukResponse(0,
                            null, t.getLocalizedMessage(), true,
                            true, Constants.REQUEST_CODE_QUERY_UPDATE, true));
                }
            });
        }
    }

    public void deleteDataSource(DataPendudukModel dataPendudukModel) {

        if (dataPendudukModel != null) {

            formResponseLiveData.setValue(new DataFormResponse(
                    "Mencoba menghapus", true));

            UtilsRetrofit.deleteDataPendudukRequest(apiService, dataPendudukModel.getId(),
                    dataPendudukModel.getFoto(), new Callback<DataPendudukResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<DataPendudukResponse> call,
                                               @NonNull Response<DataPendudukResponse> response) {

                            DataPendudukResponse responseData = response.body();
                            if (responseData != null) {
                                responseData.setCodeRequest(Constants.REQUEST_CODE_QUERY_DELETE);
                                responseData.setManipulated(true);
                            }
                            liveDataResponse.setValue(responseData);
                        }

                        @Override
                        public void onFailure(@NonNull Call<DataPendudukResponse> call,
                                              @NonNull Throwable t) {

                            liveDataResponse.setValue(new DataPendudukResponse(0,
                                    null, t.getLocalizedMessage(), true,
                                    true, Constants.REQUEST_CODE_QUERY_DELETE, true));
                        }
                    });
        }
    }

    /*
    * Login request
    * */
    public MutableLiveData<LoginResponse> getLoginResponseLive() {
        return loginResponseLive;
    }

    public Call<LoginResponse> getLoginRequest() {
        return loginRequest;
    }

    public void login(String email, String password) {

        if (UtilsString.isEmpty(email) || UtilsString.isEmpty(password)) {
            loginResponseLive.setValue(new LoginResponse(true));
            return;
        }

        loginRequest = UtilsRetrofit.getLoginRequest(apiService, email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {

                LoginResponse dataResponse = response.body();

                if (dataResponse != null) {
                    loginResponseLive.setValue(dataResponse);
                    setLoggedInUser(dataResponse);
                } else {
                    loginResponseLive.setValue(new LoginResponse(true));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                loginResponseLive.setValue(new LoginResponse(true));
            }
        });
    }

    public void logout() {
        user = null;
        this.preferences.remove(Constants.KEY_PREF_USER);
        this.loginResponseLive.setValue(new LoginResponse(true, true));
    }

    public boolean isLoggedIn() {
        LoginResponse loginResponse;
        if (user != null) {
            loginResponse = user;
        } else {
            loginResponse = preferences.getParcelable(
                    Constants.KEY_PREF_USER, LoginResponse.class);
        }

        if (loginResponse != null) {
            boolean validLogin = !loginResponse.isError();
            if (validLogin) {
                login(loginResponse.getEmail(), loginResponse.getPassword());
            }
            return validLogin;
        }
        return false;
    }

    private void setLoggedInUser(LoginResponse user) {
        this.user = user;
        this.preferences.setParcelable(Constants.KEY_PREF_USER, user);
    }

    public void cancelRequest() {
        if (loginRequest != null) {
            loginRequest.cancel();
        }
    }

    /*
    * Filter data
    * */
    public void setFilterData(String value) {

        UtilsDialog.showLog(ApiRequestRepository.class,
                "data lastDataModels value : " + value);

        UtilsDialog.showLog(ApiRequestRepository.class,
                "data lastDataModels blockFilter : " + blockFilter);

        if (!blockFilter) {
            PendudukDataSource dataSource = (PendudukDataSource) liveDataSource.getValue();
            if (dataSource != null) {
                PagedList<DataPendudukModel> pagedList = itemPagedList.getValue();
                if (pagedList != null) {
                    List<DataPendudukModel> dataPendudukModels = pagedList.snapshot();

                    boolean lastDataEmpty = lastDataModels == null || lastDataModels.isEmpty();

                    if (!lastDataEmpty) {
                        UtilsDialog.showLog(ApiRequestRepository.class,
                                "data lastDataModels set 1 : " + lastDataModels.size());
                        filterModels = lastDataModels;
                    }  else {
                        UtilsDialog.showLog(ApiRequestRepository.class,
                                "data lastDataModels set 2 : " + dataPendudukModels.size());
                        filterModels = lastDataModels = dataPendudukModels;
                    }
                    textFilter = UtilsString.validateNull(value);
                    dataSource.invalidate();
                }
            }
        } else {
            blockFilter = false;
        }
    }

    public List<DataPendudukModel> getFilterModels() {
        return filterModels;
    }

    public String getTextFilter() {
        return textFilter;
    }

    public void resetFilterData() {
        textFilter = null;
        filterModels = null;
    }

    public void blockFilter(boolean blockFilter) {
        this.blockFilter = blockFilter;
    }

    public void invalidateDataSource() {
        resetFilterData();
        PageKeyedDataSource<Integer, DataPendudukModel> ds = getLiveDataSource().getValue();
        if (ds != null) {
            ds.invalidate();
        }
    }

    private boolean isDataValid(DataPendudukModel dataPendudukModel) {

        DataFormResponse dataFormResponse = new DataFormResponse();

        if (UtilsString.isEmpty(dataPendudukModel.getNama())) {
            dataFormResponse.setNamaError(true);
            dataFormResponse.setMessage("Data nama tidak boleh kosong.");
        } else if (UtilsString.isEmpty(dataPendudukModel.getJenis_kelamin())) {
            dataFormResponse.setJenisKelaminError(true);
            dataFormResponse.setMessage("Data jenis kelamin tidak boleh kosong.");
        } else if (UtilsString.isEmpty(dataPendudukModel.getAlamat())) {
            dataFormResponse.setAlamatError(true);
            dataFormResponse.setMessage("Data alamat tidak boleh kosong.");
        } else if (UtilsString.isEmpty(dataPendudukModel.getTempat_lahir())) {
            dataFormResponse.setTempatLahirError(true);
            dataFormResponse.setMessage("Data tempat lahir tidak boleh kosong.");
        } else if (UtilsString.isEmpty(dataPendudukModel.getTanggal_lahir())) {
            dataFormResponse.setTanggalLahirError(true);
            dataFormResponse.setMessage("Data tanggal lahir tidak boleh kosong.");
        } else if (UtilsString.isEmpty(dataPendudukModel.getPekerjaan())) {
            dataFormResponse.setPekerjaanError(true);
            dataFormResponse.setMessage("Data pekerjaan tidak boleh kosong.");
        } else {
            dataFormResponse.setDataValid(true);
        }

        formResponseLiveData.setValue(dataFormResponse);

        return dataFormResponse.isDataValid();
    }
}
