package com.example.isoft.studyskadden.ui;

import com.example.isoft.studyskadden.PreviewCityWeather;
import java.util.List;

/**
 * Created by isoft on 07.06.16.
 */
public interface WeatherView extends MvpView {

    public void startUpdate();

    public void refreshView(List<PreviewCityWeather> previewCityWeathers);

    public void stopUpdate();

    public void showMessage(int titleResId, String message);

}
