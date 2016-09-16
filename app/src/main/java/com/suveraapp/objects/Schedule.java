package com.suveraapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Schedule extends RealmObject implements Parcelable {

    private long dosage; //dosage of medication needed to take
    private long time; // time for alarm stored in milliseconds

    public Schedule(){}
    public Schedule(int dosage, long time) {
        this.dosage = dosage;
        this.time = time;
    }

    private Schedule(Parcel in) {
        dosage = in.readInt();
        time = in.readLong();
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int i) {
            return new Schedule[0];
        }
    };

    public long getDosage() {
        return dosage;
    }

    public void setDosage(Long dosage) {
        this.dosage = dosage;
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
