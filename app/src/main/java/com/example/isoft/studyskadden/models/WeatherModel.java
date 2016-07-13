package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherModel {

    public Observable<MyCity> request(String name);
    public Observable<MyCity> request(long id);
    public RealmResults<MyCity> getAll();
    public RealmObject save(MyCity myCity);
    public void remove(long id);
    public void closeDBconnection();
    public void init();
    public boolean isItemSaved(long id);
}
