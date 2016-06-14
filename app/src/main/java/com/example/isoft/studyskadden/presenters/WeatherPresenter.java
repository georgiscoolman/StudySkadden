package com.example.isoft.studyskadden.presenters;

import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.models.WeatherModel;
import com.example.isoft.studyskadden.rest.pojo.ForecastDaily;
import com.example.isoft.studyskadden.ui.WeatherView;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by isoft on 07.06.16.
 */
public class WeatherPresenter extends BasePresenter<WeatherView>{

    private WeatherModel model;
    private Realm realm;

    @Inject
    public WeatherPresenter(WeatherModel model) {
        this.model = model;

    }

    public void onUpdate() {

        Observable<ForecastDaily> dailyObservable = model.request("Minsk");

        Observable observable = dailyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(pojoModel -> {
                    realm.beginTransaction();
                    MyCity city = new MyCity((ForecastDaily) pojoModel, realm);
                    realm.commitTransaction();
                    mMvpView.startUpdate();
                    mMvpView.showResponse(city);
                    mMvpView.stopUpdate();
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void attachView(WeatherView mvpView) {
        realm = Realm.getDefaultInstance();
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        realm.close();
        super.detachView();
    }

}
