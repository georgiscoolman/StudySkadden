
package com.example.isoft.studyskadden.rest.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ForecastDaily {

    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Double message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public List<WeatherDaily> list = new ArrayList<WeatherDaily>();

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public Double getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<WeatherDaily> getList() {
        return list;
    }
}
