package applab.client.search.synchronization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import applab.client.search.ApplicationRegistry;
import applab.client.search.GlobalConstants;
import applab.client.search.R;
import applab.client.search.utils.DeviceMetadata;

/**
 * Handler the broadcast message to start background synchronization.
 */
public class SynchBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ApplicationRegistry.getApplicationContext() == null) {
            ApplicationRegistry.setApplicationContext(context);
        }

        //caching the device imie in the application registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                DeviceMetadata.getDeviceImei(context));

        //register application version in registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION,
                context.getResources().getString(R.string.app_name) + "/" + R.string.app_version);

        Intent backgroundServiceIntent = new Intent(context, BackgroundSynchronizationService.class);
        System.out.println("Sync Service it ");
        context.startService(backgroundServiceIntent);
    }
}
