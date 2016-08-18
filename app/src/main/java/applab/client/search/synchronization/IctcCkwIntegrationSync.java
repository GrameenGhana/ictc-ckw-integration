package applab.client.search.synchronization;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import applab.client.search.model.Farmer;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.settings.SettingsConstants;
import applab.client.search.settings.SettingsManager;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.storage.StorageManager;
import applab.client.search.utils.HttpHelpers;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 8/4/15.
 */
public class IctcCkwIntegrationSync {


    //104.236.220.225:45805
    ///http://104.236.220.225:8080

    public static   String ICTC_SALESFORCE_SEND_MEASUREMENT="http://ictchallenge.force.com/";
    public static   String ICTC_SALESFORCE_SEND_MEASUREMENT_URL=ICTC_SALESFORCE_SEND_MEASUREMENT+SettingsManager.getInstance().getValue(SettingsConstants.ICTC_SEND_MEASUREMENT_SERVER);
    public static   String ICTC_SERVER_CONTEXT_PATH="ICTC/";
    public static   String ICTC_SERVER_MAIN_URL= SettingsManager.getInstance().getValue(SettingsConstants.ICTC_KEY_SERVER);

    public static String ICTC_SERVER_URL_ROOT_2= ICTC_SERVER_MAIN_URL+ICTC_SERVER_CONTEXT_PATH+"MobileController?";//http://104.236.220.225:8080
    static String ICTC_SERVER_URL_ROOT = ICTC_SERVER_MAIN_URL+ICTC_SERVER_CONTEXT_PATH+"api/v1/";
    public static String ICTC_SERVER_URL = ICTC_SERVER_MAIN_URL+ICTC_SERVER_CONTEXT_PATH+"api/v1?";
    public static String IMAGE_URL=ICTC_SERVER_MAIN_URL+"images/";
    public static String GET_FARMER_DETAILS ="fdetails";
    public static final String WEATHER_URL = ICTC_SERVER_URL_ROOT+"weather";
    public static String LOGIN ="login";

    /** AGSMO End points **/
    public static final String AGSMO_API = "http://chnonthego.org/smartex/";
    public static final String AGSMO_LOGIN_API = AGSMO_API+"?action=authenticate&";
    public static final String AGSMO_GOODS_API = AGSMO_API+"?action=goods&";
    /** END **/

    public static void syncFarmerDetails() {
        int networkTimeout = 10 * 60 * 1000;
        SettingsManager.getInstance().getValue(SettingsConstants.ICTC_KEY_SERVER);
        try {
            System.out.println("Main Int Ltd");
            //   if(FarmerServiceUtil.getFarmerCount()<=0) {
            if (true) {
                String url = ICTC_SERVER_URL;
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                InputStream inputStream = HttpHelpers.postFormRequestAndGetStream(url, new UrlEncodedFormEntity(params),
                        networkTimeout);
                StringBuilder stringBuilder = HttpHelpers.getUncompressedResponseString(new BufferedReader(
                        new InputStreamReader(inputStream)));
                String responseJson = stringBuilder.toString();
                System.out.println("JSON Received  : " + responseJson);
                saveReceivedString(responseJson);
            }
        } catch (Exception e) {
            System.out.println("Exception e :" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    public static String saveReceivedString(String input) {

        JSONObject obj = new JSONObject();
        List<Farmer> myFarmers = new ArrayList<Farmer>();
        try {
            JSONArray farmers = obj.getJSONArray("farmers");
            int farmersCnt = 0;

            for (int i = 0; i < farmersCnt; i++) {
                JSONObject farmer = farmers.getJSONObject(i);
//                Farmer f =
                saveFarmer(farmer.getString("fname"), farmer.getString("lastName"), farmer.getString("nickname"), farmer.getString("community"), farmer.getString("village"), farmer.getString("district"), farmer.getString("region"), farmer.getString("age"), farmer.getString("gender"), farmer.getString("maritalStatus"), farmer.getString("numberOfChildren"), farmer.getString("numberOfDependants"), farmer.getString("education"), farmer.getString("cluster"), farmer.getString("farmID"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }
    public static boolean saveFarmer(String firstName, String lastName, String nickname, String community, String village, String district,
                                     String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                                     String education, String cluster, String farmID) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.FIRST_NAME, firstName);
        values.put(DatabaseHelperConstants.OTHER_NAMES, lastName);
        values.put(DatabaseHelperConstants.NICKNAME, nickname);
        values.put(DatabaseHelperConstants.COMMUNITY, community);
        values.put(DatabaseHelperConstants.VILLAGE, village);
        values.put(DatabaseHelperConstants.DISTRICT, district);
        values.put(DatabaseHelperConstants.REGION, region);
        values.put(DatabaseHelperConstants.AGE, age);
        values.put(DatabaseHelperConstants.GENDER, gender);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.NO_OF_CHILD, numberOfChildren);
        values.put(DatabaseHelperConstants.NO_OF_DEPENDANT, numberOfDependants);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.CLUSTER, cluster);
        values.put(DatabaseHelperConstants.EDUCATION, education);
        values.put(DatabaseHelperConstants.FARMER_ID, farmID);


        return StorageManager.getInstance().insert(DatabaseHelperConstants.ICTC_FARMER, values);

    }
}


