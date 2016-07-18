package com.example.isoft.studyskadden.models;

import android.os.Looper;
import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.realm.MyCityRealmManager;
import com.example.isoft.studyskadden.rest.RestApi;
import com.example.isoft.studyskadden.rest.RetrofitServiceFactory;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public class OpenWeatherModel implements WeatherModel {
    private MyCityRealmManager myCityRealmManager;
    private Realm realm;

    private final static String TAG = "Threading";

    @Override
    public Observable<PreviewCityWeather> request(String name) {
        RestApi weatherService = RetrofitServiceFactory.getInstance();
        return weatherService
                .getWheatherReportByCityName(name)
                .map(forecastDaily ->
                {
                    Log.d(TAG, "request parcing & saving & building preview" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    MyCity myCity = new MyCity(forecastDaily);
                    RealmObject savedCity = myCityRealmManager.save(Realm.getDefaultInstance(), myCity);
                    PreviewCityWeather previewCityWeather = new PreviewCityWeather(savedCity);
                    return previewCityWeather;
                });
    }

    @Override
    public Observable<PreviewCityWeather> request(long id) {
        RestApi weatherService = RetrofitServiceFactory.getInstance();
        return weatherService
                .getWheatherReportByCityId(id)
                .map(forecastDaily ->
                {
                    Log.d(TAG, "request parcing & saving & building preview" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    MyCity myCity = new MyCity(forecastDaily);
                    RealmObject savedCity = myCityRealmManager.save(Realm.getDefaultInstance(), myCity);
                    PreviewCityWeather previewCityWeather = new PreviewCityWeather(savedCity);
                    return previewCityWeather;
                });
    }

    @Override
    public RealmResults<MyCity> getAll() {
        Log.d(TAG, "getAll" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        return myCityRealmManager.getAll(realm);
    }

    @Override
    public void remove(long id) {
        Log.d(TAG, "remove" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        myCityRealmManager.remove(realm, id);
    }

    @Override
    public void init() {
        Log.d(TAG, "init" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        realm = Realm.getDefaultInstance();
        myCityRealmManager = new MyCityRealmManager();
    }

    @Override
    public void closeDBconnection() {
        Log.d(TAG, "closeDBconnection" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        realm.close();
    }

    public boolean isItemSaved(long id){
        return myCityRealmManager.isIdExist(realm,id);
    }
}
