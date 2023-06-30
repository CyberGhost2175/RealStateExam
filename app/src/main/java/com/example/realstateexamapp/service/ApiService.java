package com.example.realstateexamapp.service;

import com.example.realstateexamapp.domain.RealEstatesDomain;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface    ApiService {
    @GET("real-estates/get-all")
    Call<List<RealEstatesDomain>> getAll();

    @GET("real-estates/category/{category}")
    Call<List<RealEstatesDomain>> getByCategory(@Path("category") String category);

    @GET("real-estates/type/{type}")
    Call<List<RealEstatesDomain>> getByType(@Path("type") String type);

    @GET("real-estates/{id}")
    Call<List<RealEstatesDomain>> getById(@Path("id") Long id);


}
