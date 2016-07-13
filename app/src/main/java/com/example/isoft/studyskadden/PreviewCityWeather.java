package com.example.isoft.studyskadden;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.entities.MyWeather;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Georg on 22.04.2016.
 */
public class PreviewCityWeather {

    public Long id;
    public String name;
    public String country;
    public Double tempDay;
    public String icon;
    public Double tempEvening;
    public Double tempMorning;
    public String description;
    public Double pressure;
    public Integer humidity;
    public Double wind;
    public String date;

    public PreviewCityWeather(RealmObject city) {

        MyCity myCity = ((MyCity) city);
        this.id = myCity.getId();
        this.name = myCity.getName();
        this.country = myCity.getCountry();

        RealmList<MyWeather> weathers = (RealmList<MyWeather>) myCity.getWeatherLog();
        MyWeather lastWeather = weathers.sort(MyWeather.DATE).first();

        this.tempDay = lastWeather.getTempDay();
        this.icon = lastWeather.getIcon();
        this.tempEvening = lastWeather.getTempEvening();
        this.tempMorning = lastWeather.getTempMorning();
        this.description = lastWeather.getDescription();
        this.pressure = lastWeather.getPressure();
        this.humidity = lastWeather.getHumidity();
        this.wind = lastWeather.getWindSpeed();
        this.date = lastWeather.getDateReadable();

    }

    public static ArrayList<PreviewCityWeather> fromList(RealmResults<MyCity> cities){
        ArrayList<PreviewCityWeather> result = new ArrayList<>();

        for (MyCity city : cities) {
            result.add(new PreviewCityWeather(city));
        }

        return result;
    }
}
