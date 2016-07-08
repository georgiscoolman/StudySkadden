package com.example.isoft.studyskadden.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Georg on 06.05.2016.
 */
public class RetrofitServiceFactory {

    private static volatile RestApi retrofitSingletone;

    private RetrofitServiceFactory() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        retrofitSingletone = retrofit.create(RestApi.class);
    }

    public static RestApi getInstance(){
        if (retrofitSingletone == null) {
            synchronized(RestApi.class){
                if (retrofitSingletone == null) {
                    OkHttpClient.Builder client = new OkHttpClient.Builder();
                    client
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .followRedirects(true)
                            .followSslRedirects(true);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RestApi.URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client.build())
                            .build();

                    retrofitSingletone = retrofit.create(RestApi.class);
                }
            }
        }
        return retrofitSingletone;
    }

}
