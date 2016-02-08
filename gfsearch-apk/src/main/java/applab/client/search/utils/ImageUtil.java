package applab.client.search.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by skwakwa on 2/4/16.
 */
public class ImageUtil{


    public static String ProfilePix="FarmerPix";
    public static String SMART_EXT_FOLDER_NAME="smartext";
    public static String SMART_EXT_FOLDER = Environment.getExternalStorageDirectory()+"/"+SMART_EXT_FOLDER_NAME;
    public  static String  FULL_URL_PROFILE_PIX=SMART_EXT_FOLDER+"/"+ProfilePix;

public void getPhoneDirectory(){

    Environment.getDataDirectory().toString();
}

}
