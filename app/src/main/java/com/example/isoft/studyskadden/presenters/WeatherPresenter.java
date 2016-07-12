package com.example.isoft.studyskadden.presenters;

import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.models.WeatherModel;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;
import com.example.isoft.studyskadden.ui.WeatherView;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by isoft on 07.06.16.
 */
public class WeatherPresenter extends BasePresenter<WeatherView>{

    private WeatherModel model;

    @Inject
    public WeatherPresenter(WeatherModel model) {
        this.model = model;
    }

    private final static String FORECAST_DAILY_SUBSCRIBER = "forecastDailySubscriber";

    private Subscriber<ForecastDaily> getForecastObservable(){

        Subscriber<ForecastDaily> forecastDailySubscriber = new Subscriber<ForecastDaily>() {
            @Override
            public void onStart() {
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onStart");
                mMvpView.startUpdate();
            }

            @Override
            public void onCompleted() { // show results
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onCompleted");
                updateAllView();
                mMvpView.stopUpdate();
            }

            @Override
            public void onError(Throwable e) {
                mMvpView.stopUpdate();
                if (e!=null) {
                    Log.d(FORECAST_DAILY_SUBSCRIBER, "onError " + e.toString());
                    mMvpView.showMessage(R.string.network_trouble ,e.getMessage());
                }
            }

            @Override
            public void onNext(ForecastDaily forecastDaily) { // saving items
                MyCity city = new MyCity(forecastDaily);
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onNext " + city.getName());
                model.save(city);
            }
        };

        return forecastDailySubscriber;

    }

    public void refreshData() {
        RealmResults<MyCity> allCities =  model.getAll();
        LinkedList<Observable<ForecastDaily>> observables = new LinkedList<>();

        for (MyCity city : allCities) {
            Observable<ForecastDaily> dailyObservable = model.request(city.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observables.add(dailyObservable);
        }

        Observable observable = Observable.merge(observables);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getForecastObservable());

        mSubscriptions.add(subscription);
    }

    public void requestCity(String query){
        Observable<ForecastDaily> dailyObservable = model.request(query);

        Observable observable = dailyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getForecastObservable());

        mSubscriptions.add(subscription);
    }

    public void removeCity(long id){
        model.remove(id);
        updateAllView();
    }

    public void updateAllView(){
        mMvpView.startUpdate();

        RealmResults<MyCity> allCities =  model.getAll();
        ArrayList<PreviewCityWeather> weathers = PreviewCityWeather.fromList(allCities);

        mMvpView.refreshView(weathers);
        mMvpView.stopUpdate();
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        model.closeDBconnection();
        super.detachView();
    }

}
