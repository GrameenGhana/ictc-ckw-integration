package org.grameenfoundation.search.synchronization;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Responsible for configuring the background synchronization process. It interacts with the
 * AlarmManager to determine how often the background synchronization process will run.
 * <p/>
 * This broadcast configurer is a broadcast listener of
 * BOOT_COMPLETED, TIME_CHANGED, TIME_ZONE_CHANGED, PACKAGE_INSTALL and
 * BACKGROUND_SYNC_CONFIGURATION events.
 */
public class BackgroundSynchronizationConfigurer extends BroadcastReceiver {
    public static final String ACTION_BACKGROUND_SYNC = "org.grameenfoundation.search.synchronization.BACKGROUND_SYNC";
    public static final String ACTION_BACKGROUND_SYNC_CONFIGURATION =
            "org.grameenfoundation.search.synchronization.BACKGROUND_SYNC_CONFIGURATION";

    private AlarmManager alarmManager = null;
    private final static Integer DEFAULT_SYNCHRONIZATION_INTERVAL = 30 * 60 * 1000; //30 minutes

    @Override
    public void onReceive(Context context, Intent intent) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (!isAlarmSet(context)) {
            sendRepeatingAlarm(context);
        }
    }

    /**
     * sets up a repeating alarm and configures it
     *
     * @param context
     */
    private void sendRepeatingAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1);

        Intent intent = new Intent(ACTION_BACKGROUND_SYNC);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                DEFAULT_SYNCHRONIZATION_INTERVAL, pendingIntent);
    }

    /**
     * checks whether the alarm is already set.
     *
     * @param context
     * @return
     */
    public boolean isAlarmSet(Context context) {
        Intent intent = new Intent(ACTION_BACKGROUND_SYNC);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }
}
