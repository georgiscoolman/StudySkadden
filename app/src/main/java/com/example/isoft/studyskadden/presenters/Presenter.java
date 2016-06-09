package com.example.isoft.studyskadden.presenters;

import com.example.isoft.studyskadden.ui.MvpView;

public interface Presenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

}
