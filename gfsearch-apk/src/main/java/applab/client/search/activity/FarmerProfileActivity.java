package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.Html;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.adapters.ProfileViewAdapter;
import applab.client.search.adapters.UnitsListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.model.Meeting;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;
import applab.client.search.utils.IctcCKwUtil;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 10/20/15.
 */
public class FarmerProfileActivity extends BaseActivityGroup {
    DatabaseHelper dbHelper = null;
    Farmer farmer= null;


    private ExpandableListView list;
    private LinearLayout linearlayout_crop;
    private ListView list_meet;
    List<FarmerInputs> myInputs =new ArrayList<FarmerInputs>();
    List<Meeting> meetings = new ArrayList<Meeting>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(getBaseContext());
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Farmer Profile");
        setContentView(R.layout.activity_farmer_summary_profile);
        linearlayout_crop=(LinearLayout) findViewById(R.id.linearlayout_crops);

        super.setDetails(dbHelper,"Farmer","Farmer Profile");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String farmerId = (String) extras.get("farmerId");

            if(null == farmerId ||farmerId.isEmpty()) {
                farmer = (Farmer) extras.get("farmer");
                farmerId = farmer.getFarmID();
            }
            farmer = dbHelper.findFarmer(farmerId);
        }


       TabHost tabHost = (TabHost) findViewById(R.id.tabHost5);

        LocalActivityManager mLocalActivityManager = new LocalActivityManager(FarmerProfileActivity.this,false);
        mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
        tabHost.setup(mLocalActivityManager);


        final List<String> sections = new ArrayList<String>();
        sections.add("Farmer Summary");
        sections.add("Previous Performance");
        sections.add("Technical Needs");

        int cnt=0;
        for(String section:sections) {
            FragmentTabHost.TabSpec spec = tabHost.newTabSpec(section.trim());
            spec.setIndicator(section);
            if(section.equalsIgnoreCase("Previous Performance")){
                spec.setContent(new Intent(FarmerProfileActivity.this, FarmerBaselineSummaryActivity.class)
                        .putExtra("farmer", farmer)
                        .putExtra("section", section));
            }else {
                spec.setContent(new Intent(FarmerProfileActivity.this, FarmerProfileSecionActivity.class)
                        .putExtra("farmer", farmer)
                        .putExtra("section", section));
            }
            tabHost.addTab(spec);
            TextView tv =(TextView) tabHost.getTabWidget().getChildAt(cnt).findViewById(android.R.id.title);

        }


//        list = (ExpandableListView) findViewById(R.id.exp_summary_profile);
//
//        list.setGroupIndicator(null);
//        final List<String> sections = new ArrayList<String>();
//        sections.add("Farmer Summary");
//        Map<String,List<ItemWrapper>> wr = FarmerUtil.getFarmerSummaryDetails(farmer);
//
//        ProfileViewAdapter adapter = new ProfileViewAdapter(FarmerProfileActivity.this, sections, wr, list,true);
//        list.setAdapter(adapter);





//        TextView t= (TextView) findViewById(R.id.txt_nname);
//        t.setText(farmer.getNickname());
//        t= (TextView) findViewById(R.id.txt_cluster);
//        t.setText(farmer.getCluster());
//        t= (TextView) findViewById(R.id.txt_mcrop);
//        t.setText(farmer.getMainCrop());
//        t= (TextView) findViewById(R.id.txt_aucultivation);
//        t.setText(Html.fromHtml(farmer.getLandArea()+"m<sup>2</sup> Perimeter : "+farmer.getSizePlot()+" m "));
//        t= (TextView) findViewById(R.id.txt_village);
//        t.setText(farmer.getVillage());
//
//
//
//        t= (TextView) findViewById(R.id.txt_last_season_yeild_acre);
//        t.setText("");
//        t= (TextView) findViewById(R.id.txt_this_sea_ypa);
//        t.setText("");
//        t= (TextView) findViewById(R.id.txt_this_seppt);
//        t.setText("");
//        t= (TextView) findViewById(R.id.txt_aucultivation);
//        t.setText(Html.fromHtml(farmer.getLandArea()+" m<sup>2</sup> Perimeter : "+farmer.getSizePlot()+" m "));
//        t= (TextView) findViewById(R.id.txt_village);
//        t.setText(farmer.getVillage());

        setValuesForFields();
        setMeetingList();

    }
    public void setValuesForFields(){

        /**
         *  FarmerInputReceivedWrapper seedsReceived = searchNeeds(farmers, "seeds");
         FarmerInputReceivedWrapper fertReceived = searchNeeds(farmers, "fertiliser");
         FarmerInputReceivedWrapper ploughReceived = searchNeeds(farmers, "plough");
         */
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
        ListView  listView = (ListView)findViewById(R.id.lst_inputs);

        ListAdapter adapter = new UnitsListAdapter(FarmerProfileActivity.this, details,firstLetters ,units,enabled,getResources().getStringArray(R.array.text_colors));
        listView.setAdapter(adapter);


    }
    public void setMeetingList(){
        list_meet = (ListView) findViewById(R.id.list_ind_meet);
        meetings =  dbHelper.getFarmerMeetings(farmer.getFarmID());
        meetings = IctcCKwUtil.sortMeeting(meetings);

       // System.out.println("Meeting Indexx : "+meetings.size());
       // System.out.println("Ktd : "+list_meet.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(FarmerProfileActivity.this, meetings, getResources().getStringArray(R.array.text_colors));
        list_meet.setAdapter(adapter);

        list_meet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmerProfileActivity.this,MeetingIndexActivity.class);
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

    public void viewFullProfile(View view){

        Intent t= new Intent(this, FarmerDetailedProfile.class);
        t.putExtra("farmer",farmer);
        startActivity(t);
    }

}