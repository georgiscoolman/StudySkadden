
package com.example.isoft.studyskadden.rest.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Temp {

    @SerializedName("day")
    @Expose
    public Double day;
    @SerializedName("min")
    @Expose
    public Double min;
    @SerializedName("max")
    @Expose
    public Double max;
    @SerializedName("night")
    @Expose
    public Double night;
    @SerializedName("eve")
    @Expose
    public Double eve;
    @SerializedName("morn")
    @Expose
    public Double morn;

    public Double getDay() {
        return day;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getNight() {
        return night;
    }

    public Double getEve() {
        return eve;
    }

    public Double getMorn() {
        return morn;
    }
}
