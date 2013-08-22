package org.grameenfoundation.search;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import org.grameenfoundation.search.settings.SettingsActivity;
import org.grameenfoundation.search.synchronization.SynchronizationListener;
import org.grameenfoundation.search.synchronization.SynchronizationManager;
import org.grameenfoundation.search.utils.DeviceMetadata;

public class MainActivity extends Activity {
    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    private Context activityContext;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = this;
        ApplicationRegistry.setApplicationContext(this.getApplicationContext());
        setContentView(R.layout.main);
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //caching the device imie in the application registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                DeviceMetadata.getDeviceImei(this.getApplicationContext()));

        //register application version in registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION,
                getResources().getString(R.string.app_name) + "/" + R.string.app_version);

        handler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_view).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent().setClass(this, SettingsActivity.class);
            this.startActivityForResult(intent, 0);
        } else if (item.getItemId() == R.id.action_synchronise) {
            startSynchronization();
        }

        return true;
    }

    private void startSynchronization() {
        SynchronizationManager.getInstance().registerListener(new SynchronizationListener() {
            @Override
            public void synchronizationStart() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(activityContext);
                            progressDialog.setTitle(R.string.synchronization_progress_bar_title);
                            progressDialog.setCancelable(true);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setIndeterminate(false);
                            progressDialog.setMessage("Please Wait...");
                            progressDialog.setIcon(R.drawable.ic_refresh);
                            progressDialog.setProgressNumberFormat(null);
                        }

                        progressDialog.show();
                    }
                });
            }

            @Override
            public void synchronizationUpdate(final Integer step, final Integer max, final String message, Boolean reset) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage(message);
                        progressDialog.setMax(max);
                        progressDialog.setProgress(step);
                    }

                });
            }

            @Override
            public void synchronizationComplete() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }

                });
            }

            @Override
            public void onSynchronizationError(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        AlertDialog alertDialog =
                                new AlertDialog.Builder(getApplicationContext().getApplicationContext()).create();
                        alertDialog.setMessage(throwable.getMessage());
                        alertDialog.setIcon(android.R.drawable.stat_sys_warning);

                        alertDialog.setTitle(R.string.error_title);
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                    }
                });
            }
        });

        SynchronizationManager.getInstance().start();
    }
}

