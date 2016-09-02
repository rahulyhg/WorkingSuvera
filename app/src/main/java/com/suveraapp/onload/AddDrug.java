package com.suveraapp.onload;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.suveraapp.R;
import com.suveraapp.drug.Drug;
import com.suveraapp.objects.Days;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.Reason;
import com.suveraapp.objects.Schedule;

public class AddDrug extends FragmentActivity implements SelectDrug.SelectDrugListener, AddReason.AddReasonListener,
        SelectInterval.SelectIntervalListener, SelectSpecDays.Specific_daysListener, AddSchedule.AddScheduleListener {

    private Days days;
    private Interval interval;
    private Reason reason;

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
        Bundle bundle = new Bundle();
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
        if(interval.isInterval()) {
            swapFragment(new SelectSpecDays());
        }else{
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

    }
}
