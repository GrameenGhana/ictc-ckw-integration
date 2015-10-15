package applab.client.search.activity;

import android.app.ActivityGroup;
import android.os.Bundle;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseActivityGroup extends ActivityGroup {

    private long startTime;
    private String module;
    private String data;
    String imei;
    String battery;
    String version;
    String section;

    String page;
    DatabaseHelper hp;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
    }

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
        version= IctcCKwUtil.getAppVersion();
        imei= IctcCKwUtil.getImei(getBaseContext());

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
            obj.put("battery", battery);
            obj.put("version", version);
            obj.put("imei", imei);
        }catch (Exception e){

        }

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

}
