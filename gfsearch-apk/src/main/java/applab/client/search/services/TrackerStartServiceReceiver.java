package applab.client.search.services;

/**
 * Created by skwakwa on 10/15/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import applab.client.search.R;

public class TrackerStartServiceReceiver extends BroadcastReceiver {

    public final static String TAG = TrackerStartServiceReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context ctx, Intent intent) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean backgroundData = prefs.getBoolean(ctx.getString(R.string.prefs_background_data_connect), true);
        Intent service = new Intent(ctx, TrackerService.class);

        Bundle tb = new Bundle();
        tb.putBoolean("backgroundData", backgroundData);
        service.putExtras(tb);

        ctx.startService(service);
    }
}