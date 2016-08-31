package com.suveraapp.onload;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.suveraapp.R;

/**
 * Created by Hibatop on 31/08/2016.
 */
public class Specific_days extends Fragment {
    private Specific_daysListener parentListener;
    private Button btnNext;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;
    private boolean[] specDays = new boolean[7];

    public Specific_days() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specific_days, container, false);

        //find checkboxes
        mon = (CheckBox) view.findViewById(R.id.monday);
        tue = (CheckBox) view.findViewById(R.id.tuesday);
        wed = (CheckBox) view.findViewById(R.id.wednesday);
        thu = (CheckBox) view.findViewById(R.id.thursday);
        fri = (CheckBox) view.findViewById(R.id.friday);
        sat = (CheckBox) view.findViewById(R.id.saturday);
        sun = (CheckBox) view.findViewById(R.id.sunday);

        //find button
        btnNext = (Button) view.findViewById(R.id.btnConfirmDays);

        //listen for user input
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set each element in the boolean array equivalent to the checked boxes
                if (mon.isChecked()) {
                    specDays[0] = true;
                } else {
                    specDays[0] = false;
                }

                if (tue.isChecked()) {
                    specDays[1] = true;
                } else {
                    specDays[1] = false;
                }

                if (wed.isChecked()) {
                    specDays[2] = true;
                } else {
                    specDays[2] = false;
                }

                if (thu.isChecked()) {
                    specDays[3] = true;
                } else {
                    specDays[3] = false;
                }

                if (fri.isChecked()) {
                    specDays[4] = true;
                } else {
                    specDays[4] = false;
                }

                if (sat.isChecked()) {
                    specDays[5] = true;
                } else {
                    specDays[5] = false;
                }

                if (sun.isChecked()) {
                    specDays[6] = true;
                } else {
                    specDays[6] = false;
                }

                //check if any of the boxes have been selected
                if (containsTrue(specDays)) {
                    //pass through the boolean array if so
                    parentListener.daysSelected(specDays);
                    //visually check the checkboxes are working
                    Toast.makeText(getContext(), "Checked" +
                                    specDays[0] + specDays[1] + specDays[2] + specDays[3]
                                    + specDays[4] + specDays[5] + specDays[6]
                            , Toast.LENGTH_SHORT).show();
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

    public boolean containsTrue(boolean[] array) {

        for (boolean val : array) {
            if (val)
                return true;
        }

        return false;
    }


    public interface Specific_daysListener {
        void daysSelected(boolean[] days);
    }
}
