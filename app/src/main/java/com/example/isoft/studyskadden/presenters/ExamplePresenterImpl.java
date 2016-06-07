package com.example.isoft.studyskadden.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.isoft.studyskadden.models.ExampleModel;
import com.example.isoft.studyskadden.views.ExampleView;

/**
 * Created by isoft on 07.06.16.
 */
public class ExamplePresenterImpl implements ExamplePresenter{

    private final ExampleModel model;
    private final ExampleView view;

    public ExamplePresenterImpl(ExampleModel model, ExampleView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onUpdate() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                
                view.showResponse(model.request());
                view.stopUpdate();

            }
        }, 1000);

    }
}
