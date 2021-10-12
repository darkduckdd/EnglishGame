package space.darkduck.englishgame;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.Calendar;
import java.util.Date;

public class WakefulReceiver extends WakefulBroadcastReceiver {
    private AlarmManager mAlarmManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulReceiver.completeWakefulIntent(intent);
    }

    public void setAlarm(Context context) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakefulReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //// TODO: use calendar.add(Calendar.SECOND,MINUTE,HOUR, int);
        calendar.add(Calendar.SECOND, 10);

        //ALWAYS recompute the calendar after using add, set, roll
        Date date = calendar.getTime();

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), alarmIntent);

        // Enable {@code BootReceiver} to automatically restart when the
        // device is rebooted.
        //// TODO: you may need to reference the context by ApplicationActivity.class
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
}
