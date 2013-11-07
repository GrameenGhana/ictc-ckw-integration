package org.grameenfoundation.search;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.grameenfoundation.search.location.GpsManager;
import org.grameenfoundation.search.services.MenuItemService;
import org.grameenfoundation.search.settings.SettingsActivity;
import org.grameenfoundation.search.settings.SettingsManager;
import org.grameenfoundation.search.synchronization.BackgroundSynchronizationConfigurer;
import org.grameenfoundation.search.synchronization.SynchronizationListener;
import org.grameenfoundation.search.synchronization.SynchronizationManager;
import org.grameenfoundation.search.ui.AboutActivity;
import org.grameenfoundation.search.utils.DeviceMetadata;

public class MainActivity extends Activity {
    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    private Context activityContext;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] drawerListItems;
    private ActionBarDrawerToggle drawerToggle;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            activityContext = this;
            ApplicationRegistry.setApplicationContext(this.getApplicationContext());
            ApplicationRegistry.setMainActivity(this);

            setContentView(R.layout.main);
            initNavigationDrawer();

            ActionBar actionBar = this.getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            //caching the device imie in the application registry
            ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                    DeviceMetadata.getDeviceImei(this.getApplicationContext()));

            //register application version in registry
            ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION,
                    getResources().getString(R.string.app_name) + "/" + R.string.app_version);

            //prepare default settings.
            SettingsManager.getInstance().setDefaultSettings(false);

            //setup background synchronization
            initiateBackgroundSyncConfiguration();

            handler = new Handler();

            //initMainListView();
            createProgressBar();

            /**
             * auto start synchronization when their are no search
             * menu items. This is for a clean database.
             */
            if (new MenuItemService().countSearchMenus() == 0) {
                startSynchronization();
            }

            //get GPS location
            GpsManager.getInstance().update();

            selectItem(0);
        } catch (Exception ex) {
            Log.e(MainActivity.class.getName(), "Application Error", ex);
        }
    }

    private void initiateBackgroundSyncConfiguration() {
        Intent intent = new Intent(BackgroundSynchronizationConfigurer.ACTION_BACKGROUND_SYNC_CONFIGURATION);
        this.sendBroadcast(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main, menu);
/*        backNavigationMenuItem = menu.findItem(R.id.action_nav_back);
        if (listObjectNavigationStack != null
                && !listObjectNavigationStack.isEmpty()) {
            backNavigationMenuItem.setVisible(true);
        }*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }

            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent().setClass(this, SettingsActivity.class);
                this.startActivityForResult(intent, 0);
            } /*else if (item.getItemId() == R.id.action_nav_back) {
                listViewBackNavigation();
            } */ else if (item.getItemId() == R.id.action_synchronise) {
                startSynchronization();
            } else if (item.getItemId() == R.id.action_about) {
                Intent intent = new Intent().setClass(this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                this.startActivity(intent);
            } else if (item.getItemId() == android.R.id.home) {
                //resetDisplayMenus();
                selectItem(0);
            }
        } catch (Exception ex) {
            Log.e(MainActivity.class.getName(), "", ex);
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
                        progressDialog.setIndeterminate(false);
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    }
                });
            }

            @Override
            public void synchronizationUpdate(final String message, Boolean indeterminate) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage(message);
                        progressDialog.setIndeterminate(true);
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    }
                });
            }

            @Override
            public void synchronizationComplete() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        //((MainListViewAdapter) mainListView.getAdapter()).refreshData();
                        //TODO notify fragement to refresh data.
                    }

                });

                SynchronizationManager.getInstance().unRegisterListener(this);
            }

            @Override
            public void onSynchronizationError(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        AlertDialog alertDialog =
                                new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setMessage(throwable.getMessage());
                        alertDialog.setIcon(android.R.drawable.stat_sys_warning);

                        alertDialog.setTitle(R.string.error_title);
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                    }
                });

                SynchronizationManager.getInstance().unRegisterListener(this);
            }
        });

        SynchronizationManager.getInstance().start();
    }

    private void createProgressBar() {
        progressDialog = new ProgressDialog(activityContext);
        progressDialog.setTitle(R.string.synchronization_progress_bar_title);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIcon(R.drawable.ic_refresh);
        progressDialog.setProgressNumberFormat(null);

        if (SynchronizationManager.getInstance().isSynchronizing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        /*if (listObjectNavigationStack != null && !listObjectNavigationStack.isEmpty()) {
            listViewBackNavigation();
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //hide menu items when drawer is open
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);


        for (int index = 0; index < menu.size(); index++) {
            menu.getItem(index).setVisible(!drawerOpen);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //sync the toggle state after onRestoreInstanceState has occurred
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void initNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerListItems = getResources().getStringArray(R.array.drawer_items);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerListItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setHomeButtonEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch (position) {
            case 0:  //default view
                displayDefaultFragment();
                break;
            case 1: //favourite view
                displayFavouriteFragment();
                break;
            case 2: //recent searches
                displayRecentSearchesFragment();
                break;
            default:
                displayDefaultFragment();
                break;
        }

        drawerList.setItemChecked(position, true);
        setTitle(drawerListItems[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    private void displayRecentSearchesFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(RecentSearchesViewFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new RecentSearchesViewFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment,
                RecentSearchesViewFragment.FRAGMENT_TAG).commit();

    }

    private void displayFavouriteFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(FavouriteViewFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new FavouriteViewFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment,
                FavouriteViewFragment.FRAGMENT_TAG).commit();
    }

    private void displayDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(DefaultViewFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new DefaultViewFragment();
        }

        //insert the fragment by replacing the existing fragment.
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment,
                DefaultViewFragment.FRAGMENT_TAG).commit();
    }
}

