package applab.client.search.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import applab.client.search.R;

/**
 * Created by skwakwa on 9/15/15.
 */
public class IndividualMeetingByCrop  extends ActivityGroup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_meeting_by_group_activity);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost2);
        tabHost.setup(this.getLocalActivityManager());
        String []  crops = {"Maize","Cassava","Yam","Rice"};
        Intent gencal  = null;
        for (String crop:crops){
             gencal  = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
            gencal.putExtra("type","individual");
            gencal.putExtra("crop",crop);
            TabHost.TabSpec spec =tabHost.newTabSpec(crop).setIndicator(crop).setContent(gencal);
            tabHost.addTab(spec);
        }
        tabHost.setCurrentTab(0);
    }
}