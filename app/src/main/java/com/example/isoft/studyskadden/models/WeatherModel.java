package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.rest.pojo.PojoModel;

import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherModel {

    public Observable<PojoModel> request(String name);
}
