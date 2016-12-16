package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.MeetingActivity;
import applab.client.search.model.NextMeetingItem;
import applab.client.search.model.wrapper.MeetingSettingWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;
import applab.client.search.utils.IctcCKwUtil;

import java.util.List;

/**
 * Created by skwakwa on 10/22/15.
 */
public class NextMeetingActivity extends BaseActivity {
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nexxt_meeting_item);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Next Meeting Activity");
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Next Meeting Activity");

        IctcCKwUtil.setActionbarUserDetails(this,mCustomView);
        DatabaseHelper dh = new DatabaseHelper(getBaseContext());
        super.setDetails(dh, "Client", "Next Activity Item", "", "");
       // mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);


        Bundle extras = getIntent().getExtras();
        int meetingIndex = 0;
        String meetingType;
        String crop;
        if (extras != null) {
            meetingIndex = (Integer) extras.get("mi");
            meetingType = (String) extras.get("mtype");
            crop = (String) extras.get("crop");


            if(null == meetingType)
                meetingType="Group";
            Farmer f= (Farmer) extras.get("farmer");

            TextView tv= (TextView)findViewById(R.id.txt_nxt_header);
            System.out.println("Meeting Index : "+meetingIndex+" - "+meetingType);
            MeetingActivity details = AgentVisitUtil.getMeetingDetails(meetingIndex,meetingType);
            tv.setText(details.getActivityName()+" Next Activities");

            if(null == f){
                IctcCKwUtil.setFarmerDetails(this,R.id.ccs_layout,"",null,false);
            }else{
                IctcCKwUtil.setFarmerDetails(this,R.id.ccs_layout,f.getFullname(),f,true);
            }


            System.out.println("meeting Index: "+details.getMeetingIndex());
            System.out.println("meetingCrop: "+crop);
            List<MeetingSettingWrapper> meetings = dh.getMeetingSettings(meetingType, String.valueOf(details.getMeetingIndex()), crop);
            if (!meetings.isEmpty()) {
                List<NextMeetingItem> meetingItems = meetings.get(0).getMeetingActivities();

                final boolean[] enabled = new boolean[meetingItems.size()];
                System.out.println("Meeting Sixe : " + meetings.size());
                int cnt = 0;
                final String[] titles = new String[meetingItems.size()];
                final String[] icons = new String[meetingItems.size()];
                for (NextMeetingItem act : meetingItems) {
                    titles[cnt] = act.getName();
                    icons[cnt] = String.valueOf(cnt + 1);
                    enabled[cnt] = true;
                    cnt++;
                }


                list = (ListView) findViewById(R.id.lst_nxt_meeting);


                SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(NextMeetingActivity.this, titles, icons, enabled, getResources().getStringArray(R.array.text_colors));
                list.setAdapter(adapter);
            }
        }
    }
}