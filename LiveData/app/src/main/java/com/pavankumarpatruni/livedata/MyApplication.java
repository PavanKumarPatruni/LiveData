package com.pavankumarpatruni.livedata;

import android.app.Application;
import android.content.Context;

import com.pavankumarpatruni.livedata.api.APIConstants;
import com.pavankumarpatruni.livedata.api.APIService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private APIService service;

    public static MyApplication getInstance(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.HOST_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);

    }

    public APIService getAPIService() {
        return service;
    }

}
