
package com.example.isoft.studyskadden.rest.pojo;

import com.google.gson.annotations.Expose;

public class Main {

    @Expose
    public Double temp;
    @Expose
    public Double pressure;
    @Expose
    public Integer humidity;
    @Expose
    public Double tempMin;
    @Expose
    public Double tempMax;

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }


}
