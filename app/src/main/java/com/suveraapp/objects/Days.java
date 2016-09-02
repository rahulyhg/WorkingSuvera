package com.suveraapp.objects;

/**
 * Created by Hibatop on 01/09/2016.
 */
public class Days {

    private boolean [] days = new boolean[7]; //selected days of the week represented as booleans

    public Days(boolean [] days){
        this.days = days;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

}
