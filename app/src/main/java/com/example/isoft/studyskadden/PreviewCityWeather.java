package com.example.isoft.studyskadden;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.entities.MyWeather;

import java.util.ArrayList;
import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Georg on 22.04.2016.
 */
public class PreviewCityWeather {

    private MyCity city;
    private MyWeather lastWeather;

    public PreviewCityWeather(MyCity city) {
        this.city = city;

        RealmList<MyWeather> weathers = (RealmList<MyWeather>) city.getWeatherLog();

        lastWeather = weathers.sort(MyWeather.DATE).first();
    }

    public MyCity getCity() {
        return city;
    }

    public MyWeather getLastWeather() {
        return lastWeather;
    }

    public static ArrayList<PreviewCityWeather> fromList(RealmResults<MyCity> cities){
        ArrayList<PreviewCityWeather> result = new ArrayList<>();

        for (MyCity city : cities) {
            result.add(new PreviewCityWeather(city));
        }

        return result;
    }
}
