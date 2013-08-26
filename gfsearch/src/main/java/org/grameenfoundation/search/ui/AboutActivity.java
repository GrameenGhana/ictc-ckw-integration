package org.grameenfoundation.search.ui;

import android.app.Activity;
import android.os.Bundle;
import org.grameenfoundation.search.R;

/**
 * An Activity to show the about information of the application
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
}
