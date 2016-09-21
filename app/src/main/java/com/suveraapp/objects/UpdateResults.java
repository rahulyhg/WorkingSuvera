package com.suveraapp.objects;

import io.realm.RealmObject;

public class UpdateResults extends RealmObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDosage() {
        return dosage;
    }

    public void setDosage(long dosage) {
        this.dosage = dosage;
    }

    public int getMyDrugID() {
        return myDrugID;
    }

    public void setMyDrugID(int myDrugID) {
        this.myDrugID = myDrugID;
    }

    private String name;
    private long dosage;
    private int myDrugID;

}
