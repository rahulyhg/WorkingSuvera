package com.suveraapp.objects;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ScheduleList extends RealmObject {


    @PrimaryKey
    private int myDrugID;

    private RealmList<Schedule> list;

    public ScheduleList(){
    }
    public ScheduleList(int myDrugID,ArrayList<Schedule> schedules){
        this.myDrugID=myDrugID;
        list = new RealmList<Schedule>();
        for(Schedule schedule : schedules){
            list.add(schedule);
        }
    }

    public int getMyDrugID() {
        return myDrugID;
    }

    public void setMyDrugID(int myDrugID) {
        this.myDrugID = myDrugID;
    }

    public RealmList<Schedule> getList() {
        return list;
    }

    public void setList(RealmList<Schedule> list) {
        this.list = list;
    }

}
