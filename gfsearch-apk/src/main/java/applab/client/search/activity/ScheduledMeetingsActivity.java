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

/**
 * Created by skwakwa on 8/30/15.
 */
public class ScheduledMeetingsActivity extends ActivityGroup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scheduled_meetings);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Meeting Schedule");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup(this.getLocalActivityManager());

        Intent  gencal  = new Intent(ScheduledMeetingsActivity.this, GeneralAgentCalendarActivity.class);
        TabHost.TabSpec spec =tabHost.newTabSpec("Meetings").setIndicator("Meetings").setContent(gencal);
        tabHost.addTab(spec);
        gencal  = new Intent(ScheduledMeetingsActivity.this, MeetingByGroupActivity.class);
        spec =tabHost.newTabSpec("Individual").setIndicator("Individual").setContent(gencal);

        tabHost.addTab(spec);

        gencal  = new Intent(ScheduledMeetingsActivity.this, MeetingByGroupActivity.class);
        spec =tabHost.newTabSpec("Group").setIndicator("Group").setContent(gencal);

        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
//        TabHost.TabSpec spec =
    }



    public void mkAttendance(View view){
        Intent intent = new Intent(ScheduledMeetingsActivity.this, ListCheckBoxActivity.class);
//        intent.putExtra("type","search");
//        intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

        startActivity(intent);

    }
}