package org.grameenfoundation.search;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import org.grameenfoundation.search.model.ListObject;
import org.grameenfoundation.search.services.MenuItemService;
import org.grameenfoundation.search.settings.SettingsActivity;
import org.grameenfoundation.search.settings.SettingsManager;
import org.grameenfoundation.search.synchronization.SynchronizationListener;
import org.grameenfoundation.search.synchronization.SynchronizationManager;
import org.grameenfoundation.search.ui.AboutActivity;
import org.grameenfoundation.search.ui.MainListViewAdapter;
import org.grameenfoundation.search.ui.OnSwipeTouchListener;
import org.grameenfoundation.search.ui.SearchMenuItemActivity;
import org.grameenfoundation.search.utils.DeviceMetadata;

import java.util.Stack;

public class MainActivity extends Activity {
    private static final String NAVIGATION_STACK_SAVED_STATE_KEY = "navigation_stack_state_key";
    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    private Context activityContext;
    private Stack<ListObject> listObjectNavigationStack = null;
    private ListView mainListView;
    private MenuItem backNavigationMenuItem = null;

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
            setContentView(R.layout.main);
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

            handler = new Handler();

            if (savedInstanceState != null
                    && savedInstanceState.containsKey(NAVIGATION_STACK_SAVED_STATE_KEY)) {
                listObjectNavigationStack =
                        (Stack<ListObject>) savedInstanceState.get(NAVIGATION_STACK_SAVED_STATE_KEY);
            } else {
                listObjectNavigationStack = new Stack<ListObject>();
            }

            initMainListView();
            createProgressBar();

            /**
             * auto start synchronization when their are no search
             * menu items. This is for a clean database.
             */
            if (new MenuItemService().countSearchMenus() == 0) {
                startSynchronization();
            }
        } catch (Exception ex) {
            Log.e(MainActivity.class.getName(), "Application Error", ex);
        }
    }

    private void initMainListView() {
        mainListView = (ListView) this.findViewById(R.id.main_list);
        final MainListViewAdapter listViewAdapter = new MainListViewAdapter(this);
        mainListView.setAdapter(listViewAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListObject itemToSelect = (ListObject) listViewAdapter.getItem(position);
                selectListElement(itemToSelect, listViewAdapter);
            }
        });

        mainListView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }

            @Override
            public boolean onSwipeRight() {
                super.onSwipeRight();
                listViewBackNavigation();
                return false;
            }
        });

        if (!listObjectNavigationStack.isEmpty()) {
            selectListElement(listObjectNavigationStack.pop(), listViewAdapter);
        }
    }

    private void selectListElement(ListObject itemToSelect, MainListViewAdapter listViewAdapter) {
        if (listViewAdapter.hasChildren(itemToSelect)) {
            listViewAdapter.setSelectedObject(itemToSelect);
            listObjectNavigationStack.push(listViewAdapter.getSelectedObject());

            if (backNavigationMenuItem != null) {
                backNavigationMenuItem.setVisible(true);
            }
        } else {
            Intent intent = new Intent().setClass(activityContext, SearchMenuItemActivity.class);
            intent.putExtra(SearchMenuItemActivity.EXTRA_LIST_OBJECT_IDENTIFIER, itemToSelect);
            this.startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save state of
        if (listObjectNavigationStack != null && !listObjectNavigationStack.isEmpty()) {
            outState.putSerializable(NAVIGATION_STACK_SAVED_STATE_KEY, listObjectNavigationStack);
        }
    }

    private void listViewBackNavigation() {
        MainListViewAdapter listViewAdapter = (MainListViewAdapter) mainListView.getAdapter();

        //we pop the stack twice to get the right navigation element.
        if (!listObjectNavigationStack.isEmpty())
            listObjectNavigationStack.pop();

        if (!listObjectNavigationStack.isEmpty()) {
            listViewAdapter.setSelectedObject(listObjectNavigationStack.peek());
            return;
        }

        if (listObjectNavigationStack.isEmpty()) {
            listViewAdapter.setSelectedObject(null);
            backNavigationMenuItem.setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main, menu);
        backNavigationMenuItem = menu.findItem(R.id.action_nav_back);
        if (listObjectNavigationStack != null
                && !listObjectNavigationStack.isEmpty()) {
            backNavigationMenuItem.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent().setClass(this, SettingsActivity.class);
                this.startActivityForResult(intent, 0);
            } else if (item.getItemId() == R.id.action_nav_back) {
                listViewBackNavigation();
            } else if (item.getItemId() == R.id.action_synchronise) {
                startSynchronization();
            } else if (item.getItemId() == R.id.action_about) {
                Intent intent = new Intent().setClass(this, AboutActivity.class);
                this.startActivityForResult(intent, 0);
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
                        ((MainListViewAdapter) mainListView.getAdapter()).refreshData();
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
}

