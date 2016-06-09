package com.example.isoft.studyskadden.ui;

import com.example.isoft.studyskadden.entities.City;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherView extends MvpView {

    public void showResponse(City city);

    public void stopUpdate();

}
