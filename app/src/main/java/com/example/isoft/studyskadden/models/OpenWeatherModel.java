package com.example.isoft.studyskadden.models;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.realm.MyCityRealmManager;
import com.example.isoft.studyskadden.rest.RestApi;
import com.example.isoft.studyskadden.rest.RetrofitServiceFactory;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by isoft on 07.06.16.
 */
public class OpenWeatherModel{
    private MyCityRealmManager myCityRealmManager;

    private final static String TAG = "Threading";

    public Observable<PreviewCityWeather> request(String name) {
        RestApi weatherService = RetrofitServiceFactory.getInstance();
        return weatherService
                .getWheatherReportByCityName(name)
                .map(forecastDaily -> getPreviewCityWeather(forecastDaily));
    }

    public Observable<PreviewCityWeather> request(long id) {
        RestApi weatherService = RetrofitServiceFactory.getInstance();
        return weatherService
                .getWheatherReportByCityId(id)
                .map(forecastDaily -> getPreviewCityWeather(forecastDaily));
    }

    private PreviewCityWeather getPreviewCityWeather(ForecastDaily forecastDaily) {
        Log.d(TAG, "parsing & saving & building preview" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        Realm realm = Realm.getDefaultInstance();
        MyCity myCity = new MyCity(forecastDaily);
        RealmObject savedCity = myCityRealmManager.save(realm, myCity);
        PreviewCityWeather previewCityWeather = new PreviewCityWeather(savedCity);
        realm.close();
        return previewCityWeather;
    }

    public Observable<List<PreviewCityWeather>> getAll() {
        return Observable.create(
            new Observable.OnSubscribe<List<PreviewCityWeather>>() {
                @Override
                public void call(Subscriber<? super List<PreviewCityWeather>> subscriber) {
                    subscriber.onStart();
                    Log.d(TAG, "getAll & building preview" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<MyCity> myCities = myCityRealmManager.getAll(realm);
                    ArrayList<PreviewCityWeather> res = PreviewCityWeather.fromList(myCities);
                    realm.close();
                    subscriber.onNext(res);
                    subscriber.onCompleted();
                }
            }
        );
    }

    public Observable<List<Long>> getAllId() {
        return Observable.create(
            new Observable.OnSubscribe<List<Long>>() {
                @Override
                public void call(Subscriber<? super List<Long>> subscriber) {
                    subscriber.onStart();
                    Log.d(TAG, "getAllId" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<MyCity> myCities = myCityRealmManager.getAll(realm);
                    ArrayList<Long> res = new ArrayList<Long>();
                    for (MyCity myCity : myCities) {
                        res.add(myCity.getId());
                    }
                    realm.close();
                    subscriber.onNext(res);
                    subscriber.onCompleted();
                }
            }
        );
    }

    public Observable<Long> remove(Long id) {
        return Observable.create(
            new Observable.OnSubscribe<Long>() {
                @Override
                public void call(Subscriber<? super Long> subscriber) {
                    subscriber.onStart();
                    Log.d(TAG, "remove" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    Realm realm = Realm.getDefaultInstance();
                    try {
                        myCityRealmManager.remove(realm, id);
                    }
                    catch (IllegalStateException ex){
                        subscriber.onError(ex);
                    }
                    realm.close();
                    subscriber.onNext(id);
                    subscriber.onCompleted();
                }
            }
        );
    }

    public void init() {
        Log.d(TAG, "init" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
        myCityRealmManager = new MyCityRealmManager();
    }
}
