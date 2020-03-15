package com.rikyahmadfathoni.developer.common_dapenduk.network;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.ApiRequestRepository;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource.PendudukDataSource;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.datasource.PendudukDataSourceFactory;
import com.rikyahmadfathoni.developer.common_dapenduk.repository.provider.ViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiService getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    ApiRequestRepository getRepository(ApiService apiService) {
        return new ApiRequestRepository(apiService);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(ApiRequestRepository requestRepository) {
        return new ViewModelFactory(requestRepository);
    }

    @Provides
    @Singleton
    PendudukDataSourceFactory getDataSourceFactory(ApiRequestRepository requestRepository) {
        return new PendudukDataSourceFactory(requestRepository);
    }

    @Provides
    @Singleton
    PendudukDataSource getDataSource(ApiRequestRepository requestRepository) {
        return new PendudukDataSource(requestRepository);
    }
}
