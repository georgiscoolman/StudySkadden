package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherModel {

    public Observable<ForecastDaily> request(String name);
}
