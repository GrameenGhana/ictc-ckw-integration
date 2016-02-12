package applab.client.search.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.BaseLogActivity;
import org.json.JSONObject;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseFragmentActivity extends FragmentActivity{

    BaseLogActivity baseLogActivity;

    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        baseLogActivity = new BaseLogActivity(getBaseContext());
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseLogActivity.save();
//        }

    }

    public void setDetails(DatabaseHelper dh, String module, String page){
        baseLogActivity.setItemValues(dh,module,page,"","");
    }
    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        baseLogActivity.setItemValues(dh,module,page,section,data);
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
