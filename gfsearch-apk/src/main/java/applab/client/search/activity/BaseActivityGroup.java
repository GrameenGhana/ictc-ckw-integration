package applab.client.search.activity;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
public class BaseActivityGroup extends ActivityGroup {

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

        System.out.println("About to save data43P "+page);
        hp.insertCCHLog(module, obj.toString(), startTime, System.currentTimeMillis());
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
        version= IctcCKwUtil.getAppVersion(this.getBaseContext());
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
            obj.put("battery", (battery));
            obj.put("version", version);
            obj.put("imei", imei);
        }catch (Exception e){

        }
        System.out.println("   System.out.println(Data44) : "+obj.toString());
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
    public String getSection() {
        return section;
    }

    public void setSection(String data) {
        this.section = data;
    }

    public void showHome(View view){

        Intent t =  new Intent(view.getContext(),DashboardActivity.class);
        view.getContext().startActivity(t);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflator = getMenuInflater();
//        inflator.inflate(R.menu.main, menu);
//
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        try {
//
////            if (drawerToggle.onOptionsItemSelected(item)) {
////                return true;
////            }
//
////            System.out.println("Itemsing android.R.id.home  : "+android.R.id.action_home);
//
//            if (item.getItemId() == R.id.action_settings) {
//                Intent intent = new Intent().setClass(this, SettingsActivity.class);
//                this.startActivityForResult(intent, 0);
//            } /*else if (item.getItemId() == R.id.action_nav_back) {
//                listViewBackNavigation();
//            }
//              else if (item.getItemId() == R.id.action_synchronise) {
//                startSynchronization();
//            */
//            //    }
//            else if (item.getItemId() == R.id.action_about) {
////                Intent intent = new Intent().setClass(this, AboutActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
////                this.startActivity(intent);
//                Intent intent = new Intent().setClass(this, AboutActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
//            }
//
//            else if (item.getItemId() == R.id.action_refresh_farmer) {
////                Intent intent = new Intent().setClass(this, AboutActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
////                this.startActivity(intent);
//                //(final Context context,final DatabaseHelper databaseHelper,final Intent intent, final String queryString, final String type,String msg )
//
//
////                Toast.makeText(getBaseContext(),"Synchronising Data Please wait",Toast.LENGTH_LONG).show();
////                System.out.println("Payload Refresh farmer Data");
//////                IctcCkwIntegration app = null;
//////                try {
//////                    app=(IctcCkwIntegration) this.getApplication();
//////
//////                }catch(Exception e)h{
//////                    System.out.println("Exceptione e: "+e.getLocalizedMessage());
//////                }
////
////                System.out.println("Payload ppapp ");
////                DatabaseHelper dbh = new DatabaseHelper(getBaseContext());
////                System.out.println("Payload dbh ");
////                Payload mqp = dbh.getCCHUnsentLog();
////                System.out.println("Payload unset ");
////                IctcTrackerLogTask omUpdateCCHLogTask = new IctcTrackerLogTask(this);
////                System.out.println("Payload stask ");
////                omUpdateCCHLogTask.execute(mqp);
////                System.out.println("Payload execute ");
////                ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report");
////
////
////                ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");
//                startSynchronization();
////
//            }
////            else if (item.getItemId() == android.R.id.home) {
////                //resetDisplayMenus();
////                selectItem(0);
////            }
//            else if (item.getItemId() == R.id.action_logout) {
////                helper.resetFarmer();
//                Intent intent = new Intent().setClass(this, StartUpActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
//
//            }
//            else if (item.getItemId() == android.R.id.home) {
//                Intent intent = new Intent().setClass(this, DashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
//
//            } /*else if (item.getItemId() ==R.id.action_meeting_items) {
//                Intent intent = new Intent().setClass(this, ScheduledMeetingsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
//
//            }*/
//        } catch (Exception ex) {
//            Log.e(MainActivity.class.getName(), "", ex);
//        }
//
//        return true;
//    }
//
//    private void startSynchronization() {
//        SynchronizationManager.getInstance().registerListener(new SynchronizationListener() {
//            @Override
//            public void synchronizationStart() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.show();
//                    }
//                });
//            }
//
//            @Override
//            public void synchronizationUpdate(final Integer step, final Integer max, final String message, Boolean reset) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.setMessage(message);
//                        progressDialog.setMax(max);
//                        progressDialog.setProgress(step);
//                        progressDialog.setIndeterminate(false);
//                        if (!progressDialog.isShowing()) {
//                            progressDialog.show();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void synchronizationUpdate(final String message, Boolean indeterminate) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.setMessage(message);
//                        progressDialog.setIndeterminate(true);
//                        if (!progressDialog.isShowing()) {
//                            progressDialog.show();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void synchronizationComplete() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//
//                    }
//
//                });
//
//                SynchronizationManager.getInstance().unRegisterListener(this);
//            }
//
//            @Override
//            public void onSynchronizationError(final Throwable throwable) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (progressDialog != null) {
//                            progressDialog.dismiss();
//                        }
//
//                        AlertDialog alertDialog =
//                                new AlertDialog.Builder(getBaseContext()).create();
//                        alertDialog.setMessage(throwable.getMessage());
//                        alertDialog.setIcon(android.R.drawable.stat_sys_warning);
//
//                        alertDialog.setTitle(R.string.error_title);
//                        alertDialog.setCancelable(true);
//                        alertDialog.show();
//                    }
//                });
//
//                SynchronizationManager.getInstance().unRegisterListener(this);
//            }
//        });
//
//        SynchronizationManager.getInstance().start();
//    }
//
//    private void createProgressBar() {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                progressDialog = new ProgressDialog(getBaseContext());
//                progressDialog.setTitle(R.string.synchronization_progress_bar_title);
//                progressDialog.setCancelable(true);
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                progressDialog.setIndeterminate(false);
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.setIcon(R.drawable.ic_refresh);
//                progressDialog.setProgressNumberFormat(null);
//
//                if (SynchronizationManager.getInstance().isSynchronizing()) {
//                    progressDialog.show();
//                }
//            }
//        });
//    }
//
//    private void initiateBackgroundSyncConfiguration() {
//        Intent intent = new Intent(BackgroundSynchronizationConfigurer.ACTION_BACKGROUND_SYNC_CONFIGURATION);
//        this.sendBroadcast(intent);
//    }
}
