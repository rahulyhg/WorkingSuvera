package com.suveraapp.onload;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.suveraapp.R;
import com.suveraapp.objects.Schedule;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSchedule extends Fragment {
    private AddScheduleListener parentListener;
    private ImageButton btnNext;
    private TimePicker timePicker;
    private NumberPicker numberPicker;
    private Schedule schedule = new Schedule(0, 0);

    private int hour, min;

    public AddSchedule() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        final Calendar calendar = Calendar.getInstance();

        //find NumberPicker
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(5);
        numberPicker.setMinValue(1);
        //find TimePicker
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        //find button
        btnNext = (ImageButton) view.findViewById(R.id.button);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets calendar time to selected time
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                }

                //update medicine amount and time in schedule object
                schedule.setAmount(numberPicker.getValue());
                schedule.setTime(calendar.getTimeInMillis());

                //pass through the schedule object if a medicine amount more than 0 is selected
                parentListener.scheduleSelected(schedule);

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddScheduleListener) {
            parentListener = (AddScheduleListener) context;
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

    public interface AddScheduleListener {
        void scheduleSelected(Schedule schedule);
    }

}
