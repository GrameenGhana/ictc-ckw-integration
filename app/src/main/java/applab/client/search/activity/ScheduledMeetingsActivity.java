package applab.client.search.activity;

import android.app.LocalActivityManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 8/30/15.
 */
public class ScheduledMeetingsActivity extends BaseActivityGroup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_scheduled_meetings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();




        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Meeting Schedule");

        /*final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Meeting Schedule");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);*/

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);

        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

        tabHost.setup(mLocalActivityManager);


        Intent  gencal  = new Intent(ScheduledMeetingsActivity.this, GeneralAgentCalendarActivity.class);
        gencal.putExtra("type","group");

        TabHost.TabSpec spec = tabHost.newTabSpec("Meetings");
        spec.setIndicator("Group");
        spec.setContent(gencal);
        tabHost.addTab(spec);

        gencal  = new Intent(ScheduledMeetingsActivity.this, IndividualMeetingByCrop.class);
        gencal.putExtra("type","individual");

        spec = tabHost.newTabSpec("Individual");
        spec.setIndicator("Individual");
        spec.setContent(gencal);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);



        super.setDetails(new DatabaseHelper(getBaseContext()), "Meeting", "List of Meetings");
//
    }






}