package com.suveraapp.objects;

/**
 * Created by Hibatop on 31/08/2016.
 */
public class Schedule {

    private int amount; //amount of medication needed to take
    private long time; // time for alarm stored in milliseconds

    public Schedule(int amount, long time){
        this.amount = amount;
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
