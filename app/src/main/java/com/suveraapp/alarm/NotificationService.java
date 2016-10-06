package com.suveraapp.alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
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

public class NotificationService extends BroadcastReceiver {
    public static final String TAG = "Suvera";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("OnReceive","Alarm working");
        PugNotification.with(context).load().title("Hey, it's time :)").
                message("You need to take your medication!").
                bigTextStyle("You currently have some medication you need to take!").
                smallIcon(R.drawable.ic_launcher).color(R.color.colorAccent).
                largeIcon(R.drawable.ic_launcher).flags(Notification.DEFAULT_ALL).click(MainActivity.class).simple().build();
    }
}
