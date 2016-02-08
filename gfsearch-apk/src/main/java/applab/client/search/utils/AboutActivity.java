package applab.client.search.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.activity.BaseActivity;
import applab.client.search.storage.DatabaseHelper;

/**
 * Created by Software Developer on 08-Oct-15.
 */
public class AboutActivity extends BaseActivity {
    private TextView about;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        about=(TextView) findViewById(R.id.about_version);
        about.setText("Version "+IctcCKwUtil.getAppVersion(getBaseContext()));
        super.setDetails(new DatabaseHelper(getBaseContext()),"About","About");
    }
}