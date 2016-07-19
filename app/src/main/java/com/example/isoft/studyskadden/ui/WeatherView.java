package com.example.isoft.studyskadden.ui;

import com.example.isoft.studyskadden.PreviewCityWeather;

import java.util.List;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherView extends MvpView {

    public void startUpdate();

    public void stopUpdate();

    public void showMessage(String title, String message);

    public void addCity(PreviewCityWeather previewCityWeather);

    public void removeCity(Long cityId);
}
