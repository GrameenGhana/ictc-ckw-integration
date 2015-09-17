package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import applab.client.search.R;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/10/15.
 */
public class AgentMeetings extends Activity {
    private ListView list;
    DatabaseHelper helper=null;
    List<Meeting> meetings = new ArrayList<Meeting>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.list_agent_ind_meeting);
        helper = new DatabaseHelper(getBaseContext());
        String type;
        String crop;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = (String) extras.get("type");
            crop = (String) extras.get("crop");

            if(type.equalsIgnoreCase("individual"))
                meetings = helper.getIndividualMeetings(crop);
            else
                meetings = helper.getGroupMeetings();
        }

//        helper = new DatabaseHelper(getBaseContext());
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
        //lst_agt_list
        list = (ListView) findViewById(R.id.lst_meet_view);

        System.out.println("Meeting Indexx : "+meetings.size());
        System.out.println("Ktd : "+list.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(AgentMeetings.this, meetings, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AgentMeetings.this,MeetingIndexActivity.class);
                intent.putExtra("mi",meetings.get(i).getMeetingIndex());
                intent.putExtra("mt", meetings.get(i).getTitle());
                intent.putExtra("farmerId", meetings.get(i).getFarmer());
                startActivity(intent);


            }
        });
    }

}