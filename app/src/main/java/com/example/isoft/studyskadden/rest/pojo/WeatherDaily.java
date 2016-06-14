
package com.example.isoft.studyskadden.rest.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class WeatherDaily {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("temp")
    @Expose
    public Temp temp;
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("weather")
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("deg")
    @Expose
    public Integer deg;
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("rain")
    @Expose
    public Double rain;

    public Integer getDt() {
        return dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public Integer getClouds() {
        return clouds;
    }

    public Double getRain() {
        return rain;
    }
}
