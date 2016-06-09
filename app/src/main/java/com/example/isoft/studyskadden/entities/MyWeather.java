package com.example.isoft.studyskadden.entities;

import com.example.isoft.studyskadden.rest.pojo.Main;
import com.example.isoft.studyskadden.rest.pojo.PojoModel;
import com.example.isoft.studyskadden.rest.pojo.Weather;
import com.example.isoft.studyskadden.rest.pojo.Wind;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyWeather implements BaseEntity{

    public SimpleDateFormat sdfmad = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private Long id;
    private Date date;
    private String description;
    private String icon;
    private Double temp;
    private Double tempMax;
    private Double tempMin;
    private Double pressure;
    private Integer humidity;
    private Double windSpeed;

    public MyWeather() {
    }

    public MyWeather(PojoModel model){

        if (model!= null){

            Main main = model.getMain();

            date = new Date (model.getDt()*1000L);

            if (main != null){
                temp = main.getTemp();
                tempMax = main.getTempMax();
                tempMin = main.getTempMin();
                pressure = main.getPressure();
                humidity = main.getHumidity();
            }

            Wind wind = model.getWind();

            if (wind!=null){
                windSpeed = wind.getSpeed();
            }

            Weather weather = model.getWeather().get(0);
            if (weather!=null){
                description = weather.getDescription();
                icon = weather.getIcon();
            }

        }

    }

    public Long getId() {
        return id;
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

    public Double getTemp() {
        return temp;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public Double getTempMin() {
        return tempMin;
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
        return "id " + id + " date " + getDateReadable() + " temp " + temp + " tempMax " + tempMax + " tempMin " + tempMin + " pressure "
                + pressure + " humidity " + humidity + " windSpeed " + windSpeed + " description " + description + " icon " + icon;
    }

}
