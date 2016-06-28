package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.BaseLogActivity;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class BaseActivity extends Activity {

    BaseLogActivity baseLogActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        baseLogActivity = new BaseLogActivity(getBaseContext());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        super.onDestroy();
        System.out.println("onSaveInstanceState for " + this.getLocalClassName());

        //onsaveinstance does not always get called for the ckw list pages uses the destroy for those pages
//        if(!this.getLocalClassName().contains("SearchMenuItemActivity")){
            baseLogActivity.save();
//         }

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
        System.out.println("Destroyed for "+this.getLocalClassName());
//        if(this.getLocalClassName().contains("SearchMenuItemActivity") || this.getLocalClassName().contains("ImageViewerActivity") ){
            System.out.println("CKW save on destroy");
            baseLogActivity.save();
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