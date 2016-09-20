package com.suveraapp.adapter;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suveraapp.R;
import com.suveraapp.objects.Schedule;
import com.suveraapp.objects.ScheduleList;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MyDrugAdapter extends RecyclerView.Adapter<MyDrugAdapter.DrugHolder> {
    private LayoutInflater mInflater;
    private RealmResults<MyDrug> mDrugs;
    private RealmResults<ScheduleList> mSchedules;
    private RealmList<Schedule> mSchedule;
    private Realm mRealm;
    private int hour, min;


    public MyDrugAdapter(Context context, RealmResults<MyDrug> drugs) {
        mInflater = LayoutInflater.from(context);
        update(drugs);
    }

    public void update(RealmResults<MyDrug> results) {
        mDrugs = results;
        notifyDataSetChanged();
    }

    @Override
    public DrugHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.drug_item, parent, false);
        DrugHolder holder = new DrugHolder(view);
        view.getBackground().setAlpha(77);
        return holder;
    }

    @Override
    public void onBindViewHolder(DrugHolder holder, int position) {
        getTimeFromAndroid();
        mRealm = Realm.getDefaultInstance();
        MyDrug myDrug = mDrugs.get(position);
        mSchedules = mRealm.where(ScheduleList.class).equalTo("myDrugID", myDrug.getMyDrugID()).findAll();

        //query for the drugs that are within this time period
        //eg, show all morning drugs (drugs between 3am and 12pm)

        for (int j = 0; j < mSchedules.size(); j++) {
            ScheduleList myScheduleList = mSchedules.get(j);
            mSchedule = myScheduleList.getList();

            for (int i = 0; i < mSchedule.size(); i++) {
                String[] name = myDrug.getMyDrugName().split(" ");
                long alarmTime = mSchedule.get(i).getTime();
                long dosage = mSchedule.get(i).getDosage();
                long myHour = (int) ((alarmTime / (1000 * 60 * 60)) % 24);
                //  int myMinutes = (int) ((alarmTime / (1000 * 60 * 60)) % 60);

                if (hour >= 0 && hour < 3) {
                    //latenight
                    if (myHour >= 0 && myHour < 3) {
                        holder.mDrug.setText(name[0]);
                        holder.mDosage.setText("x" + String.valueOf(dosage));
                    }
                } else if (hour >= 3 && hour < 12) {
                    //morning
                    if (myHour >= 3 && myHour < 12) {
                        holder.mDrug.setText(name[0]);
                        holder.mDosage.setText("x" + String.valueOf(dosage));
                    }
                } else if (hour >= 12 && hour < 17) {
                    //afternoon
                    if (myHour >= 12 && myHour < 17) {
                        holder.mDrug.setText(name[0]);
                        holder.mDosage.setText("x" + String.valueOf(dosage));
                    }
                } else if (hour >= 17 && hour < 21) {
                    //evening
                    if (myHour >= 17 && myHour < 21) {
                        holder.mDrug.setText(name[0]);
                        holder.mDosage.setText("x" + String.valueOf(dosage));
                    }
                } else if (hour >= 21 && hour <= 24) {
                    //latenight
                    if (myHour >= 21 && myHour < 24) {
                        holder.mDrug.setText(name[0]);
                        holder.mDosage.setText("x" + String.valueOf(dosage));
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrugs.size();
    }

    public static class DrugHolder extends RecyclerView.ViewHolder {

        TextView mDrug, mDosage;

        public DrugHolder(View itemView) {
            super(itemView);
            mDrug = (TextView) itemView.findViewById(R.id.rc_drug);
            mDosage = (TextView) itemView.findViewById(R.id.rc_dosage);
        }
    }


    //get current time from Android device
    public void getTimeFromAndroid() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            min = cal.get(Calendar.MINUTE);
            // day = cal.get(Calendar.DAY_OF_WEEK);
        } else {
            Date dt = new Date();
            hour = dt.getHours();
            min = dt.getMinutes();
            // day = dt.getDay();
        }
    }
}
