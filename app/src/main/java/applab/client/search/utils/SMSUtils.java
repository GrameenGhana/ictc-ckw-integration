package applab.client.search.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import applab.client.search.activity.FarmerDetailActivity;

/**
 * Created by aangjnr on 26/04/2017.
 */

public class SMSUtils extends BroadcastReceiver {

    public static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
    public static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";

    static ProgressDialog progress;


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (progress != null && progress.isShowing()) progress.dismiss();
        }catch(Exception e){
            e.printStackTrace();
        }


        if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK: // Sms sent
                    Toast.makeText(context,  "SMS sent!", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                    Toast.makeText(context, "SMS not sent", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                    Toast.makeText(context, "No service! SMS not sent", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                    Toast.makeText(context, "SMS not sent", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                    Toast.makeText(context, "Airplane Mode is active, SMS not sent", Toast.LENGTH_LONG).show();
                    break;
            }
        }
         else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS received", Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "SMS not received", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    /**
     * Test if device can send SMS
     * @param context
     * @return
     */
    public static boolean canSendSMS(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public static void sendSMS(final Context context, String phoneNumber, String message) {

        progress = new ProgressDialog(context);
        progress.setMessage("Sending message, please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        if (!canSendSMS(context)) {
            Toast.makeText(context, "You cannot send SMS messages. Please make sure permissions are provided", Toast.LENGTH_LONG).show();

            try {
                if (progress != null && progress.isShowing()) progress.dismiss();
            }catch(Exception e){
                e.printStackTrace();
            }

             return;
        }

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        final SMSUtils smsUtils = new SMSUtils();
        //register for sending and delivery
        context.registerReceiver(smsUtils, new IntentFilter(SMSUtils.SENT_SMS_ACTION_NAME));
        context.registerReceiver(smsUtils, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);

        ArrayList<PendingIntent> sendList = new ArrayList<>();
        sendList.add(sentPI);

        ArrayList<PendingIntent> deliverList = new ArrayList<>();
        deliverList.add(deliveredPI);

        sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList);

        //we unsubscribed in 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.unregisterReceiver(smsUtils);

                try {
                    if (progress != null && progress.isShowing()) progress.dismiss();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, 10000);

    }
}