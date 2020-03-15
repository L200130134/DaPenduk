package com.rikyahmadfathoni.developer.common_dapenduk.network;

import com.rikyahmadfathoni.developer.common_dapenduk.model.response.DataPendudukResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.LoginResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginRequest(@Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("data-penduduk.php")
    Call<DataPendudukResponse> dataPendudukRequest(@Field("startIndex") int startIndex,
                                                   @Field("totalPage") int totalPage);

    @Multipart
    @POST("add-data-penduduk.php")
    Call<DataPendudukResponse> addDataPendudukRequest(@Part MultipartBody.Part foto,
                                                      @Part("nama") RequestBody nama,
                                                      @Part("jenis_kelamin") RequestBody jenisKelamin,
                                                      @Part("alamat") RequestBody alamat,
                                                      @Part("tempat_lahir") RequestBody tempatLahir,
                                                      @Part("tanggal_lahir") RequestBody tanggalLahir,
                                                      @Part("pekerjaan") RequestBody pekerjaan);

    @Multipart
    @POST("edit-data-penduduk.php")
    Call<DataPendudukResponse> updateDataPendudukRequest(@Part MultipartBody.Part fotoData,
                                                         @Part("updateimage") RequestBody updateimage,
                                                         @Part("foto") RequestBody fotoUrl,
                                                         @Part("id") RequestBody id,
                                                         @Part("nama") RequestBody nama,
                                                         @Part("jenis_kelamin") RequestBody jenisKelamin,
                                                         @Part("alamat") RequestBody alamat,
                                                         @Part("tempat_lahir") RequestBody tempatLahir,
                                                         @Part("tanggal_lahir") RequestBody tanggalLahir,
                                                         @Part("pekerjaan") RequestBody pekerjaan);

    @FormUrlEncoded
    @POST("delete-data-penduduk.php")
    Call<DataPendudukResponse> deletePendudukRequest(@Field("id") int id,
                                                     @Field("foto") String foto);
}
