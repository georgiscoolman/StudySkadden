
package com.example.isoft.studyskadden.rest.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Coord {

    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("lat")
    @Expose
    public Double lat;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
