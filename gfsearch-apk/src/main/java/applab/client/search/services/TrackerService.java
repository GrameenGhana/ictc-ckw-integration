package applab.client.search.services;

/**
 * Created by skwakwa on 10/15/15.
 */

import java.util.ArrayList;

//import org.grameenfoundation.cch.supervisor.R;
//import org.grameenfoundation.cch.supervisor.application.CCHSupervisor;
//import org.grameenfoundation.cch.supervisor.application.DbHelper;
//import org.grameenfoundation.cch.supervisor.listener.SubmitListener;
//import org.grameenfoundation.cch.supervisor.model.Payload;
//import org.grameenfoundation.cch.supervisor.model.User;
//import org.grameenfoundation.cch.supervisor.tasks.UpdateCCHLogTask;
//import org.grameenfoundation.cch.supervisor.tasks.UpdateSupervisorInfoTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import applab.client.search.application.IctcCkwIntegration;
import applab.client.search.model.Payload;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.SubmitListener;
import applab.client.search.task.APIRequestListener;
import applab.client.search.task.APIRequestTask;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.task.SubmitTrackerMultipleTask;
import org.json.JSONObject;

//import com.bugsense.trace.BugSenseHandler;

public class TrackerService extends Service implements APIRequestListener {

    public static final String TAG = TrackerService.class.getSimpleName();

    private final IBinder mBinder = new MyBinder();
    private SharedPreferences prefs;
    private DatabaseHelper dbh;

    @Override
    public void onCreate() {
        System.out.println("Tracker Init ");
        super.onCreate();
        dbh = new DatabaseHelper(this);
//        BugSenseHandler.initAndStartSession(this, CCHSupervisor.BUGSENSE_API_KEY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Starting Tracker Service");
        System.out.println("Tracker Initiated");

        boolean backgroundData = true;
        Bundle b = intent.getExtras();
        if (b != null) {
            backgroundData = b.getBoolean("backgroundData");
        }

        if (isOnline() && backgroundData) {
            IctcCkwIntegration app = (IctcCkwIntegration) this.getApplication();
            Payload p = null;
            // check for updated nurse info and upload logs: should only do this once a day or so....
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            long lastRun = prefs.getLong("lastCourseUpdateCheck", 0);
            long now = System.currentTimeMillis()/1000;
            if((lastRun + (3600*12)) < now) {

                APIRequestTask task = new APIRequestTask(this);
                p = new Payload(app.TRACKER_SUBMIT_PATH);
                task.setAPIRequestListener(this);
                task.execute(p);

                Editor editor = prefs.edit();
                editor.putLong("lastCourseUpdateCheck", now);
                editor.commit();
            }

			/* CCH: Check to see if the CCH log needs any updating */
            if(app.omUpdateCCHLogTask == null){
                Log.v(TAG, "Updating CCH logs");
                Payload mqp = dbh.getCCHUnsentLog();
                app.omUpdateCCHLogTask = new IctcTrackerLogTask(this);
                app.omUpdateCCHLogTask.execute(mqp);
            }


            // send activity trackers
            if(app.omSubmitTrackerMultipleTask == null){
                app.omSubmitTrackerMultipleTask = new SubmitTrackerMultipleTask(this);
                app.omSubmitTrackerMultipleTask.execute();
            }



            dbh.close();

        }
        return Service.START_NOT_STICKY;
    }

    public void submitComplete(Payload response)
    {
//        if(response.isResult()){
//            User u = (User) response.getData().get(0);
//            dbh.updateUser(u);
//        }
//        System.out.println("submitComplete tracker");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }


    public void apiRequestComplete(Payload response) {
        DatabaseHelper db = new DatabaseHelper(this);
        Log.d(TAG,"completed getting course list");



    }

    public class MyBinder extends Binder {
        public TrackerService getService() {
            return TrackerService.this;
        }
    }

    private boolean isOnline() {
        getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
