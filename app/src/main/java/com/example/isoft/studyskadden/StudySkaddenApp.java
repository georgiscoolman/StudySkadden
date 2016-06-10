package com.example.isoft.studyskadden;

import android.app.Application;
import android.content.Context;

import com.example.isoft.studyskadden.injection.component.ApplicationComponent;
import com.example.isoft.studyskadden.injection.component.DaggerApplicationComponent;
import com.example.isoft.studyskadden.injection.module.ApplicationModule;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class StudySkaddenApp extends Application {

    private ApplicationComponent mApplicationComponent;

    public static StudySkaddenApp get(Context context) {
        return (StudySkaddenApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

}
