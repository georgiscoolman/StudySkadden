package com.example.isoft.studyskadden.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.isoft.studyskadden.injection.ApplicationContext;
import com.example.isoft.studyskadden.models.OpenWeatherModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    OpenWeatherModel provideWeatherModel() {
        return new OpenWeatherModel();
    }

}