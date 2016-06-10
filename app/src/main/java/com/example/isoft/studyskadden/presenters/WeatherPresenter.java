package com.example.isoft.studyskadden.presenters;

import com.example.isoft.studyskadden.entities.City;
import com.example.isoft.studyskadden.models.WeatherModel;
import com.example.isoft.studyskadden.rest.pojo.PojoModel;
import com.example.isoft.studyskadden.ui.WeatherView;

import javax.inject.Inject;

import rx.Observable;
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

    public void onUpdate() {

        Observable<PojoModel> pojoModelObservable = model.request("Minsk");

        Observable observable = pojoModelObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(pojoModel -> {
                    City city = new City((PojoModel) pojoModel);
                    mMvpView.showResponse(city);
                    mMvpView.stopUpdate();
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void detachView() {
        if (mSubscriptions.hasSubscriptions() && !mSubscriptions.isUnsubscribed()) {
            mMvpView.stopUpdate();
        }
        super.detachView();
    }

}
