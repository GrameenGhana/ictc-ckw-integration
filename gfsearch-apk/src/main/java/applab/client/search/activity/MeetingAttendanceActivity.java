package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;
import applab.client.search.R;
import applab.client.search.model.Farmer;

/**
 * Created by skwakwa on 10/30/15.
 */
public class MeetingAttendanceActivity extends BaseActivityGroup {
    String title;
    Farmer f;
    int index;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_meetings);


        TabHost tabHost = (TabHost)findViewById(R.id.tabHost4);
        tabHost.setup(this.getLocalActivityManager());
        Bundle extras = getIntent().getExtras();
        int meetingIndex = 0;

        if (extras != null) {
            f= (Farmer) extras.get("farmer");
            title = (String)extras.get("title");
            index= extras.getInt("index");
        }
        Intent gencal  = new Intent(MeetingAttendanceActivity.this, IndividualMeetingAAttendance.class);
        gencal.putExtra("farmer",f);
        gencal.putExtra("title",title);
        gencal.putExtra("index",index);

        gencal.putExtra("type","Group");
        TabHost.TabSpec spec =tabHost.newTabSpec("Meetings").setIndicator("Meetings").setContent(gencal);
        tabHost.addTab(spec);
        gencal  = new Intent(MeetingAttendanceActivity.this, MeetingAttendeeActivity.class);
        gencal.putExtra("farmer",f);
        gencal.putExtra("title",title);
        gencal.putExtra("index",index);
        gencal.putExtra("type","Group");
        spec =tabHost.newTabSpec("Attendance").setIndicator("Attendance").setContent(gencal);

        tabHost.addTab(spec);
    }
}