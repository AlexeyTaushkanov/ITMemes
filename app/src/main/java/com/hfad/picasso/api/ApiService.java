package com.hfad.picasso.api;

import com.hfad.picasso.Example;
import com.hfad.picasso.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/random?json=true")
    Call<Data> getRandomGifDescription();
    @GET("/latest/{id}?json=true")
    Call<Example> getExample(@Path("id") int id);
    @GET("/top/{page}?json=true")
    Call<Example> getTop(@Path("page") int page);
}
