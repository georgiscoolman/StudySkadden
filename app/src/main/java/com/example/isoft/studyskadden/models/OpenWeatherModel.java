package com.example.isoft.studyskadden.models;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.realm.MyCityRealmManager;
import com.example.isoft.studyskadden.rest.RestApi;
import com.example.isoft.studyskadden.rest.RetrofitServiceFactory;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by isoft on 07.06.16.
 */
public class OpenWeatherModel implements WeatherModel {
    private MyCityRealmManager myCityRealmManager;
    private Realm realm;

    public OpenWeatherModel() {
        realm = Realm.getDefaultInstance();
        myCityRealmManager = new MyCityRealmManager();
    }

    @Override
    public Observable<ForecastDaily> request(String name) {

        RestApi weatherService = RetrofitServiceFactory.getInstance();

        return weatherService.getWheatherReportByCityName(name);

    }

    @Override
    public Observable<ForecastDaily> request(long id) {

        RestApi weatherService = RetrofitServiceFactory.getInstance();

        return weatherService.getWheatherReportByCityId(id);
    }

    @Override
    public void closeDBconnection() {
        realm.close();
    }

    @Override
    public RealmResults<MyCity> getAll() {
        return myCityRealmManager.getAll(realm);
    }

    @Override
    public void save(MyCity myCity) {
        myCityRealmManager.save(realm,myCity);

    }

    @Override
    public void remove(long id) {

    }
}
