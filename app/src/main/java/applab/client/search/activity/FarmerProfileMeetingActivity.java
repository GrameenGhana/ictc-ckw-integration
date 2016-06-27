package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import applab.client.search.R;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.adapters.UnitsListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.model.Meeting;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

public class FarmerProfileMeetingActivity extends BaseActivity {

    private ListView list_meet;
    private Farmer farmer;
    private String name;
    private String mainCrop;
    private String location;
    List<FarmerInputs> myInputs =new ArrayList<FarmerInputs>();
    List<Meeting> meetings = new ArrayList<Meeting>();
    private ListView listView;
    DatabaseHelper dbHelper = null;
    private ExpandableListView exp_meetings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile_meeting);
       listView = (ListView)findViewById(R.id.lst_inputs);
        dbHelper = new DatabaseHelper(FarmerProfileMeetingActivity.this);
       // exp_meetings=(ExpandableListView) findViewById(R.id.meetings);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");

                location = farmer.getCommunity();
                name = farmer.getLastName() + " , " + farmer.getFirstName();
            }
            mainCrop = farmer.getMainCrop();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            setValuesForFields();
            setMeetingList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setValuesForFields(){

        /**
         *  FarmerInputReceivedWrapper seedsReceived = searchNeeds(farmers, "seeds");
         FarmerInputReceivedWrapper fertReceived = searchNeeds(farmers, "fertiliser");
         FarmerInputReceivedWrapper ploughReceived = searchNeeds(farmers, "plough");
         */
        myInputs=dbHelper.getIndividualFarmerInputs(farmer.getFarmID());
        String  [] details= new String[myInputs.size()];
        String [] firstLetters= new String[myInputs.size()];
        String [] units= new String[myInputs.size()];
        boolean  [] enabled = new boolean[myInputs.size()];
        Arrays.fill(enabled, true);

        int cnt=0;
        for(FarmerInputs fi : myInputs){
            String title="";
            String xt="";String act=String.valueOf(fi.getQty());
            if(fi.getQty()<1.0){
                xt=" Not Given ";
                act="NA";
            }else{
                act= IctcCKwUtil.formatDoubleNoDecimal(fi.getQty());
            }
            if(fi.getName().equalsIgnoreCase("plough")){
                units[cnt]="";
            }else{
                units[cnt]="bags";
            }
            firstLetters[cnt]=act;

            details[cnt]= fi.getName().toUpperCase()+xt;

            cnt++;
        }
        ListAdapter adapter = new UnitsListAdapter(FarmerProfileMeetingActivity.this, details,firstLetters ,units,enabled,getResources().getStringArray(R.array.text_colors));
        listView.setAdapter(adapter);

    }
    public void setMeetingList(){
        list_meet = (ListView) findViewById(R.id.list_ind_meet);
        meetings =  dbHelper.getFarmerMeetings(farmer.getFarmID());
        meetings = IctcCKwUtil.sortMeeting(meetings);

        // System.out.println("Meeting Indexx : "+meetings.size());
        // System.out.println("Ktd : "+list_meet.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(FarmerProfileMeetingActivity.this, meetings, getResources().getStringArray(R.array.text_colors));
        list_meet.setAdapter(adapter);

        list_meet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmerProfileMeetingActivity.this,MeetingIndexActivity.class);
                intent.putExtra("mi",meetings.get(i).getMeetingPosition());
                intent.putExtra("mid",meetings.get(i).getId());
                intent.putExtra("mtype",meetings.get(i).getType());
                intent.putExtra("atd",meetings.get(i).getAttended());
                System.out.println("Sendt ID : "+meetings.get(i).getId());
                intent.putExtra("mt", meetings.get(i).getTitle());
                intent.putExtra("farmerId", meetings.get(i).getFarmer());
                startActivity(intent);

            }
        });
    }

}
