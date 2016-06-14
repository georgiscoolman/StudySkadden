package com.example.isoft.studyskadden.ui;

import com.example.isoft.studyskadden.entities.MyCity;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherView extends MvpView {

    public void startUpdate();

    public void showResponse(MyCity city);

    public void stopUpdate();

}
