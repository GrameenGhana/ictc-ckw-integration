package applab.client.search.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.BackgroundSynchronizationConfigurer;
import applab.client.search.synchronization.SynchronizationListener;
import applab.client.search.synchronization.SynchronizationManager;
import applab.client.search.utils.AboutActivity;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseFragmentActivity extends FragmentActivity{
    private long startTime;
    private String module;
    private String data;
    String imei;
    String battery;
    String version;
    String section;

    String page="None";
    DatabaseHelper hp;

    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
//        createProgressBar();
//        initiateBackgroundSyncConfiguration();
    }
//    @Override
//    public void onBackPressed() {
//        // your code.
//        Intent  i = new Intent(this, DashboardActivity.class);
//        startActivity(i);
//    }


    public void setDetails(DatabaseHelper dh, String module, String page){
        this.hp =dh;
        this.module= module;
        this.page=page;
        this.section="";
        this.data="";
        this.setOtherValues();
    }

    public void setDetails(DatabaseHelper dh, String module, String data,String section){
        this.hp =dh;
        this.module= module;
        this.page=page;
        this.setOtherValues();
        this.section=section;
        this.data="";
    }
    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        this.hp =dh;
        this.module= module;
        this.data=data;
        this.setOtherValues();
        this.section=section;
        this.page=page;
    }


    public void setOtherValues(){

        battery = String.valueOf(IctcCKwUtil.getBatteryLevel(getBaseContext()));
        version= IctcCKwUtil.getAppVersion(this.getBaseContext());
        imei= IctcCKwUtil.getImei(getBaseContext());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("Abt data4-5");
        JSONObject obj=null;

        try {
            if(data.isEmpty())
                obj = new JSONObject();
            else
                obj = new JSONObject(data);

            obj.put("page", page);
            obj.put("section", section);
            obj.put("battery", (battery));
            obj.put("version", version);
            obj.put("imei", imei);
        }catch (Exception e){

        }

        System.out.println("About to save data4-3P "+page);
        hp.insertCCHLog(module, obj.toString(), startTime, System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject obj=null;

        try {
            if(data.isEmpty())
                obj = new JSONObject();
            else
                obj = new JSONObject(data);

            obj.put("page", page);
            obj.put("section", section);
            obj.put("battery", (battery));
            obj.put("version", version);
            obj.put("imei", imei);
        }catch (Exception e){

        }

        System.out.println("About to save data45P ");
        hp.insertCCHLog(module, obj.toString(), startTime, System.currentTimeMillis());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public void showHome(View view){
        Intent t =  new Intent(view.getContext(),DashboardActivity.class);
        view.getContext().startActivity(t);
    }


}
