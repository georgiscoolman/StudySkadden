package com.example.isoft.studyskadden;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isoft.studyskadden.entities.MyCity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Georg on 22.04.2016.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CityWeatherViewHolder>{

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

    public void updateItems(List<PreviewCityWeather> cityWeathers){
        cityWeathers.clear();
        cityWeathers.addAll(cityWeathers);
        notifyDataSetChanged();
    }

    public void addItem(PreviewCityWeather newItem){
        int newItemPosition = -1;
        long insertCityId = newItem.getCity().getId();
        Log.d("CitiesAdapter", "insertCityId " + insertCityId);

        for (PreviewCityWeather cityWeather : cityWeathers) {
            if (cityWeather.getCity().getId() == insertCityId){
                newItemPosition = cityWeathers.indexOf(cityWeather);
                break;
            }
        }

        Log.d("CitiesAdapter", "adding Item at position " + newItemPosition);

        if (newItemPosition != -1){
            cityWeathers.set(newItemPosition, newItem);
            notifyItemChanged(newItemPosition);
        }else {
            cityWeathers.add(newItem);
            notifyItemInserted(cityWeathers.indexOf(newItem));
        }

    }

    public void dropItem(int position) {
        cityWeathers.remove(position);
        notifyItemRemoved(position);
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cityWeathers, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cityWeathers, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public boolean isItemInList(long id){
        boolean isItemInList = false;

        for (PreviewCityWeather cityWeather : cityWeathers) {
            if (cityWeather.getCity().getId() == id){
                isItemInList = true;
            }
        }

        return isItemInList;
    }

}
