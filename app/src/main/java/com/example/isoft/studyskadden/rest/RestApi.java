package com.example.isoft.studyskadden.rest;

import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Georg on 19.04.2016.
 */
public interface RestApi {

    String URL = "http://api.openweathermap.org";
    String API_KEY = "e96b626a0cb231086ffea9d1f23488bd";
    String METRIC = "&units=metric";

    @GET("/data/2.5/forecast/daily?appid=" + API_KEY + METRIC)
    Observable<ForecastDaily> getWheatherReportByCityName(@Query("q") String cityName);

    @GET("/data/2.5/forecast/daily?appid=" + API_KEY + METRIC)
    Observable<ForecastDaily> getWheatherReportBySeveralCityId(@Query("id") String ids);

}