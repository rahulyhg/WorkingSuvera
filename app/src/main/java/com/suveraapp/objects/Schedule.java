package com.suveraapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hibatop on 31/08/2016.
 */
public class Schedule implements Parcelable {

    private long amount; //amount of medication needed to take
    private long time; // time for alarm stored in milliseconds

    public Schedule(int amount, long time){
        this.amount = amount;
        this.time = time;
    }

    private Schedule(Parcel in){
        amount = in.readInt();
        time = in.readLong();
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>(){
        public Schedule createFromParcel(Parcel in){
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int i) {
            return new Schedule[0];
        }
    };

    public long getAmount() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
