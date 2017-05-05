package applab.client.search.application;

import android.app.Application;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.stetho.Stetho;

import applab.client.search.R;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.task.SubmitTrackerMultipleTask;
import applab.client.search.utils.MediaUtils;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by skwakwa on 10/15/15.
 */
public class IctcCkwIntegration  extends MultiDexApplication {
    public static final String TAG = IctcCkwIntegration.class.getSimpleName();

    public static final String API_PATH = "api/v1/";
    public static final String LOGIN_PATH = API_PATH + "user/";
    public static final String TRACKER_SUBMIT_PATH = "api/v1/tracker";
    public static final String SUPERVISOR_API = "api/v1/incharge";

    public static final String BUGSENSE_API_KEY = "f3a6ec3a";
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final String USER_AGENT = "CCHSupervisor Android: ";
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");
    public static final int MAX_TRACKER_SUBMIT = 150;
    public IctcTrackerLogTask omUpdateCCHLogTask = null;
    public SubmitTrackerMultipleTask omSubmitTrackerMultipleTask = null;

    public static boolean isLoggedIn(Activity act) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(act.getBaseContext());
        String username = prefs.getString(act.getString(R.string.prefs_username), "");
        String apiKey = prefs.getString(act.getString(R.string.prefs_api_key), "");
        if (username.trim().equals("") || apiKey.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
    public void onCreate() {
        super.onCreate();
        createNoMediaFile();
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(  base);
        MultiDex.install(this);

    }


    public static void createNoMediaFile() {
        FileOutputStream out = null;

        try {

            File ROOT = new File(MediaUtils.SMART_EXT_FOLDER);

            if (!ROOT.exists())
                if (ROOT.mkdirs()){

                    Log.i(TAG, "ROOT dir file created!  " + ROOT);

                }

            File file = new File(ROOT + File.separator, ".nomedia");
            if (!file.exists()) {
                out = new FileOutputStream(file);
                out.write(0);
                out.close();


                Log.i(TAG, "No media file created!  " + file);
            } else {
                Log.i(TAG, "No media already exists!!!!!!  " + file);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
