package com.suveraapp.onload;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.suveraapp.R;
import com.suveraapp.objects.Schedule;

import java.util.ArrayList;
import java.util.List;



public class Overview extends Fragment {

    private AddOverviewListener parentListener;
    private List<String> arrList = new ArrayList<>();

    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);


        ListView lv = (ListView) view.findViewById(R.id.listView);
        ArrayList<Schedule> mySchedule;


        //acquire bundle which contains the arraylist containing the schedules
        Bundle b = getArguments();
        mySchedule = b.getParcelableArrayList("key"); //set the new AL to the AL for the drug

        //convert schedule list to list of strings for displaying
        if (mySchedule != null) {
            for (Schedule schedule : mySchedule) {
                arrList.add("• Dosage: " + String.valueOf(schedule.getAmount()) +
                        " • Time: " + formatTime(schedule.getTime()) );
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
