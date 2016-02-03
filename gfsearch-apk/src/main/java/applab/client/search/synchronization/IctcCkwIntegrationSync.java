package applab.client.search.synchronization;

import android.content.ContentValues;
import applab.client.search.model.Farmer;
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
public static String ICTC_SERVER_URL_ROOT_2= "http://104.236.220.225:8080/ICTC/MobileController?";
    static String ICTC_SERVER_URL_ROOT = "http://104.236.220.225:8080/ICTC/api/v1/";
    public static String ICTC_SERVER_URL = "http://104.236.220.225:8080/ICTC/api/v1?";
    public static String GET_FARMER_DETAILS ="fdetails";
    public static final String WEATHER_URL = ICTC_SERVER_URL_ROOT+"weather";
    public static String LOGIN ="login";

    public static void syncFarmerDetails() {
        int networkTimeout = 10 * 60 * 1000;

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


