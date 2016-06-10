package com.example.isoft.studyskadden.ui;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.entities.City;
import com.example.isoft.studyskadden.entities.MyWeather;
import com.example.isoft.studyskadden.presenters.WeatherPresenter;
import com.example.isoft.studyskadden.rest.RestApi;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends BaseActivity implements WeatherView, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    WeatherPresenter weatherPresenter;

    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.city_country) TextView cityCountry;
    @BindView(R.id.temp) TextView temp;
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.temp_min) TextView tempMin;
    @BindView(R.id.temp_max) TextView tempMax;
    @BindView(R.id.descr) TextView description;
    @BindView(R.id.pressure) TextView pressure;
    @BindView(R.id.humidity) TextView humidity;
    @BindView(R.id.wind) TextView wind;
    @BindView(R.id.date) TextView date;

    private static final String WEB_IMAGES_FORMAT = "%s/img/w/%s.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        weatherPresenter.attachView(this);

        weatherPresenter.onUpdate();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
         weatherPresenter.onUpdate();
    }

    @Override
    public void showResponse(City city) {
        if (city != null){
            cityCountry.setVisibility(View.VISIBLE);
            cityCountry.setText(String.format(this.getString(R.string.city_country_format), city.getName(), city.getCountry()));

            MyWeather weather = city.getMyWeather();

            if (weather != null){
                Double tmp = weather.getTemp();
                if (tmp!=null) {
                    temp.setVisibility(View.VISIBLE);
                    temp.setText(String.format(getString(R.string.temp_format), String.valueOf(Math.round(tmp))));
                }else {
                    temp.setVisibility(View.GONE);
                }

                String url = String.format(WEB_IMAGES_FORMAT, RestApi.URL, weather.getIcon());
                Glide.with(this).load(url).into(icon);


                Double tmpMax = weather.getTemp();
                if (tmpMax!=null) {
                    tempMax.setVisibility(View.VISIBLE);
                    tempMax.setText(String.format(getString(R.string.max_temp_format), String.valueOf(Math.round(tmpMax))));
                }else {
                    tempMax.setVisibility(View.GONE);
                }

                Double tmpMin = weather.getTemp();
                if (tmpMin!=null) {
                    tempMin.setVisibility(View.VISIBLE);
                    tempMin.setText(String.format(getString(R.string.min_temp_format), String.valueOf(Math.round(tmpMin))));
                }else {
                    tempMin.setVisibility(View.GONE);
                }

                description.setText(weather.getDescription());

                Double prs = weather.getPressure();
                if (prs!=null) {
                    pressure.setVisibility(View.VISIBLE);
                    pressure.setText(String.format(getString(R.string.pressure_format), String.valueOf(Math.round(prs))));
                }else {
                    pressure.setVisibility(View.GONE);
                }

                humidity.setText(String.format(getString(R.string.humidity_format), String.valueOf(weather.getHumidity())));

                Double wnd = weather.getWindSpeed();
                if (wnd!=null) {
                    wind.setVisibility(View.VISIBLE);
                    wind.setText(String.format(getString(R.string.wind_format), String.valueOf(Math.round(wnd))));
                }else {
                    wind.setVisibility(View.GONE);
                }

                date.setText(weather.getDateReadable());
            }
        }
        else {
            cityCountry.setVisibility(View.GONE);
        }

    }

    @Override
    public void startUpdate() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopUpdate() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.city_name));
        //searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_refresh){
            weatherPresenter.onUpdate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.detachView();
    }
}
