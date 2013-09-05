package org.grameenfoundation.search.synchronization;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Background Service that initiates the synchronization process in the background.
 */
public class BackgroundSynchronizationService extends Service implements SynchronizationListener {
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        SynchronizationManager.getInstance().registerListener(this);
        SynchronizationManager.getInstance().start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationManager.cancelAll();
        super.onDestroy();
        SynchronizationManager.getInstance().unRegisterListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void synchronizationStart() {

    }

    @Override
    public void synchronizationUpdate(Integer step, Integer max, String message, Boolean reset) {
    }

    @Override
    public void synchronizationComplete() {
    }

    @Override
    public void onSynchronizationError(Throwable throwable) {
    }
}
