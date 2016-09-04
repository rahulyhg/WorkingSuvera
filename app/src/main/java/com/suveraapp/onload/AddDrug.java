package com.suveraapp.onload;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.suveraapp.R;
import com.suveraapp.drug.Drug;
import com.suveraapp.objects.Days;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.Reason;
import com.suveraapp.objects.Schedule;

import java.util.ArrayList;

public class AddDrug extends FragmentActivity implements SelectDrug.SelectDrugListener, AddReason.AddReasonListener,
        SelectInterval.SelectIntervalListener, SelectSpecDays.Specific_daysListener, AddSchedule.AddScheduleListener, Overview.AddOverviewListener {

    private Days days;
    private Interval interval;
    private Reason reason;
    private ArrayList<Schedule> mySchedule = new ArrayList<>();


    private Bundle bundle;
    private Overview overview = new Overview();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        //start on select drug
        swapFragment(new SelectDrug());
    }

    public void swapFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void drugSelected(Drug selection) {
        //passed from Select Drug
        bundle = new Bundle();
        bundle.putInt("DrugID", selection.getId());
        AddReason addReason = new AddReason();
        addReason.setArguments(bundle);
        swapFragment(addReason);
    }

    @Override
    public void reasonGiven(Reason reason) {
        this.reason = reason;
        swapFragment(new SelectInterval());
    }

    @Override
    public void intervalSelected(Interval interval) {
        this.interval = interval;

        //checks if the interval is either false (everyday)
        //or true (specific days)
        if (interval.isInterval()) {
            swapFragment(new SelectSpecDays());
        } else {
            swapFragment(new AddSchedule());
        }
    }

    @Override
    public void daysSelected(Days days) {
        this.days = days;
        swapFragment(new AddSchedule());
    }

    @Override
    public void scheduleSelected(Schedule schedule) {

        //checks if the arraylist is already contains 3 values
        if (mySchedule.size() == 3) {
            //Schedule temp1 = mySchedule.get(1);
            //Schedule temp2 = mySchedule.get(1);

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
        bundle.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) mySchedule);
        Overview overview = new Overview();
        overview.setArguments(bundle);
        swapFragment(overview);
    }

    @Override
    public void overviewSubmit(AddDrug drug) {

    }
}