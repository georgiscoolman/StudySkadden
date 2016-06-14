package com.example.isoft.studyskadden.entities;

import com.example.isoft.studyskadden.rest.pojo.City;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;
import com.example.isoft.studyskadden.rest.pojo.WeatherDaily;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class MyCity extends RealmObject {

    @PrimaryKey
    private Long id;
    private String name;
    private String country;
    private RealmList<MyWeather> weatherLog;

    public MyCity() {
    }

    public MyCity(ForecastDaily forecast){
        if (forecast!=null) {

            City city = forecast.getCity();

            if (city != null){
                this.id = city.getId().longValue();
                this.name = city.getName();
                this.country = city.getCountry();
            }

            if (weatherLog == null)
            weatherLog = new RealmList<MyWeather>();

            List<WeatherDaily> weatherList = forecast.getList();

            for (WeatherDaily weatherDaily : weatherList) {
                weatherLog.add(new MyWeather(weatherDaily));
            }
        }
    }

    public MyCity(ForecastDaily forecast, Realm realm){
        this(forecast);
        if (realm != null){

            // need to drop old weather log if it exist
            MyCity oldInstance = realm.where(MyCity.class).equalTo("id", this.id).findFirst();
            if (oldInstance !=null) {
                RealmList<MyWeather> oldLog = (RealmList<MyWeather>) oldInstance.getWeatherLog();
                if (oldLog != null)
                    oldLog.deleteAllFromRealm();
            }

            realm.copyToRealmOrUpdate(this);
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

    public List<MyWeather> getWeatherLog() {
        return weatherLog;
    }

    @Override
    public String toString() {
        return "id " + id + " name " + name + " country " + country;
    }

}
