package com.example.isoft.studyskadden.presenters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.models.WeatherModel;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;
import com.example.isoft.studyskadden.ui.WeatherView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.inject.Inject;

import io.realm.RealmObject;
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

    private Subscriber<MyCity> getForecastObservable(){

        Subscriber<MyCity> forecastDailySubscriber = new Subscriber<MyCity>() {
            @Override
            public void onStart() {
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onStart");
                mMvpView.startUpdate();
            }

            @Override
            public void onCompleted() { // show results
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onCompleted");
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
            public void onNext(MyCity city) { // saving items
                Log.d(FORECAST_DAILY_SUBSCRIBER, "onNext " + city.getName());
                addCity(city);
            }
        };

        return forecastDailySubscriber;

    }

    public void refreshData() {
        RealmResults<MyCity> allCities =  model.getAll();
        LinkedList<Observable<MyCity>> observables = new LinkedList<>();

        for (MyCity city : allCities) {
            Observable<MyCity> dailyObservable = model.request(city.getId())
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
        Observable<MyCity> dailyObservable = model.request(query);

        Observable observable = dailyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(getForecastObservable());

        mSubscriptions.add(subscription);
    }

    public void removeCity(RecyclerView.ViewHolder viewHolder){
        model.remove(viewHolder.getItemId());
        mMvpView.removeCity(viewHolder.getAdapterPosition());
    }

    public void addCity(MyCity myCity){
        RealmObject savedCity = model.save(myCity);
        PreviewCityWeather previewCityWeather = new PreviewCityWeather(savedCity);
        EventBus.getDefault().post(previewCityWeather);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityAdded(PreviewCityWeather previewCityWeather) {
        mMvpView.addCity(previewCityWeather);
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
        EventBus.getDefault().register(this);
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        model.closeDBconnection();
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

}
