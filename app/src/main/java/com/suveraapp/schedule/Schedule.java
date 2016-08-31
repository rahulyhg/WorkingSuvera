package com.suveraapp.schedule;

/**
 * Created by Hibatop on 31/08/2016.
 */
public class Schedule {


    private int amount;


    private long calendar;

    public Schedule(int amount, long calendar){
        this.amount = amount;
        this.calendar = calendar;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCalendar() {
        return calendar;
    }

    public void setCalendar(long calendar) {
        this.calendar = calendar;
    }

}
