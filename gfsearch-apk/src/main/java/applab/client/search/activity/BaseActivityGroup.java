package applab.client.search.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.BaseLogActivity;

/**
 * Created by skwakwa on 10/12/15.
 */
public class BaseActivityGroup extends ActivityGroup {
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
        baseLogActivity.setItemValues(dh,module,page,section,data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.getLocalClassName().contains("SearchMenuItemActivity")){
            System.out.println("CKW save on destroy");
            baseLogActivity.save();
        }
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
