package com.example.isoft.studyskadden.viewHolders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.rest.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Georg on 22.04.2016.
 */
public class CityWeatherViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

    private static final String WEB_IMAGES_FORMAT = "%s/img/w/%s.png";

    @BindView(R.id.card_weather) CardView mCardView;
    @BindView(R.id.linear_content) LinearLayout mLinearLayoutContent;
    @BindView(R.id.text_city_country) TextView mTextViewCityCountry;
    @BindView(R.id.text_temp_day) TextView mTextViewTempDay;
    @BindView(R.id.image_icon) ImageView mImageViewIcon;
    @BindView(R.id.text_temp_evening) TextView mTextViewTempEvening;
    @BindView(R.id.text_temp_morning) TextView mTextViewTempMorning;
    @BindView(R.id.text_description) TextView mTextViewDescription;
    @BindView(R.id.text_pressure) TextView mTextViewPressure;
    @BindView(R.id.text_humidity) TextView mTextViewHumidity;
    @BindView(R.id.text_wind) TextView mTextViewWind;
    @BindView(R.id.text_date) TextView mTextViewDate;

    public CityWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(PreviewCityWeather item, Context context) {

        setTextValues(context, mTextViewCityCountry, R.string.city_country_format, item.name, item.country);
        setTextValues(context, mTextViewTempDay, R.string.temp_format, Math.round(item.tempDay));

        String url = String.format(WEB_IMAGES_FORMAT, RestApi.URL, item.icon);
        Glide.with(context).load(url).into(mImageViewIcon);

        setTextValues(context, mTextViewTempMorning, R.string.max_temp_format, Math.round(item.tempMorning));
        setTextValues(context, mTextViewTempEvening, R.string.min_temp_format, Math.round(item.tempEvening));

        setTextValue(mTextViewDescription, item.description);

        setTextValues(context, mTextViewPressure, R.string.pressure_format, item.pressure);
        setTextValues(context, mTextViewHumidity, R.string.humidity_format, item.humidity);
        setTextValues(context, mTextViewWind, R.string.wind_format, item.wind);

        setTextValue(mTextViewDate, item.date);
    }

    private void setTextValues(Context context, TextView textView, int formatStringResId, Object... values){
        if (values!=null) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.format(context.getString(formatStringResId), values));
        }else {
            textView.setVisibility(View.GONE);
        }
    }

    private void setTextValue(TextView textView, String value){
        if (value!=null) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(value);
        }else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected() {
        mLinearLayoutContent.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        mLinearLayoutContent.setBackgroundColor(0);
    }
}
