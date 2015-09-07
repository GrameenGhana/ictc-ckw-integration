package applab.client.search.utils;

import android.database.Cursor;
import android.provider.ContactsContract;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.storage.StorageManager;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 8/4/15.
 */
public class FarmerServiceUtil {


    public static List findAllFarmers() {
        return buildFarmerFromResult(StorageManager.getInstance().sqlSearch("SELECT * FROM " + DatabaseHelperConstants.ICTC_FARMER + " order by " + DatabaseHelperConstants.FIRST_NAME + " asc"));
    }

    public static List searchFarmerByName(String name) {
        String replaced = name.replace(" ", "%");
        return buildFarmerFromResult(StorageManager.getInstance().sqlSearch("SELECT * FROM " + DatabaseHelperConstants.ICTC_FARMER + " where " + DatabaseHelperConstants.FIRST_NAME + " LIKE '" + name + "%'" +
                " or " + DatabaseHelperConstants.NICKNAME + " LIKE '" + name + "%'" +
                " or " + DatabaseHelperConstants.OTHER_NAMES + " LIKE '" + name + "%' order by " + DatabaseHelperConstants.FIRST_NAME + " asc"));
    }


    public static Farmer getFarmerDetails(String id) {
        List<Farmer> farmers = buildFarmerFromResult(StorageManager.getInstance().sqlSearch("SELECT * FROM " + DatabaseHelperConstants.ICTC_FARMER + " WHERE " + DatabaseHelperConstants.FARMER_ID + "='" + id + "'"));

        return (farmers.isEmpty()) ? null : farmers.get(0);

    }

    public static List<Farmer> getFarmerField(String fieldName, String fieldValue) {
        List<Farmer> farmers = buildFarmerFromResult(StorageManager.getInstance().sqlSearch("SELECT * FROM " + DatabaseHelperConstants.ICTC_FARMER + " WHERE " + fieldName + "='" + fieldValue + "'"));

        return (farmers);

    }

    public static List getFarmersInCommunity(String name) {

        return getFarmerField(DatabaseHelperConstants.COMMUNITY, name);

    }

    public static List getFarmersInCluster(String cluster) {

        return getFarmerField(DatabaseHelperConstants.CLUSTER, cluster);

    }

    public static int getFarmerCount() {
        return StorageManager.getInstance().recordCount(DatabaseHelperConstants.ICTC_FARMER);
    }

    public static int getFarmerCommunityCount() {
        return StorageManager.getInstance().recordCount(DatabaseHelperConstants.ICTC_FARMER, DatabaseHelperConstants.COMMUNITY);
    }

    public static int getFarmerClusterCount(String cluster) {
        return StorageManager.getInstance().recordCount(DatabaseHelperConstants.ICTC_FARMER, DatabaseHelperConstants.CLUSTER, cluster);
    }

    public static List<Farmer> buildFarmerFromResult(Cursor localCursor) {

        List<Farmer> farmers = new ArrayList<Farmer>();
        while (localCursor.moveToNext()) {
            farmers.add(new Farmer(
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.OTHER_NAMES)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NICKNAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.COMMUNITY)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.VILLAGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DISTRICT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.REGION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.AGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.GENDER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MARITAL_STATUS)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_CHILD)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_DEPENDANT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.EDUCATION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.CLUSTER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FARMER_ID))));
        }
        return farmers;
    }
}
