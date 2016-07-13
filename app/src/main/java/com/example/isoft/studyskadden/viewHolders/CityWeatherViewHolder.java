package com.example.isoft.studyskadden.viewHolders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.entities.MyCity;
import com.example.isoft.studyskadden.entities.MyWeather;
import com.example.isoft.studyskadden.rest.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Georg on 22.04.2016.
 */
public class CityWeatherViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

    private static final String WEB_IMAGES_FORMAT = "%s/img/w/%s.png";

    @BindView(R.id.root) CardView root;
    @BindView(R.id.content) LinearLayout content;
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

    public CityWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(PreviewCityWeather item, Context context) {
        MyCity city = item.getCity();
        MyWeather weather = item.getLastWeather();

        if (city != null){
            cityCountry.setVisibility(View.VISIBLE);
            cityCountry.setText(String.format(context.getString(R.string.city_country_format), city.getName(), city.getCountry()));

            if (weather != null){
                Double tmp = weather.getTempMorning();
                if (tmp!=null) {
                    temp.setVisibility(View.VISIBLE);
                    temp.setText(String.format(context.getString(R.string.temp_format), String.valueOf(Math.round(tmp))));
                }else {
                    temp.setVisibility(View.GONE);
                }

                String url = String.format(WEB_IMAGES_FORMAT, RestApi.URL, weather.getIcon());
                Glide.with(context).load(url).into(icon);


                Double tmpMax = weather.getTempMorning();
                if (tmpMax!=null) {
                    tempMax.setVisibility(View.VISIBLE);
                    tempMax.setText(String.format(context.getString(R.string.max_temp_format), String.valueOf(Math.round(tmpMax))));
                }else {
                    tempMax.setVisibility(View.GONE);
                }

                Double tmpMin = weather.getTempMorning();
                if (tmpMin!=null) {
                    tempMin.setVisibility(View.VISIBLE);
                    tempMin.setText(String.format(context.getString(R.string.min_temp_format), String.valueOf(Math.round(tmpMin))));
                }else {
                    tempMin.setVisibility(View.GONE);
                }

                description.setText(weather.getDescription());

                Double prs = weather.getPressure();
                if (prs!=null) {
                    pressure.setVisibility(View.VISIBLE);
                    pressure.setText(String.format(context.getString(R.string.pressure_format), String.valueOf(Math.round(prs))));
                }else {
                    pressure.setVisibility(View.GONE);
                }

                humidity.setText(String.format(context.getString(R.string.humidity_format), String.valueOf(weather.getHumidity())));

                Double wnd = weather.getWindSpeed();
                if (wnd!=null) {
                    wind.setVisibility(View.VISIBLE);
                    wind.setText(String.format(context.getString(R.string.wind_format), String.valueOf(Math.round(wnd))));
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
    public void onItemSelected() {
        content.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        content.setBackgroundColor(0);
    }
}
