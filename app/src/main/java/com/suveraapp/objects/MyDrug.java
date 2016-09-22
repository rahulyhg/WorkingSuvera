package com.suveraapp.objects;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MyDrug extends RealmObject {

    //my drug stuff for realm database
    private String myDrugName, myDrugUrl, myReason;
    private boolean myInterval;
    private RealmList<RealmInteger> myDays;
    private RealmList<Schedule> mySchedule;

    @PrimaryKey
    private int myDrugID;

    public MyDrug() {
    }

    public MyDrug(Drug drug, Reason reason, Interval interval, Days days, ArrayList<Schedule> schedules) {
        myDrugName = drug.getName();
        myDrugID = drug.getId();
        myDrugUrl = drug.getUrl();
        myReason = reason.getReason();
        myInterval = interval.isInterval();

        //realmlist makes this look unclean as hell
        //anyways just to create a list of selected days
        // 1 - selected 0 - not selected
        boolean[] temp = days.getDays();
        RealmInteger tempInteger;
        myDays = new RealmList<>();
        for (int i = 0; i < temp.length; i++) {
            tempInteger = new RealmInteger();
            //sets the realminteger object equals to the state of the element in the boolean array
            if (temp[i]) {
                tempInteger.setDay(1);
            } else {
                tempInteger.setDay(0);
            }
            myDays.add(i, tempInteger);
        }

        //creates a list of schedules appropriately
        mySchedule = new RealmList<>();
        for (Schedule schedule : schedules) {
            mySchedule.add(schedule);
        }
    }

    public boolean isMyInterval() {
        return myInterval;
    }

    public void setMyInterval(boolean myInterval) {
        this.myInterval = myInterval;
    }

    public String getMyDrugName() {
        return myDrugName;
    }

    public void setMyDrugName(String myDrugName) {
        this.myDrugName = myDrugName;
    }

    public String getMyReason() {
        return myReason;
    }

    public void setMyReason(String myReason) {
        this.myReason = myReason;
    }

    public String getMyDrugUrl() {
        return myDrugUrl;
    }

    public void setMyDrugUrl(String myDrugUrl) {
        this.myDrugUrl = myDrugUrl;
    }

    public RealmList<Schedule> getMySchedule() {
        return mySchedule;
    }

    public void setMySchedule(RealmList<Schedule> mySchedule) {
        this.mySchedule = mySchedule;
    }

    public RealmList<RealmInteger> getMyDays() {
        return myDays;
    }

    public void setMyDays(RealmList<RealmInteger> myDays) {
        this.myDays = myDays;
    }

    public int getMyDrugID() {
        return myDrugID;
    }

    public void setMyDrugID(int myDrugID) {
        this.myDrugID = myDrugID;
    }

}
