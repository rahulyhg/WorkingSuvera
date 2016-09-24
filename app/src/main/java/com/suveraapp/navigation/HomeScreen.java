package com.suveraapp.navigation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.suveraapp.R;
import com.suveraapp.adapter.MyDrugAdapter;
import com.suveraapp.alarm.NotificationService;
import com.suveraapp.objects.MyDrug;
import com.suveraapp.objects.RealmInteger;
import com.suveraapp.objects.Schedule;
import com.suveraapp.objects.UpdateResults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class HomeScreen extends Fragment {

    private TextView dayOfWeek;
    private TextView timeOfDay;
    private int hour, day;
    private RecyclerView mRecylcerview;
    private Realm mRealm;
    private MyDrugAdapter myDrugAdapter;
    private RealmResults<MyDrug> results;
    private RealmList<Schedule> mSchedule;
    private RealmList<RealmInteger> mDay;


    public HomeScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmManager manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,1000,5000,pendingIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        dayOfWeek = (TextView) view.findViewById(R.id.day);
        timeOfDay = (TextView) view.findViewById(R.id.timeOfDay);
        mRecylcerview = (RecyclerView) view.findViewById(R.id.rc_view);

        updateAll(); //update the view to show the required drugs
        return view;
    }

    public ArrayList<UpdateResults> getCurrentList(RealmResults<MyDrug> drugs) {
        ArrayList<UpdateResults> currentDrugs = new ArrayList<>();

        for (int a = 0; a < drugs.size(); a++) {

            //query for the drugs that are within this time period
            //eg, show all morning drugs (drugs between 3am and 12pm)
            MyDrug myDrug = drugs.get(a);
            String[] name = myDrug.getMyDrugName().split(" ");
            mDay = myDrug.getMyDays();

            //finding each schedule item in schedule list
            for (int b = 0; b < mDay.size(); b++) {
                RealmInteger savedDay = mDay.get(b);
                int myDay = savedDay.getDay();

                //check if the current day is equal to the day in the days array
                //and that this day has also been selected
                if ((day == b+1) && (myDay==1)) {
                    mSchedule = myDrug.getMySchedule();

                    //check each individual schedule dosage and time
                    for (int c = 0; c < mSchedule.size(); c++) {
                        Schedule schedule = mSchedule.get(c);
                        UpdateResults updateResults = new UpdateResults();

                        //change the schedule time (which is saved as long)
                        //into a 24 hour time integer
                        String myTime = formatTime(schedule.getTime());
                        String[] time = myTime.split(":");
                        int myHour = Integer.parseInt(time[0]);

                        if (hour >= 0 && hour < 3) {
                            //latenight
                            if (myHour >= 0 && myHour < 3) {
                                updateResults.setName(name[0]);
                                updateResults.setDosage(schedule.getDosage());
                                currentDrugs.add(updateResults);
                            }
                        } else if (hour >= 3 && hour < 12) {
                            //morning
                            if (myHour >= 3 && myHour < 12) {
                                updateResults.setName(name[0]);
                                updateResults.setDosage(schedule.getDosage());
                                currentDrugs.add(updateResults);
                            }
                        } else if (hour >= 12 && hour < 17) {
                            //afternoon
                            if (myHour >= 12 && myHour < 17) {
                                updateResults.setName(name[0]);
                                updateResults.setDosage(schedule.getDosage());
                                currentDrugs.add(updateResults);
                            }
                        } else if (hour >= 17 && hour < 21) {
                            //evening
                            if (myHour >= 17 && myHour < 21) {
                                updateResults.setName(name[0]);
                                updateResults.setDosage(schedule.getDosage());
                                currentDrugs.add(updateResults);
                            }
                        } else if (hour >= 21 && hour <= 24) {
                            //latenight
                            if (myHour >= 21 && myHour < 24) {
                                updateResults.setName(name[0]);
                                updateResults.setDosage(schedule.getDosage());
                                currentDrugs.add(updateResults);
                            }
                        }
                    }

                }
            }
        }
        return currentDrugs;
    }


    //update view and show the current drugs that need to be taken
    public void updateAll() {
        getTimeFromAndroid();//get current time
        mRealm = Realm.getDefaultInstance(); //get Realm database
        results = mRealm.where(MyDrug.class).findAll(); //find all drugs in database
        ArrayList<UpdateResults> currentList; //create a list to hold the drugs to be shown right now
        currentList = getCurrentList(results); //pass the drugs and find only the ones that need to be shone
        myDrugAdapter = new MyDrugAdapter(getContext(), currentList); //create new adapter object using this list

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecylcerview.setLayoutManager(manager); //set the recycler view to a linear layout
        mRecylcerview.setAdapter(myDrugAdapter); //set the recycler adapter to display items

        //set text to the time of the day, i.e. morning, evening or afternoon
        //day of the week and background to correspond to them
        setDay(dayOfWeek);
        setPeriod(timeOfDay);
    }

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
            day = cal.get(Calendar.DAY_OF_WEEK);
        } else {
            Date dt = new Date();
            hour = dt.getHours();
            day = dt.getDay();
        }
    }

    //convert milliseconds to hours and minutes
    public String formatTime(long time) {
        return String.format("%1$tH:%1$tM", time);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAll();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAll();
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
