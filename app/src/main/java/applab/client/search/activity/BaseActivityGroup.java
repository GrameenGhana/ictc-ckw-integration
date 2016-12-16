package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.model.Payload;
import applab.client.search.services.TrackerService;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.synchronization.SynchronizationListener;
import applab.client.search.synchronization.SynchronizationManager;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.utils.AboutActivity;
import applab.client.search.utils.ApplicationRegistry;
import applab.client.search.utils.BaseLogActivity;
import applab.client.search.utils.ConnectionUtil;
import applab.client.search.utils.DeviceMetadata;
import applab.client.search.utils.GlobalConstants;
import applab.client.search.utils.IctcCKwUtil;
import applab.client.search.utils.ImageUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseActivityGroup extends AppCompatActivity {
    BaseLogActivity baseLogActivity;
    private String TAG;
    private DatabaseHelper helper;
    private Handler handler;
    private ProgressDialog progressDialog;
    private Context mContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;
        baseLogActivity = new BaseLogActivity(getBaseContext());
        helper = new DatabaseHelper(getBaseContext());
        handler = new Handler();
        progressDialog = new ProgressDialog(mContext);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

        if (!ConnectionUtil.isLoggedIn(this)) {
            startActivity(new Intent(BaseActivityGroup.this, LoginActivity.class));
            finish();

        } else {
            handler = new Handler();
            createProgressBar();

            // TODO: Move to Manifest start.
            Intent service = new Intent(this, TrackerService.class);
            Bundle tb = new Bundle();
            tb.putBoolean("backgroundData", true);
            service.putExtras(tb);
            this.startService(service);

            try {
                // Register values in cache
                String username = ConnectionUtil.currentUsername(this);
                String fullName = ConnectionUtil.currentUserFullName(this);

                ApplicationRegistry.setApplicationContext(this.getApplicationContext());
                ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                        DeviceMetadata.getDeviceImei(this.getApplicationContext()));
                ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION, getResources().getString(R.string.app_name) + "/"
                        + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                ApplicationRegistry.register(IctcCKwUtil.KEY_USER_NAME, username);
                ApplicationRegistry.register(IctcCKwUtil.KEY_FULNAME, fullName);
                ApplicationRegistry.register(IctcCKwUtil.KEY_VERSION, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                ImageUtils.verifyDirectory();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //super.onDestroy();

        //onsaveinstance does not always get called for the ckw list pages uses the destroy for those pages
        if(!this.getLocalClassName().contains("SearchMenuItemActivity")){
            System.out.println("NOn CKW save on destroyi : "+this.getLocalClassName());
            baseLogActivity.save();
        }

    }

    public void setDetails(DatabaseHelper dh, String module, String page){
        baseLogActivity.setItemValues(dh,module,page,"","");
    }
    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        baseLogActivity.setItemValues(dh, module, page, section, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.getLocalClassName().contains("SearchMenuItemActivity")){
            System.out.println("CKW save on destroy");
            baseLogActivity.save();
        }
    }

   /* @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
*/
    /*@Override
    protected void onStop() {
        super.onStop();
    }*/


    public void showHome(View view){

        Intent t =  new Intent(view.getContext(),DashboardSmartExActivity.class);
        view.getContext().startActivity(t);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    protected void createProgressBar() {
        handler.post(new Runnable() {
            public void run() {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Updating");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(false);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setIcon(R.mipmap.ic_refresh);
                progressDialog.setProgressNumberFormat(null);

                if (SynchronizationManager.getInstance().isSynchronizing()) {
                    progressDialog.show();
                }
            }
        });
    }

    protected void startSynchronization() {
        if (ConnectionUtil.isNetworkConnected(this)) {
            SynchronizationManager.getInstance().registerListener(new SynchronizationListener() {
                public void synchronizationStart() {
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.show();
                        }
                    });
                }

                public void synchronizationUpdate(final Integer step, final Integer max, final String message, Boolean reset) {
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.setMessage(message);
                            progressDialog.setMax(max);
                            progressDialog.setProgress(step);
                            progressDialog.setIndeterminate(false);
                            if (!progressDialog.isShowing()) {
                                progressDialog.show();
                            }
                        }
                    });
                }

                public void synchronizationUpdate(final String message, Boolean indeterminate) {
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.setMessage(message);
                            progressDialog.setIndeterminate(true);
                            if (!progressDialog.isShowing()) {
                                progressDialog.show();
                            }
                        }
                    });
                }

                public void synchronizationComplete() {
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            //onRefresh();
                        }
                    });

                    SynchronizationManager.getInstance().unRegisterListener(this);
                }

                public void onSynchronizationError(final Throwable throwable) {
                    handler.post(new Runnable() {
                        public void run() {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(BaseActivityGroup.this).create();
                            alertDialog.setMessage(throwable.getMessage());
                            alertDialog.setIcon(android.R.drawable.stat_sys_warning);

                            alertDialog.setTitle("Error");
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }
                    });

                    SynchronizationManager.getInstance().unRegisterListener(this);
                }
            });
            SynchronizationManager.getInstance().start();
        } else {
            new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Internet Connection")
                    .setContentText("Check your internet connection and try again.")
                    .setCancelText("Close")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }
    public void onRefresh() {



    };
    protected void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to really log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                helper.resetFarmer();
                ConnectionUtil.unSetUser(BaseActivityGroup.this);
                Intent intent = new Intent().setClass(BaseActivityGroup.this, StartUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        builder.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if(item.getItemId()==R.id.search_user){
                SearchView user_search = (SearchView) item.getActionView();

                user_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Toast.makeText(BaseActivityGroup.this, query, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BaseActivityGroup.this, FarmerActivity.class);
                        intent.putExtra("type", "search");
                        String q = query;
                        intent.putExtra("q", q);
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String text) {
                        return true;
                    }
                });
            }
            else if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent().setClass(this, SettingsActivity.class);
                startActivityForResult(intent, 0);

            } else if (item.getItemId() == R.id.action_about) {
                Intent intent = new Intent().setClass(this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);

            } else if (item.getItemId() == R.id.action_refresh_farmer) {
               if( IctcCKwUtil.haveNetworkConnection(this)) {
                    Toast.makeText(getBaseContext(), "Synchronising Data Please wait", Toast.LENGTH_LONG).show();
                    DatabaseHelper dbh = new DatabaseHelper(getBaseContext());
                    Payload mqp = dbh.getCCHUnsentLog();
                    IctcTrackerLogTask omUpdateCCHLogTask = new IctcTrackerLogTask(this);
                    omUpdateCCHLogTask.execute(mqp);
                    ConnectionUtil.refreshWeather(getBaseContext(), "weather", "Get latest weather report");
                    ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");
                    startSynchronization();
                }else{
                   new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("Internet Connection")
                           .setContentText("Check your internet connection and try again.")
                           .setCancelText("Close")
                           .showCancelButton(true)
                           .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   sDialog.dismissWithAnimation();
                               }
                           })
                           .show();
               }

            } else if (item.getItemId() == R.id.action_logout) {
                logout();

            } else if (item.getItemId() == android.R.id.home) {
               /* Intent intent = new Intent().setClass(this, DashboardSmartExActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);*/

                finish();
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        return true;
    }
}
