package com.example.isoft.studyskadden;

import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class StudySkaddenApp extends Application {

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

}
