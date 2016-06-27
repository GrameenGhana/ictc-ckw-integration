package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import applab.client.search.R;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/23/15.
 */
public class FarmerMeetingActivity extends BaseActivity {

    ListView list;
    DatabaseHelper dbHelper=null;
    String farmer = null;
    List<Meeting> meetings = new ArrayList<Meeting>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_meeting_activity);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (String) extras.get("farmer");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbHelper = new DatabaseHelper(getBaseContext());

        list = (ListView) findViewById(R.id.lst_ind_farm_visit);
        meetings =  dbHelper.getFarmerMeetings(farmer);

        System.out.println("Meeting Indexx : "+meetings.size());
        System.out.println("Ktd : "+list.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(FarmerMeetingActivity.this, meetings, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmerMeetingActivity.this,MeetingIndexActivity.class);


                intent.putExtra("mi",meetings.get(i).getMeetingIndex());
                intent.putExtra("mid",meetings.get(i).getId());
                intent.putExtra("mtype",meetings.get(i).getType());
                intent.putExtra("atd",meetings.get(i).getAttended());
                System.out.println("Sendt ID : "+meetings.get(i).getId());
                intent.putExtra("mt", meetings.get(i).getTitle());
                intent.putExtra("farmerId", meetings.get(i).getFarmer());
                startActivity(intent);

            }
        });

        super.setDetails(dbHelper,"Farmer","Farmer Meeting Input");
    }



}