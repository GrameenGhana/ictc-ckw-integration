package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
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

        setContentView(R.layout.activity_scheduled_meetings);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Meeting Schedule");
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Meeting Schedule");
       // mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup(this.getLocalActivityManager());

        Intent  gencal  = new Intent(ScheduledMeetingsActivity.this, GeneralAgentCalendarActivity.class);
        TabHost.TabSpec spec =tabHost.newTabSpec("Meetings").setIndicator("Meetings").setContent(gencal);
        tabHost.addTab(spec);
        gencal  = new Intent(ScheduledMeetingsActivity.this, IndividualMeetingByCrop.class);
        gencal.putExtra("type","individual");
        spec =tabHost.newTabSpec("Individual").setIndicator("Individual").setContent(gencal);

        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        super.setDetails(new DatabaseHelper(getBaseContext()),"Meeting","List of Meetings");
//        TabHost.TabSpec spec =
    }

}