package com.suveraapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suveraapp.adapter.MyDrug;
import com.suveraapp.adapter.MyDrugAdapter;
import com.suveraapp.objects.Schedule;
import com.suveraapp.objects.ScheduleList;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class HomeScreen extends Fragment {

    private TextView dayOfWeek;
    private TextView timeOfDay;
    private int hour, min, day;
    private RecyclerView mRecylcerview;
    private Realm mRealm;
    private String myDrugname = "myDrugName";
    private MyDrugAdapter myDrugAdapter;
    private RealmResults<MyDrug> results;
    private RealmList<Schedule> schedules;
    private RealmResults<ScheduleList> scheduleLists;

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
        dayOfWeek = (TextView) view.findViewById(R.id.day);
        timeOfDay = (TextView) view.findViewById(R.id.timeOfDay);

        //show recylcer view of drugs
        mRecylcerview = (RecyclerView) view.findViewById(R.id.rc_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecylcerview.setLayoutManager(manager);
        mRealm = Realm.getDefaultInstance();
        results = mRealm.where(MyDrug.class).findAll();
        myDrugAdapter = new MyDrugAdapter(getContext(), results);
        mRecylcerview.setAdapter(new MyDrugAdapter(getContext(), results));

        //set text to the time of the day, i.e. morning, evening or afternoon
        //day of the week and background to correspond to them
        getTimeFromAndroid();
        setDay(dayOfWeek);
        setPeriod(timeOfDay);
        return view;
    }

    //update adapter when new item added
    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            myDrugAdapter.update(results);
        }
    };


    //sets day of week for textview
    public void setDay(TextView dayOfWeek) {
        //set text to day of the week
        if (day == 1) {
            dayOfWeek.setText(R.string.itSun);
        } else if (day == 2) {
            dayOfWeek.setText(R.string.itMon);
        } else if (day == 3) {
            dayOfWeek.setText(R.string.itTue);
        } else if (day == 4) {
            dayOfWeek.setText(R.string.itWed);
        } else if (day == 5) {
            dayOfWeek.setText(R.string.itThu);
        } else if (day == 6) {
            dayOfWeek.setText(R.string.itFri);
        } else if (day == 7) {
            dayOfWeek.setText(R.string.itSat);
        }
    }

    //sets period of day and some extra changes to colours for certain pictures
    public void setPeriod(TextView timeOfDay) {

        if (results.size() == 0) {
            timeOfDay.setText(R.string.newDrug);
            timeOfDay.setTextSize(35);
            timeOfDay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else if (hour >= 0 && hour < 3) {
            //latenight
            timeOfDay.setTextSize(20);
            timeOfDay.setText(R.string.lMeds);
        } else if (hour >= 3 && hour < 12) {
            //morning
            timeOfDay.setTextSize(20);
            timeOfDay.setText(R.string.mMeds);
        } else if (hour >= 12 && hour < 17) {
            //afternoon
            timeOfDay.setTextSize(20);
            timeOfDay.setText(R.string.aMeds);
            timeOfDay.setTextColor(ContextCompat.getColor(getContext(), R.color.daytext));
        } else if (hour >= 17 && hour < 21) {
            //evening
            timeOfDay.setTextSize(20);
            timeOfDay.setText(R.string.eMeds);
        } else if (hour >= 21 && hour <= 24) {
            //latenight
            timeOfDay.setTextSize(20);
            timeOfDay.setText(R.string.lMeds);
        }
    }

    //get current time from Android device
    public void getTimeFromAndroid() {
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
    public void onResume() {
        super.onResume();
        getTimeFromAndroid();
        setDay(dayOfWeek);
        setPeriod(timeOfDay);
        myDrugAdapter = new MyDrugAdapter(getContext(), results);
        mRecylcerview.setAdapter(new MyDrugAdapter(getContext(), results));
    }

    @Override
    public void onStart() {
        super.onStart();
        getTimeFromAndroid();
        setDay(dayOfWeek);
        setPeriod(timeOfDay);
        myDrugAdapter = new MyDrugAdapter(getContext(), results);
        mRecylcerview.setAdapter(new MyDrugAdapter(getContext(), results));
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
