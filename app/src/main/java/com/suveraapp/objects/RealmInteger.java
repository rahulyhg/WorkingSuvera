package com.suveraapp.objects;

import io.realm.RealmObject;

public class RealmInteger extends RealmObject{

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int day;
}
