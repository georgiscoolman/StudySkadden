package com.example.isoft.studyskadden.entities;

import com.example.isoft.studyskadden.rest.pojo.PojoModel;
import com.example.isoft.studyskadden.rest.pojo.Sys;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class City extends RealmObject {

    @PrimaryKey
    private Long id;
    private String name;
    private String country;
    private RealmList<MyWeather> weatherLog;

    public City() {
    }

    public City(PojoModel model){
        if (model!=null) {
            this.id = model.getId().longValue();
            this.name = model.getName();

            Sys sys = model.getSys();

            if (sys!=null){
                this.country = sys.getCountry();
            }

            weatherLog = new RealmList<MyWeather>();
            weatherLog.add(new MyWeather(model));

        }
    }

    public City(PojoModel model, Realm realm){
        this(model);
        if (realm != null){
            realm.copyToRealm(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public List getWeatherLog() {
        return weatherLog;
    }

    @Override
    public String toString() {
        return "id " + id + " name " + name + " country " + country;
    }

}
