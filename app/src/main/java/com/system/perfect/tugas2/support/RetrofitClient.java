package com.system.perfect.tugas2.support;

import com.system.perfect.tugas2.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retro = null;

    public static Retrofit getClient() {
        if (retro==null) {
            retro = new Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }

}
