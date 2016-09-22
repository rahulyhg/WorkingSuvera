package com.suveraapp.onboarding;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.suveraapp.R;
import com.suveraapp.objects.Days;

public class SelectSpecDays extends Fragment {

    private Specific_daysListener parentListener;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;
    private boolean[] specDays = new boolean[7];
    private boolean[] dummy = new boolean[]{false};
    private Days days = new Days(dummy);

    public SelectSpecDays() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specific_days, container, false);

        //find checkboxes
        sun = (CheckBox) view.findViewById(R.id.sunday);
        mon = (CheckBox) view.findViewById(R.id.monday);
        tue = (CheckBox) view.findViewById(R.id.tuesday);
        wed = (CheckBox) view.findViewById(R.id.wednesday);
        thu = (CheckBox) view.findViewById(R.id.thursday);
        fri = (CheckBox) view.findViewById(R.id.friday);
        sat = (CheckBox) view.findViewById(R.id.saturday);

        //find button
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btnConfirmDays);

        //listen for user input
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set each element in the boolean array equivalent to the checked boxes
                specDays[0] = sun.isChecked();
                specDays[1] = mon.isChecked();
                specDays[2] = tue.isChecked();
                specDays[3] = wed.isChecked();
                specDays[4] = thu.isChecked();
                specDays[5] = fri.isChecked();
                specDays[6] = sat.isChecked();


                //check if any of the boxes have been selected to validate a day has been selected
                if (containsTrue(specDays)) {
                    //sets the boolean array containing selected days to the object representing this
                    days.setDays(specDays);
                    //pass through the object to the parent fragactivity
                    parentListener.daysSelected(days);
                } else {
                    //else notify user to select at least one box
                    Toast.makeText(getContext(), "Select at least one day", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Specific_daysListener) {
            parentListener = (Specific_daysListener) context;
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

    //checks if given boolean array contains a true value
    public boolean containsTrue(boolean[] array) {
        for (boolean val : array) {
            if (val)
                return true;
        }
        return false;
    }

    public interface Specific_daysListener {
        void daysSelected(Days days);
    }


}
