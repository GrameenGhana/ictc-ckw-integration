package applab.client.search.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import applab.client.search.DefaultViewFragment;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.services.MenuItemService;
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
 * Created by Software Developer on 30/07/2015.
 */
public class BaseActivity extends Activity {

    BaseAppActivity baseAppActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        baseAppActivity= new BaseAppActivity(getBaseContext());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        super.onDestroy();
        System.out.println("onSaveInstanceState for " + this.getLocalClassName());

        //onsaveinstance does not always get called for the ckw list pages uses the destroy for those pages
//        if(!this.getLocalClassName().contains("SearchMenuItemActivity")){
            baseAppActivity.save();
//         }

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
        System.out.println("Destroyed for "+this.getLocalClassName());
//        if(this.getLocalClassName().contains("SearchMenuItemActivity") || this.getLocalClassName().contains("ImageViewerActivity") ){
            System.out.println("CKW save on destroy");
            baseAppActivity.save();
//        }
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