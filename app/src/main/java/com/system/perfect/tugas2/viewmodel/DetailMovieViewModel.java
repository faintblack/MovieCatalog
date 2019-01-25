package com.system.perfect.tugas2.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.support.APIInterface;
import com.system.perfect.tugas2.support.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieViewModel extends ViewModel {

    private APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
    public MutableLiveData<Movie> data;

    public MutableLiveData<Movie> getDetail(String id, String api){
        if (data == null){
            data = new MutableLiveData<>();
            service.getDetailsMovie(id, api).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    data.setValue(response.body());
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.d("DMViewModel request", "failed");
                }
            });
        }
        return data;
    }

}
