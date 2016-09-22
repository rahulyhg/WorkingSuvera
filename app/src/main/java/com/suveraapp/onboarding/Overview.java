package com.suveraapp.onboarding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.suveraapp.R;
import com.suveraapp.drug.DrugType;
import com.suveraapp.objects.Days;
import com.suveraapp.objects.Drug;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.MyDrug;
import com.suveraapp.objects.Reason;
import com.suveraapp.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class Overview extends Fragment {

    private Drug select;
    private Reason reason;
    private Interval interval;
    private Days days;
    private ArrayList<Schedule> drugSchedule;
    private List<String> arrList = new ArrayList<>();
    private MyDrug myDrug;
    private String[] name;

    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);


        ListView lv = (ListView) view.findViewById(R.id.listView);

        //acquire bundle object which contains all the saved data for creating our RealmObject
        Bundle b = getArguments();
        int drugID = b.getInt("dID");
        String drugName = b.getString("dName");
        final String drugURL = b.getString("dUrl");
        String drugReason = b.getString("dReason");
        DrugType drugType = (DrugType) b.getSerializable("dType");
        boolean drugInterval = b.getBoolean("dInterval");
        boolean[] drugDays = b.getBooleanArray("dDays");
        drugSchedule = b.getParcelableArrayList("dSchedule");


        //create an array of the drugname just for confirmation toast
        if (drugName != null) {
            name = drugName.split(" ");
        } else {
            name = new String[]{"Unknown"};
        }

        //create new instances of objects for RealmObject using the bundle
        select = new Drug(drugID, drugName, drugType, drugURL);
        reason = new Reason(drugReason);
        interval = new Interval(drugInterval);
        days = new Days(drugDays);

        //convert schedule list to list of strings for displaying in list view
        if (drugSchedule != null) {
            for (Schedule schedule : drugSchedule) {
                arrList.add("• Dosage: " + String.valueOf(schedule.getDosage()) +
                        " • Time: " + formatTime(schedule.getTime()));
            }
        }


        //display amounts and times for each schedule in a listview in Overview
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.custom_listview, arrList);

        lv.setAdapter(listViewAdapter);

        //listen for action to add new schedule alarm
        ImageButton add = (ImageButton) view.findViewById(R.id.add);
        ImageButton save = (ImageButton) view.findViewById(R.id.submit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                myDrug = new MyDrug(select, reason, interval, days, drugSchedule);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(myDrug);
                realm.commitTransaction();
                realm.close();
                Toast.makeText(getContext(), name[0] + " has been successfully added to your reminders.", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

        return view;
    }

    //convert milliseconds to hours and minutes
    public String formatTime(long time) {
        return String.format("%1$tH:%1$tM", time);
    }

}
