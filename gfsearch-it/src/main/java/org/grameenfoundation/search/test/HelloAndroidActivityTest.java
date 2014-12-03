package org.grameenfoundation.search.test;

import android.test.ActivityInstrumentationTestCase2;
import applab.client.search.MainActivity;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public HelloAndroidActivityTest() {
        super(MainActivity.class);
    }

    public void testActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }
}

