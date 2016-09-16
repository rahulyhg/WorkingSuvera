package com.suveraapp.onload;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.suveraapp.R;
import com.suveraapp.adapter.MyDrug;
import com.suveraapp.drug.Drug;
import com.suveraapp.objects.Days;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.Reason;
import com.suveraapp.objects.Schedule;

import java.util.ArrayList;
import java.util.Arrays;

public class AddDrug extends FragmentActivity implements SelectDrug.SelectDrugListener, AddReason.AddReasonListener,
        SelectInterval.SelectIntervalListener, SelectSpecDays.Specific_daysListener, AddSchedule.AddScheduleListener, Overview.AddOverviewListener {

    private boolean[] everyday = new boolean[7];

    //represents data that is needed from each sub fragment
    private Drug select = new Drug(0, null, null, null);
    private Reason reason = new Reason(null);
    private Interval interval = new Interval(false);
    private Days days = new Days(everyday);
    private ArrayList<Schedule> mySchedule = new ArrayList<>();

    //for storing drug data to save in realm
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);

        //start on select drug
        swapFragment(new SelectDrug());
    }

    //function to set each element in boolean array true (for default choice of everyday)
    public boolean [] setEveryday(boolean[] a) {
        boolean[] toFill = new boolean[a.length];
        Arrays.fill(toFill, true);
        return toFill;
    }

    @Override
    public void drugSelected(Drug selection) {
        select = selection;

        //passed from Select Drug
        bundle = new Bundle();
        bundle.putString("dName", selection.getName());
        bundle.putInt("dID", selection.getId());
        bundle.putString("dUrl", selection.getUrl());
        bundle.putSerializable("dType",selection.getType());
        AddReason addReason = new AddReason();
        addReason.setArguments(bundle);
        swapFragment(addReason);
    }

    @Override
    public void reasonGiven(Reason reason) {
        bundle.putString("dReason", reason.getReason());
        swapFragment(new SelectInterval());
    }

    @Override
    public void intervalSelected(Interval interval) {
        bundle.putBoolean("dInterval",interval.isInterval());
        //checks if the interval is either false (everyday)
        //or true (specific days)
        if (interval.isInterval()) {
            swapFragment(new SelectSpecDays());
        } else {

            //set a default boolean array for everyday being true
            everyday = setEveryday(everyday);
            bundle.putBooleanArray("dDays",everyday);
            swapFragment(new AddSchedule());
        }
    }

    @Override
    public void daysSelected(Days days) {
        bundle.putBooleanArray("dDays",days.getDays());
        swapFragment(new AddSchedule());
    }

    @Override
    public void scheduleSelected(Schedule schedule) {

        //checks if the arraylist is already contains 3 values
        if (mySchedule.size() == 3) {
            //pops top off array list and
            //moves everything up if 3 alarm schedules
            //already exist
            mySchedule.set(0, mySchedule.get(1));
            mySchedule.set(1, mySchedule.get(2));
            mySchedule.set(2, schedule);
        } else {
            mySchedule.add(schedule);
        }

        //bundle to pass the arraylist to the overview class
        bundle.putParcelableArrayList("dSchedule", mySchedule);
        Overview overview = new Overview();
        overview.setArguments(bundle);
        swapFragment(overview);
    }

    @Override
    public void overviewSubmit() {

    }

    //to move from one sub fragment to another
    public void swapFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}