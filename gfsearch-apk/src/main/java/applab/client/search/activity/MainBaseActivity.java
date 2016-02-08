package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

/**
 * Created by skwakwa on 2/4/16.
 */
public class MainBaseActivity extends Activity{
private long startTime;
private String module;
private String data;
String imei;
String battery;
String version;
String section;

String page="None";
DatabaseHelper hp;
//    private ProgressDialog progressDialog = null;
//    private Handler handler = null;

        //    private DefaultViewFragment defaultFragment;
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            startTime = System.currentTimeMillis();
//        handler = new Handler();
//        createProgressBar();
//        initiateBackgroundSyncConfiguration();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            super.onDestroy();

            //onsaveinstance does not always get called for the ckw list pages uses the destroy for those pages
            if(!this.getLocalClassName().contains("SearchMenuItemActivity")){
                System.out.println("NOn CKW save on destroyi : "+this.getLocalClassName());
                save();
            }
            // save();

        }

        public void save(){
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
            System.out.println("About to save saveBD "+page);
            hp.insertCCHLog(module, obj.toString(), startTime, System.currentTimeMillis());
        }
//    private void initiateBackgroundSyncConfiguration() {
//        Intent intent = new Intent(BackgroundSynchronizationConfigurer.ACTION_BACKGROUND_SYNC_CONFIGURATION);
//        this.sendBroadcast(intent);
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
        protected void onDestroy() {
            super.onDestroy();
            if(this.getLocalClassName().contains("SearchMenuItemActivity")){
                System.out.println("CKW save on destroy");
                save();
            }
//        System.out.println("CKW distried "+this.getLocalClassName());
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
