package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.rest.RestApi;
import com.example.isoft.studyskadden.rest.RetrofitServiceFactory;
import com.example.isoft.studyskadden.rest.pojo.PojoModel;

import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public class OpenWeatherModel implements WeatherModel {
    @Override
    public Observable<PojoModel> request(String name) {

        RestApi weatherService = RetrofitServiceFactory.getInstance();

        return weatherService.getWheatherReportByCityName(name);

    }
}
