package applab.client.search.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;


/**
 * Created by AangJnr on 7/24/16.
 */


public class PermissionsActivity  {


    static String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS };

    int OVERLAY_PERMISSION_REQ_CODE = 10;
    Boolean hasAllPermissions = null;








    public static boolean launchMultiplePermissions(Activity context){


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (String permission : PERMISSIONS) {
                if (!hasPermission(context, permission)) {


                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                    } else {
                        ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                    }

                    return false;
                }else {
                    return true;

                }
            }
        }
        return true;
    }


    public static boolean hasPermission(Context context, String PERMISSION){

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {

            if (ActivityCompat.checkSelfPermission(context, PERMISSION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSION)){


                }
                return false;
            }

        }
        return true;
    }






    }








