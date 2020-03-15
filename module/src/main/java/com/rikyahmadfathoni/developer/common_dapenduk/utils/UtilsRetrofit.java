package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.LoginResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.network.ApiService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UtilsRetrofit {

    public static Call<LoginResponse> getLoginRequest(ApiService apiService, String email, String password,
                                                      Callback<LoginResponse> callback) {
        Call<LoginResponse> responseBodyCall = apiService.loginRequest(email, password);

        responseBodyCall.enqueue(callback);

        return responseBodyCall;
    }

    public static Call<DataPendudukResponse> getDataPendudukRequest(ApiService apiService, int startIndex, int totalPage,
                                                                    Callback<DataPendudukResponse> callback) {
        Call<DataPendudukResponse> responseBodyCall = apiService.dataPendudukRequest(startIndex, totalPage);

        responseBodyCall.enqueue(callback);

        return responseBodyCall;
    }

    public static Call<DataPendudukResponse> addDataPendudukRequest(ApiService apiService,
                                                                    DataPendudukModel dataPendudukModel,
                                                                    Callback<DataPendudukResponse> callback) {

        UtilsData.removeUrlHost(dataPendudukModel);

        MultipartBody.Part partImage = createMultipartBody(
                "imageupload", dataPendudukModel.getFotoPath());

        System.out.println("response partImage : " + partImage);

        Call<DataPendudukResponse> responseBodyCall = apiService
                .addDataPendudukRequest(
                        partImage,
                        getRequest(dataPendudukModel.getNama()),
                        getRequest(dataPendudukModel.getJenis_kelamin()),
                        getRequest(dataPendudukModel.getAlamat()),
                        getRequest(dataPendudukModel.getTempat_lahir()),
                        getRequest(dataPendudukModel.getTanggal_lahir()),
                        getRequest(dataPendudukModel.getPekerjaan())
                );

        responseBodyCall.enqueue(callback);

        return responseBodyCall;
    }

    public static Call<DataPendudukResponse> updateDataPendudukRequest(ApiService apiService, DataPendudukModel dataPendudukModel,
                                                                       Callback<DataPendudukResponse> callback) {

        UtilsData.removeUrlHost(dataPendudukModel);

        System.out.println("response id : " + dataPendudukModel.getId());

        MultipartBody.Part partImage = createMultipartBody(
                "imageupload", dataPendudukModel.getFotoPath());

        Call<DataPendudukResponse> responseBodyCall = apiService
                .updateDataPendudukRequest(
                        partImage,
                        getRequest(Boolean.TRUE.toString()),
                        getRequest(dataPendudukModel.getFoto()),
                        getRequest(String.valueOf(dataPendudukModel.getId())),
                        getRequest(dataPendudukModel.getNama()),
                        getRequest(dataPendudukModel.getJenis_kelamin()),
                        getRequest(dataPendudukModel.getAlamat()),
                        getRequest(dataPendudukModel.getTempat_lahir()),
                        getRequest(dataPendudukModel.getTanggal_lahir()),
                        getRequest(dataPendudukModel.getPekerjaan())
                );

        responseBodyCall.enqueue(callback);

        return responseBodyCall;
    }

    public static Call<DataPendudukResponse> deleteDataPendudukRequest(ApiService apiService, int id, String fotoPath,
                                                                       Callback<DataPendudukResponse> callback) {
        Call<DataPendudukResponse> responseBodyCall = apiService
                .deletePendudukRequest(id, fotoPath);

        responseBodyCall.enqueue(callback);

        return responseBodyCall;
    }

    public static MultipartBody.Part createMultipartBody(String reqName, String imagePath) {

        System.out.println("response fotoPath : " + imagePath);

        if (!UtilsString.isEmpty(imagePath) && !imagePath.contains(Constants.BASE_URL)) {

            File imagefile = new File(imagePath);

            if (imagefile.exists()) {
                System.out.println("response fotoPath exists : " + imagePath);

                return MultipartBody.Part.createFormData(reqName, imagefile.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-file"), imagefile));
            }
        }

        return MultipartBody.Part.createFormData(reqName, "",
                RequestBody.create(MediaType.parse("multipart/form-file"), ""));
    }

    public static RequestBody getRequest(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
