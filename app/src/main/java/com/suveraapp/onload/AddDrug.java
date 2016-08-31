package com.suveraapp.onload;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.suveraapp.R;
import com.suveraapp.drug.Drug;

public class AddDrug extends FragmentActivity implements SelectDrug.SelectDrugListener, AddReason.AddReasonListener, SelectInterval.SelectIntervalListener, Specific_days.Specific_daysListener {

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
    public void reasonGiven(String reason) {
        swapFragment(new SelectInterval());
    }

    @Override
    public void intervalSelected(int interval) {
    swapFragment(new Specific_days());
    }

    @Override
    public void daysSelected(boolean[] days) {

    }
}
