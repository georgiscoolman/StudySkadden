package com.example.isoft.studyskadden;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isoft.studyskadden.entities.MyCity;

import java.util.List;

/**
 * Created by Georg on 22.04.2016.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CityWeatherViewHolder> {

    private  List<PreviewCityWeather> cityWeathers;
    private final LayoutInflater mInflater;
    private Context context;

    public CitiesAdapter(Context context, List<PreviewCityWeather> cityWeathers) {
        this.cityWeathers = cityWeathers;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CityWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.weather_item, parent, false);
        return new CityWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityWeatherViewHolder holder, int position) {
        final PreviewCityWeather item = cityWeathers.get(position);
        holder.bind(item,context);
    }

    @Override
    public int getItemCount() {
        return cityWeathers.size();
    }

    @Override
    public long getItemId(int position) {

        PreviewCityWeather item = cityWeathers.get(position);

        MyCity city = item.getCity();

        return city.getId();
    }

    public void setCityWeathers(List<PreviewCityWeather> cityWeathers) {
        this.cityWeathers = cityWeathers;
        notifyDataSetChanged();
    }

}
