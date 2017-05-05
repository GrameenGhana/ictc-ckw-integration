package applab.client.search.activity;

import android.os.Bundle;
import applab.client.search.R;

public class StartUpActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        if (Db().needDataUpdate()) {
            startSynchronization();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        showHome(this);
    }



}