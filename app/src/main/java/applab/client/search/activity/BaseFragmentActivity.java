package applab.client.search.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import applab.client.agrihub.activity.DashboardMainActivity;
import applab.client.search.R;
import applab.client.search.model.Payload;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.synchronization.SynchronizationListener;
import applab.client.search.synchronization.SynchronizationManager;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.utils.AboutActivity;
import applab.client.search.utils.BaseLogActivity;
import applab.client.search.utils.ConnectionUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseFragmentActivity extends AppCompatActivity {

    BaseLogActivity baseLogActivity;
    private DatabaseHelper helper;
    private String TAG;
    private ProgressDialog progressDialog;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseLogActivity = new BaseLogActivity(getBaseContext());
        helper = new DatabaseHelper(getBaseContext());
        if(getActionBar()!=null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        handler = new Handler();
        createProgressBar();
    }

    public DatabaseHelper Db() { return helper; }

    public void setDetails(DatabaseHelper dh, String module, String page){
        baseLogActivity.setItemValues(dh,module,page,"","");
    }

    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        baseLogActivity.setItemValues(dh,module,page,section,data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseLogActivity.save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void showHome(View view){
        Intent t = (ConnectionUtil.isSmartExAgent(this))
            ? new Intent(view.getContext(),DashboardMainActivity.class)
            : new Intent(view.getContext(),DashboardSmartExActivity.class);
        view.getContext().startActivity(t);
    }

    protected void createProgressBar() {
        handler.post(new Runnable() {
            public void run() {
                progressDialog = new ProgressDialog(BaseFragmentActivity.this);
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
                            onRefresh();
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

                            AlertDialog alertDialog = new AlertDialog.Builder(BaseFragmentActivity.this).create();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("No Internet connection");
            builder.setMessage("Cannot update data at the moment. Try again when the device is connected.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            builder.show();
        }
    }
    public void onRefresh() {};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if(item.getItemId()==R.id.search_user){
                SearchView user_search = (SearchView) item.getActionView();
                user_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Toast.makeText(BaseFragmentActivity.this,query,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BaseFragmentActivity.this, FarmerActivity.class);
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
                Toast.makeText(getBaseContext(),"Synchronising Data Please wait",Toast.LENGTH_LONG).show();
                DatabaseHelper dbh = new DatabaseHelper(getBaseContext());
                Payload mqp = dbh.getCCHUnsentLog();
                IctcTrackerLogTask omUpdateCCHLogTask = new IctcTrackerLogTask(this);
                omUpdateCCHLogTask.execute(mqp);
                ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report");
                ConnectionUtil.refreshFarmerInfo(BaseFragmentActivity.this, null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");
                startSynchronization();

            } else if (item.getItemId() == R.id.action_logout) {
                logout();

            } else if (item.getItemId() == android.R.id.home) {
                finish();
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        return true;
    }
    protected void logout() {
        new SweetAlertDialog(BaseFragmentActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Logout")
                .setContentText("Do you want to really log out?")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        helper.resetFarmer();
                        ConnectionUtil.unSetUser(BaseFragmentActivity.this);
                        Intent intent = new Intent().setClass(BaseFragmentActivity.this, StartUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to really log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                helper.resetFarmer();
                ConnectionUtil.unSetUser(BaseFragmentActivity.this);
                Intent intent = new Intent().setClass(BaseFragmentActivity.this, StartUpActivity.class);
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
        builder.show();*/
    }

}
