package com.example.isoft.studyskadden.injection.component;

import com.example.isoft.studyskadden.injection.module.ApplicationModule;
import com.example.isoft.studyskadden.ui.WeatherActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {

    void inject(WeatherActivity weatherActivity);

}