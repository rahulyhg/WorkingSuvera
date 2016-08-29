package suvera.suveraapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import suvera.suveraapp.drug.DrugLoader;
import suvera.suveraapp.onload.AddDrug;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView alarmStatus;
    Context context;
    PendingIntent pendingIntent;
    String hour_s, hour_m;
    public static DrugLoader drugLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        MainActivity.drugLoader = new DrugLoader(this);
        MainActivity.drugLoader.loadDrugs();

        Intent intentT = new Intent(this, AddDrug.class);
        startActivity(intentT);

        //initialise alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialise time picker
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        //initialise status
        alarmStatus = (TextView) findViewById(R.id.updateAlarm);

        //create an instance of calender
        final Calendar calendar = Calendar.getInstance();

        //create intent to start alarm
        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        //initialise text buttons
        TextView start_alarm = (TextView) findViewById(R.id.startAlarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //sets calendar instance with hour and minute we picked on time picker
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                //set calendar instance to hour and minute selected on timepicker

                //get string value of hour and minute
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();

                hour_s = String.valueOf(hour);
                hour_m = String.valueOf(min);

                //pending intent that delays intent until specified time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                //sets alarm manager to run intervalled day
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                //updates status of alarm
                update_stats("Alarm set to " + updateHour(hour_s) + ":" + updateMin(hour_m));
            }
        });


        TextView end_alarm = (TextView) findViewById(R.id.endAlarm);
        end_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alarmManager.cancel(pendingIntent);
                update_stats("Alarm off");
            }
        });
    }

    //makes 1 digit hours to two digit, i.e 1 -> 01
    private String updateHour(String hour) {
        String result = hour;

        if (Integer.parseInt(hour) < 10) {
            result = "0" + hour;
        }
        return result;
    }

    //makes 1 digit minutes to two digit, i.e 5 -> 05
    private String updateMin(String min) {
        String result = min;

        if (Integer.parseInt(min) < 10) {
            result = "0" + hour_m;
        }
        return result;
    }

    //updates alarm status
    private void update_stats(String s) {
        alarmStatus.setText(s);
    }
}


