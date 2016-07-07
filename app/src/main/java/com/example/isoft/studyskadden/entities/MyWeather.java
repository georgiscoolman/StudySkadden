package com.example.isoft.studyskadden.entities;

import com.example.isoft.studyskadden.rest.pojo.Temp;
import com.example.isoft.studyskadden.rest.pojo.Weather;
import com.example.isoft.studyskadden.rest.pojo.WeatherDaily;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class MyWeather extends RealmObject{

    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String ICON = "icon";
    public static final String TEMP_MORNING = "tempMorning";
    public static final String TEMP_DAY = "tempDay";
    public static final String TEMP_EVENING = "tempEvening";
    public static final String PRESSURE = "pressure";
    public static final String HUMIDITY = "humidity";
    public static final String WIND_SPEED = "windSpeed";

    @Ignore
    public SimpleDateFormat sdfmad = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private Date date;
    private String description;
    private String icon;
    private Double tempMorning;
    private Double tempDay;
    private Double tempEvening;
    private Double pressure;
    private Integer humidity;
    private Double windSpeed;

    public MyWeather() {
    }

    public MyWeather(WeatherDaily daily){

        if (daily!= null){

            date = new Date (daily.getDt()*1000L);

            Temp temp = daily.getTemp();

            if (temp != null){
                this.tempMorning = temp.getMorn();
                this.tempDay = temp.getDay();
                this.tempEvening = temp.getEve();
            }

            this.pressure = daily.getPressure();
            this.humidity = daily.getHumidity();
            this.windSpeed = daily.getSpeed();

            Weather weather = daily.getWeather().get(0);
            if (weather!=null){
                description = weather.getDescription();
                icon = weather.getIcon();
            }

        }

    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Double getTempMorning() {
        return tempMorning;
    }

    public Double getTempDay() {
        return tempDay;
    }

    public Double getTempEvening() {
        return tempEvening;
    }

    public Double getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public String getDateReadable(){
        sdfmad.setTimeZone(TimeZone.getDefault());
        return sdfmad.format(date);
    }

    @Override
    public String toString() {
        return " date " + getDateReadable() + " tempMorning " + tempMorning + " tempDay " + tempDay + " tempEvening " + tempEvening + " pressure "
                + pressure + " humidity " + humidity + " windSpeed " + windSpeed + " description " + description + " icon " + icon;
    }

}
