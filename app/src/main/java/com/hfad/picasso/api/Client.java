package com.hfad.picasso.api;

import com.hfad.picasso.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit getRetrofitInstance(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  ApiService getApiService(String baseUrl) {
        return getRetrofitInstance(baseUrl).create((ApiService.class));
    }
}
