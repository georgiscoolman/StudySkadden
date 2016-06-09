package com.example.isoft.studyskadden.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Georg on 06.05.2016.
 */
public class RetrofitServiceFactory {

    private static volatile RestApi retrofitSingletone;

    private RetrofitServiceFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitSingletone = retrofit.create(RestApi.class);
    }

    public static RestApi getInstance(){
        if (retrofitSingletone == null) {
            synchronized(RestApi.class){
                if (retrofitSingletone == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RestApi.URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    retrofitSingletone = retrofit.create(RestApi.class);
                }
            }
        }
        return retrofitSingletone;
    }

}
