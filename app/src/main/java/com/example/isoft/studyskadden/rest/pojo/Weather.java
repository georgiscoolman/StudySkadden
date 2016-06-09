
package com.example.isoft.studyskadden.rest.pojo;

import com.google.gson.annotations.Expose;

public class Weather {

    @Expose
    public Integer id;
    @Expose
    public String main;
    @Expose
    public String description;
    @Expose
    public String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
