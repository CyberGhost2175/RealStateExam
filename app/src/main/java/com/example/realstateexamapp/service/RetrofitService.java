package com.example.realstateexamapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    private static final String baseUrl = "http://10.0.2.2:8080/api/v1/";

    public RetrofitService() {
        init();
    }

    public static String getDownloadFileUrl(String fileName) {
        return baseUrl + "static/downloadFile/" + fileName;
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
