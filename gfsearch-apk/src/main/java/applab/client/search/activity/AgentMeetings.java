package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import applab.client.search.R;
import applab.client.search.adapters.ClusterAdapter;
import applab.client.search.adapters.FarmerMeetingGrpAdapter;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.utils.AgentVisitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 9/10/15.
 */
public class AgentMeetings extends BaseActivity {
    private ExpandableListView list;
    DatabaseHelper helper=null;
    List<Meeting> meetings = new ArrayList<Meeting>();
    String type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> clusters = new ArrayList<String>();
        final Map<String, List<Meeting>> clustersDate = new HashMap<String, List<Meeting>>();



        setContentView(R.layout.list_agent_ind_meeting);
        helper = new DatabaseHelper(getBaseContext());

        String crop;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = (String) extras.get("type");
            crop = (String) extras.get("crop");

            if(type.equalsIgnoreCase("individual")) {
             for(int i=0;i<=6;i++) {
                 System.out.println("Indivudial meeting : "+crop+"  weeker "+i);
                 meetings = helper.getIndividualMeetings(crop,String.valueOf(i));
                 if(meetings.size()>0){
                     String title= meetings.get(0).getMeetingIndex()+" Individual Meeting";
                     clusters.add(title);
                     clustersDate.put(title,meetings);
                 }
             }
            }else{
                for(int i=0;i<=6;i++) {
//                meetings = helper.getGroupMeetings();
                    meetings = helper.getGroupMeetings(crop, String.valueOf(i));
                    if (meetings.size() > 0) {
                        String title = meetings.get(0).getMeetingIndex() + " Group Meeting";
                        clusters.add(title);
                        clustersDate.put(title, meetings);
                    }
                }
            }
        }

//        helper = new DatabaseHelper(getBaseContext());
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
        //lst_agt_list
        list = (ExpandableListView) findViewById(R.id.lst_meet_view);
        list.setGroupIndicator(null);
        System.out.println("Meeting Indexx : "+meetings.size());
        System.out.println("Ktd : "+list.toString());

        super.setDetails(helper,"Client","Client Home");
        String  [] colors =  getResources().getStringArray(R.array.text_colors);

        FarmerMeetingGrpAdapter adapter = new FarmerMeetingGrpAdapter(AgentMeetings.this, clusters, clustersDate,AgentMeetings.this.getResources().getStringArray(R.array.text_colors), list);

//        MeetingInvAdapter adapter = new MeetingInvAdapter(AgentMeetings.this, meetings, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(AgentMeetings.this, MeetingIndexActivity.class);
//                intent.putExtra("mi", meetings.get(i).getMeetingIndex());
//                intent.putExtra("mt", meetings.get(i).getTitle());
//                intent.putExtra("farmerId", meetings.get(i).getFarmer());
//                intent.putExtra("mid", meetings.get(i).getId());
//                intent.putExtra("mtype", meetings.get(i).getType());
//                intent.putExtra("atd", meetings.get(i).getAttended());
//                System.out.println("Sendt ID : " + meetings.get(i).getId());
//                intent.putExtra("mt", meetings.get(i).getTitle());
//                intent.putExtra("farmerId", meetings.get(i).getFarmer());
//                startActivity(intent);
//
//
//            }
//        });

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                System.out.println("onChild Click: " + i + " - " + i1);
                Meeting  m =  clustersDate.get(clusters.get(i)).get(i1);

                Intent intent = new Intent(AgentMeetings.this, MeetingIndexActivity.class);
                int act = AgentVisitUtil.getMeetingPosition(m.getMeetingIndex(),type);

//                System.out.println("Meeting Type  "+act);

                MeetingActivity  met = AgentVisitUtil.getMeetingDetails(act);
//                System.out.println("Meeting Idx : "+met.getMeetingIndex());
//                System.out.println("Meeting Nm  : "+met.getActivityName());


                intent.putExtra("mi",act);
                intent.putExtra("mt", met.getActivityName());
                intent.putExtra("farmerId", m.getFarmer());
                intent.putExtra("mid", m.getId());
                intent.putExtra("mtype", m.getType());
                intent.putExtra("atd", m.getAttended());
                System.out.println("Sendt ID : " + m.getId());
//                intent.putExtra("mt", m.getTitle());
                intent.putExtra("farmerId", m.getFarmer());
                startActivity(intent);
                return false;
            }


        });

    }

}