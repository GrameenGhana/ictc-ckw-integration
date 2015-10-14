package applab.client.search.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import applab.client.search.R;

/**
 * Created by Software Developer on 08-Oct-15.
 */
public class AboutActivity extends Activity {
    private TextView about;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        about=(TextView) findViewById(R.id.about_version);
    }
}