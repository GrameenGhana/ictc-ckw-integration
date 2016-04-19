package applab.client.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import applab.client.agrihub.activity.DashboardMainActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.BaseLogActivity;
import applab.client.search.utils.ConnectionUtil;

public class BaseFragmentActivity extends FragmentActivity {

    BaseLogActivity baseLogActivity;
    private DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseLogActivity = new BaseLogActivity(getBaseContext());
        helper = new DatabaseHelper(getBaseContext());
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
}
