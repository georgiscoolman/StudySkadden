package com.example.isoft.studyskadden.entities;

import com.example.isoft.studyskadden.rest.pojo.PojoModel;
import com.example.isoft.studyskadden.rest.pojo.Sys;
import com.example.isoft.studyskadden.rest.pojo.Weather;

import java.util.List;


public class City implements BaseEntity{

    private Long id;
    private String name;
    private String country;
    private MyWeather myWeather;

    public City() {
    }

    public City(PojoModel model){
        if (model!=null) {
            this.id = model.getId().longValue();
            this.name = model.getName();

            Sys sys = model.getSys();

            if (sys!=null){
                this.country = sys.getCountry();
            }

            this.myWeather = new MyWeather(model);

        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public MyWeather getMyWeather() {
        return myWeather;
    }

    @Override
    public String toString() {
        return "id " + id + " name " + name + " country " + country;
    }

}
