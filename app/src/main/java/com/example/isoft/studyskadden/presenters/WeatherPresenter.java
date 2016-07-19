package com.example.isoft.studyskadden.presenters;

import android.os.Looper;
import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.models.OpenWeatherModel;
import com.example.isoft.studyskadden.ui.WeatherView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by isoft on 07.06.16.
 */
public class WeatherPresenter extends BasePresenter<WeatherView>{

    private OpenWeatherModel model;

    @Inject
    public WeatherPresenter(OpenWeatherModel model) {
        this.model = model;
    }

    private final static String ADD_CITY_SUBSCRIBER = "Thread add";
    private final static String REMOVE_CITY_SUBSCRIBER = "Thread remove";

    @Override
    public void attachView(WeatherView mvpView) {
        model.init();
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        super.detachView();
    }

    private Subscriber<PreviewCityWeather> getAddCitySubscriber(){

        Subscriber<PreviewCityWeather> addCitySubscriber = new Subscriber<PreviewCityWeather>() {
            @Override
            public void onStart() {
                Log.d(ADD_CITY_SUBSCRIBER, "onStart" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.startUpdate();
            }

            @Override
            public void onCompleted() {
                Log.d(ADD_CITY_SUBSCRIBER, "onCompleted" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.stopUpdate();

            }

            @Override
            public void onError(Throwable e) {
                mMvpView.stopUpdate();
                if (e!=null) {
                    Log.d(ADD_CITY_SUBSCRIBER, "onError " + e.toString() + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    mMvpView.showMessage(e.getClass().getName() ,e.getMessage());
                }
            }

            @Override
            public void onNext(PreviewCityWeather city) {
                Log.d(ADD_CITY_SUBSCRIBER, "onNext " + city.name + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.addCity(city);
            }
        };

        return addCitySubscriber;

    }

    private Subscriber<Long> getRemoveCitySubscriber(){

        Subscriber<Long> removeCitySubscriber = new Subscriber<Long>() {
            @Override
            public void onStart() {
                Log.d(REMOVE_CITY_SUBSCRIBER, "onStart" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.startUpdate();
            }

            @Override
            public void onCompleted() {
                Log.d(REMOVE_CITY_SUBSCRIBER, "onCompleted" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.stopUpdate();
            }

            @Override
            public void onError(Throwable e) {
                mMvpView.stopUpdate();
                if (e!=null) {
                    Log.d(REMOVE_CITY_SUBSCRIBER, "onError " + e.toString() + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    mMvpView.showMessage(e.getClass().getName() ,e.getMessage());
                }
            }

            @Override
            public void onNext(Long cityId) {
                Log.d(REMOVE_CITY_SUBSCRIBER, "onNext " + cityId + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.removeCity(cityId);
            }
        };

        return removeCitySubscriber;

    }

    public void refreshAllCities() {
        Observable<PreviewCityWeather> allCitiesObservable =  model
                .getAllId()
                .flatMap(ids -> Observable.from(ids))
                .flatMap(id -> model.request(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = allCitiesObservable.subscribe(getAddCitySubscriber());
        mSubscriptions.add(subscription);
    }

    public void requestCity(String query){
        Observable<PreviewCityWeather> requestObservable = model
                .request(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = requestObservable.subscribe(getAddCitySubscriber());
        mSubscriptions.add(subscription);
    }

    public void removeCity(Long id){
        Observable<Long> removeObservable = model.remove(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = removeObservable.subscribe(getRemoveCitySubscriber());
        mSubscriptions.add(subscription);
    }

    public void setCityList(){
        Observable<PreviewCityWeather> observable =  model.getAll()
                .flatMap(previewCityWeathers -> Observable.from(previewCityWeathers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getAddCitySubscriber());
        mSubscriptions.add(subscription);
    }
}
