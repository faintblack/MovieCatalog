package com.system.perfect.tugas2.support;

import com.system.perfect.tugas2.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/now_playing")
    Call<MovieResult> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResult> getUpcoming(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResult> getPopular(@Query("api_key") String apiKey);

    @GET("movie/{id_movie}")
    Call<MovieResult> getDetailsMovie(@Path("id_movie") int id, @Query("api_key") String apiKey);

}
