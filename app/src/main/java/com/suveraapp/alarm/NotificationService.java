package com.suveraapp.alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.suveraapp.MainActivity;
import com.suveraapp.R;
import com.suveraapp.objects.MyDrug;
import com.suveraapp.objects.RealmInteger;
import com.suveraapp.objects.Schedule;
import com.suveraapp.objects.UpdateResults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.goncalves.pugnotification.notification.PugNotification;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NotificationService extends IntentService {
    private int hour, day;
    public static final String TAG = "Suvera";
    private RealmList<RealmInteger> mDay;

    public NotificationService() {
        super("NotificationService");
        Log.d(TAG,"NotificationService ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"onHandleIntent: ");
        Realm realm = null;
        try{
            realm = Realm.getDefaultInstance();
            RealmResults<MyDrug> myDrugs = realm.where(MyDrug.class).findAll();
            fireNotification();
            for(MyDrug myDrug : myDrugs){
            }
        }finally {
            if(realm!= null){
                realm.close();
            }
        }
    }

    private void fireNotification() {
        PugNotification.with(this).load().title("Hey, it's time :)").
                message("You need to take your medication!").
                bigTextStyle("You currently have some medication you need to take!").
                smallIcon(R.mipmap.ic_launcher).color(R.color.colorAccent).
                largeIcon(R.mipmap.ic_launcher).flags(Notification.DEFAULT_ALL).click(MainActivity.class).simple().build();
    }

}
