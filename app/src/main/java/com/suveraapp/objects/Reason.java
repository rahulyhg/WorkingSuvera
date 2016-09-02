package com.suveraapp.objects;

/**
 * Created by Hibatop on 01/09/2016.
 */
public class Reason {

    private String reason; //reason for taking the drug

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public Reason(String reason){
        this.reason = reason;
    }
}
