package com.example.isoft.studyskadden.presenters;

import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.models.OpenWeatherModel;
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

    private OpenWeatherModel model;

    @Inject
    public WeatherPresenter(OpenWeatherModel model) {
        this.model = model;
    }

    private final static String FORECAST_DAILY_SUBSCRIBER = "Threading subscriber";

    private Subscriber<PreviewCityWeather> getPreviewSubscriber(){

        Subscriber<PreviewCityWeather> forecastDailySubscriber = new Subscriber<PreviewCityWeather>() {
            @Override
            public void onStart() {
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onStart" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.startUpdate();
            }

            @Override
            public void onCompleted() { // show results
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onCompleted" + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.stopUpdate();
            }

            @Override
            public void onError(Throwable e) {
                mMvpView.stopUpdate();
                if (e!=null) {
                    Log.d(FORECAST_DAILY_SUBSCRIBER, "onError " + e.toString() + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                    mMvpView.showMessage(R.string.trouble ,e.getMessage());
                }
            }

            @Override
            public void onNext(PreviewCityWeather city) { // saving items
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onNext " + city.name + (Looper.myLooper() == Looper.getMainLooper() ? " on UI" : " on OUT"));
                mMvpView.addCity(city);
            }
        };

        return forecastDailySubscriber;

    }

    public void refreshData() {
        RealmResults<MyCity> allCities =  model.getAll();
        LinkedList<Observable<PreviewCityWeather>> observables = new LinkedList<>();

        for (MyCity city : allCities) {
            Observable<PreviewCityWeather> dailyObservable = model.request(city.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observables.add(dailyObservable);
        }

        Observable observable = Observable.merge(observables);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getPreviewSubscriber());

        mSubscriptions.add(subscription);
    }

    public void requestCity(String query){
        Observable<PreviewCityWeather> dailyObservable = model.request(query);

        Observable observable = dailyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getPreviewSubscriber());

        mSubscriptions.add(subscription);
    }

    public void removeCity(RecyclerView.ViewHolder viewHolder){
        model.remove(viewHolder.getItemId());
        mMvpView.removeCity(viewHolder.getAdapterPosition());
    }

    public void setCityList(){
        mMvpView.startUpdate();

        RealmResults<MyCity> allCities =  model.getAll();
        ArrayList<PreviewCityWeather> weathers = PreviewCityWeather.fromList(allCities);

        mMvpView.setCityList(weathers);
        mMvpView.stopUpdate();
    }

    @Override
    public void attachView(WeatherView mvpView) {
        model.init();
        //EventBus.getDefault().register(this);
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        model.closeDBconnection();
        //EventBus.getDefault().unregister(this);
        super.detachView();
    }

}
