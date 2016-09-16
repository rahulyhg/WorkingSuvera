package com.suveraapp.onload;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.suveraapp.R;
import com.suveraapp.adapter.MyDrug;
import com.suveraapp.drug.Drug;
import com.suveraapp.drug.DrugType;
import com.suveraapp.objects.Days;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.Reason;
import com.suveraapp.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Overview extends Fragment {

    private boolean[] everyday = new boolean[7];
    private Drug select;
    private Reason reason;
    private Interval interval;
    private Days days;
    private ArrayList<Schedule> drugSchedule;
    private AddOverviewListener parentListener;
    private List<String> arrList = new ArrayList<>();
    private MyDrug myDrug;

    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        
        ListView lv = (ListView) view.findViewById(R.id.listView);
        
        //acquire bundle which contains all the saved data for creating
        Bundle b = getArguments();
        int drugID = b.getInt("dID");
        String drugName = b.getString("dName");
        String drugURL = b.getString("dUrl");
        String drugReason = b.getString("dReason");
        DrugType drugType = (DrugType) b.getSerializable("dType");
        boolean drugInterval = b.getBoolean("dInterval");
        boolean [] drugDays = b.getBooleanArray("dDays");
        drugSchedule = b.getParcelableArrayList("dSchedule");

        //create new objects using the bundle
        select = new Drug(drugID,drugName,drugType,drugURL);
        reason = new Reason(drugReason);
        interval = new Interval(drugInterval);
        days = new Days(drugDays);

        //set the new AL to the AL for the drug

        //convert schedule list to list of strings for displaying
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

                RealmConfiguration configuration = new RealmConfiguration.Builder(getActivity()).build();
                Realm.setDefaultConfiguration(configuration);
                Realm realm = Realm.getDefaultInstance();
                myDrug = new MyDrug(select,reason,interval,days,drugSchedule);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(myDrug);
                realm.commitTransaction();
                realm.close();
                getActivity().finish();
            }
        });

        return view;
    }

    //convert milliseconds to hours and minutes
    public String formatTime(long time) {

        return String.format("%1$tH:%1$tM", time);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddOverviewListener) {
            parentListener = (AddOverviewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }

    public interface AddOverviewListener {
        void overviewSubmit(AddDrug drug);
    }


}
