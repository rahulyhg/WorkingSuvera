package com.suveraapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends Fragment {

    int hour,min,day;
    public HomeScreen() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        TextView dayOfWeek = (TextView) view.findViewById(R.id.day);
        TextView timeOfDay = (TextView) view.findViewById(R.id.timeOfDay);
        getTimeFromAndroid();

        //set text to the time of the day, i.e. morning, evening or afternoon
        if(hour>=0 && hour<=12){
            timeOfDay.setText(R.string.mMeds);
        } else if(hour>=12 && hour<=18){
            timeOfDay.setText(R.string.aMeds);
        } else if(hour>=18 && hour<=24){
            timeOfDay.setText(R.string.eMeds);
        }

        //set ttext to day of the week
        if(day == 1){
            dayOfWeek.setText(R.string.itSun);
        } else if(day == 2){
            dayOfWeek.setText(R.string.itMon);
        }else if(day == 3){
            dayOfWeek.setText(R.string.itTue);
        }else if(day == 4){
            dayOfWeek.setText(R.string.itWed);
        }else if(day == 5){
            dayOfWeek.setText(R.string.itThu);
        }else if(day == 6){
            dayOfWeek.setText(R.string.itFri);
        }else if(day == 7){
            dayOfWeek.setText(R.string.itSat);
        }

        return view;
    }


    //get current time from Android device
    public void getTimeFromAndroid(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            min = cal.get(Calendar.MINUTE);
            day = cal.get(Calendar.DAY_OF_WEEK);
        } else {
            Date dt = new Date();
            hour = dt.getHours();
            min = dt.getMinutes();
            day = dt.getDay();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
