package com.suveraapp.objects;

/**
 * Created by Hibatop on 01/09/2016.
 */
public class Interval {

    private boolean interval; //boolean to represent interval selection of days for medicine
                              //default is false(everyday) and true represents on specific days

    public Interval(boolean interval){
        this.interval = interval;
    }

    public boolean isInterval() {
        return interval;
    }

    public void setInterval(boolean interval) {
        this.interval = interval;
    }

}
