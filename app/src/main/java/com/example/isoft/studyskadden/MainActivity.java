package com.example.isoft.studyskadden;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.isoft.studyskadden.models.ExampleModel;
import com.example.isoft.studyskadden.models.ExampleModelImpl;
import com.example.isoft.studyskadden.presenters.ExamplePresenter;
import com.example.isoft.studyskadden.presenters.ExamplePresenterImpl;
import com.example.isoft.studyskadden.views.ExampleView;
import com.example.isoft.studyskadden.views.ExampleViewImpl;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ExamplePresenter examplePresenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_degr = (TextView) findViewById(R.id.degrees);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        ExampleViewHolder holder = new ExampleViewHolder(swipeRefreshLayout, tv_degr);

        ExampleModel exampleModel = new ExampleModelImpl();
        ExampleView exampleView = new ExampleViewImpl(holder);
        examplePresenter = new ExamplePresenterImpl(exampleModel, exampleView);

        examplePresenter.onUpdate();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {

         examplePresenter.onUpdate();

    }
}
