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
import applab.client.search.utils.BaseAppActivity;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseFragmentActivity extends FragmentActivity{

    BaseAppActivity baseAppActivity;

    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        baseAppActivity= new BaseAppActivity(getBaseContext());
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseAppActivity.save();
//        }

    }

    public void setDetails(DatabaseHelper dh, String module, String page){
        baseAppActivity.setItemValues(dh,module,page,"","");
    }
    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        baseAppActivity.setItemValues(dh,module,page,section,data);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject obj=null;
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
        Intent t =  new Intent(view.getContext(),DashboardActivity.class);
        view.getContext().startActivity(t);
    }


}
