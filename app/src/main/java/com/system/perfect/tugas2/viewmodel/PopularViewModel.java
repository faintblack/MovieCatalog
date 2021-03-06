package com.system.perfect.tugas2.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.model.MovieResult;
import com.system.perfect.tugas2.support.APIInterface;
import com.system.perfect.tugas2.support.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularViewModel extends ViewModel {
    private APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
    public MutableLiveData<List<Movie>> data;
    public MutableLiveData<List<Movie>> dataSearch;

    public MutableLiveData<List<Movie>> getDataPopular(String api, String title){
        if (TextUtils.isEmpty(title)){
            if (data == null){
                data = new MutableLiveData<>();
                service.getPopular(api).enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        data.setValue(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d("PViewModel request", "failed");
                    }
                });
            }
        } else {
            if (title.equals(".")){
                data = new MutableLiveData<>();
                service.getPopular(api).enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        data.setValue(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d("PViewModel request", "failed");
                    }
                });
            } else {
                service.getSearchMovie(api, title).enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        data.setValue(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d("SearchVModel request", "failed");
                    }
                });
            }

        }
        return data;
    }
}
