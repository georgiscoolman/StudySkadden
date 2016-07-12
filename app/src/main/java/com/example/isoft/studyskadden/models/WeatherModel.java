package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherModel {

    public Observable<ForecastDaily> request(String name);
    public Observable<ForecastDaily> request(long id);
    public void closeDBconnection();
    public RealmResults<MyCity> getAll();
    public void save(MyCity myCity);
    public void remove(long id);

}
