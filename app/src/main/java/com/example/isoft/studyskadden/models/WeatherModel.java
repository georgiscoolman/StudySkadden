package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherModel {

    public Observable<PreviewCityWeather> request(String name);
    public Observable<PreviewCityWeather> request(long id);
    public RealmResults<MyCity> getAll();
    public void remove(long id);
    public void closeDBconnection();
    public void init();
    public boolean isItemSaved(long id);
}
