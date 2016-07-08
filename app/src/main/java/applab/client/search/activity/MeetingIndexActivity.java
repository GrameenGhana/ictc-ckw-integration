package applab.client.search.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;
import applab.client.search.utils.IctcCKwUtil;


import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingIndexActivity extends BaseActivity {
    private ListView list;

    int index;
    String meetingTitle;
    DatabaseHelper helper;
    String titler = "";
    String farmerId="";
    Farmer farmer=null; String mtype ="";
    boolean isFarmerSelected=false;
    String justBrowse="N";
    String xtraTitle="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_index_activity);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Meeting Activity");
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Meeting Activity");

        IctcCKwUtil.setActionbarUserDetails(this,mCustomView);
        LinearLayout ll = (LinearLayout)findViewById(R.id.default_view_profile_item);
        isFarmerSelected= true;
        helper = new DatabaseHelper(getBaseContext());
        list = (ListView) findViewById(R.id.lst_meeting_index);
        Bundle extras = getIntent().getExtras();
        int meetingIndex = 0;
        Button b = (Button) findViewById(R.id.btn_nxt_mark_attendance);
        b.setVisibility(Button.GONE);
        if (extras != null) {
            meetingIndex = (Integer) extras.get("mi");
            titler = (String) extras.get("mt");
            justBrowse = (String) extras.get("jb");
            if(null == justBrowse || justBrowse.isEmpty())
                justBrowse="N";
            farmerId = (String) extras.get("farmerId");
            String meetId = (String) extras.get("mid");
             mtype = (String) extras.get("mtype");
            int atd = (Integer) extras.get("atd");
            if(null != farmerId && farmerId.length()>0 && titler.toLowerCase().contains("visit")) {
                farmer = helper.findFarmer(farmerId);
                isFarmerSelected=true;
                String part = (atd==1)?" [Already Attended]":"";
                TextView title = (TextView) findViewById(R.id.act_farmer_details);
                title.setText("Farmer >> "+farmer.getFullname() +part);
                title.setVisibility(View.GONE);
                System.out.println("Meeting Id : " + meetingIndex);
                System.out.println("mtyppe Id : "+mtype);
                MeetingActivity meetingActivity = AgentVisitUtil.getMeetingDetails(meetingIndex,mtype);
                Meeting meeting  = helper.getFarmerMeetings(farmer.getFarmID(), meetingActivity.getMeetingIndex());
//if(null!=meeting){
                System.out.println("meeting not not null");
                ViewGroup view = (ViewGroup)getWindow().getDecorView();
                IctcCKwUtil.setFarmerDetails(view,R.id.default_view_profile_item,farmer.getFullname(),farmer,true);
//                if(mtype.toLowerCase().contains("ind")){
                System.out.println("Meeting Found : "+meeting);
                if(null!=meeting){
                    System.out.println("Meeting Details : "+meeting.getAttended());
                }
                if(null != meeting && meeting.getAttended()==1){
                    b.setVisibility(Button.GONE);
                    xtraTitle=" {{Already Attended}}";
                }else
                    b.setVisibility(Button.VISIBLE);
//                }
            }else{
                ll.setVisibility(View.GONE);

            }
            index=meetingIndex;
            meetingTitle=titler;
        }

        super.setDetails(new DatabaseHelper(getBaseContext()),"Meeting","Meeting Index");
        TextView title = (TextView) findViewById(R.id.act_4_meeting);
       // title.setText(AgentVisitUtil.getMeetingTitle(meetingIndex,mtype)+" "+xtraTitle);
        title.setText(titler+ " "+xtraTitle);

        final List<applab.client.search.model.MeetingActivity> meetings = AgentVisitUtil.getMeetingActivity(meetingIndex);
        final String []   titles = new String[meetings.size()];
        final String []   icons = new String[meetings.size()];

        final boolean [] enabled = new boolean[meetings.size()];
        System.out.println("Meeting Sixe : "+meetings.size());
        int cnt=0;
        for(MeetingActivity act : meetings){
            titles[cnt] = act.getActivityName();
            icons[cnt] = act.getApplicationToHandle();
            enabled[cnt] = act.isCurrentlyAvailable();
            cnt++;
        }
        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(MeetingIndexActivity.this, titles,icons,enabled,getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
if(meetings.get(i).isCurrentlyAvailable()) {
    showDialog(meetings.get(i).getActivityName(), meetings.get(i).getDescription(), meetings.get(i).getApplicationToHandle(), index, meetingTitle,titler,isFarmerSelected);
}    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void showDialog(final String title,final String  msg, final String type, final int idx, final String  meetTitle,final String meetingType,boolean selected) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MeetingIndexActivity.this);
        System.out.println("Meet type : "+meetingType);

        builder.setTitle(title);

        if(selected){
            if(null!=farmer)
            msg.replace("Select Farmer",farmer.getFullname()+" Already Selected");
        }

        builder.setMessage(msg);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equalsIgnoreCase("C")) {


//                    Intent intent = new Intent(MeetingIndexActivity.this, CKWSearchActivity.class);
//                    startActivity(intent);

                    if(meetingType.toLowerCase().contains("multi")){
                        Intent intent = new Intent(MeetingIndexActivity.this, CropSelectorActivity.class);

                        intent.putExtra("index",idx);
                        intent.putExtra("title",meetTitle);
                        intent.putExtra("detail",title);
                        intent.putExtra("farmer",farmer);
//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                        intent.putExtra("type","ckw");
                        startActivity(intent);
                    }else{


                        if(justBrowse.equalsIgnoreCase("Y")) {
                            Intent intent= new Intent(MeetingIndexActivity.this, CKWSearchActivity.class);
                            intent.putExtra("farmer","");
                            intent.putExtra("SEARCH_CROP","");
                            intent.putExtra("SEARCH_TITLE","");
                            startActivity(intent);

                        }else{
                            Intent intent = new Intent(MeetingIndexActivity.this, FarmerActivitySelectFarmer.class);
                            intent.putExtra("index", idx);
                            intent.putExtra("title", meetTitle);
                            intent.putExtra("detail", title);
                            intent.putExtra("farmer", farmer);
//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                            intent.putExtra("type", "ckw");
                            startActivity(intent);
                        }
                    }

//                    }

                }else if(type.equalsIgnoreCase("T")){
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                    startActivity(launchIntent);
                }else if(title.contains(AgentVisitUtil.TAKE_ATTENDANCE)){

                    String attendanceType="Initial";
                    if(title.contains("Final")){
                        attendanceType="Final";
                    }
                    Intent intent = new Intent(MeetingIndexActivity.this, AttendanceMarkerActivity.class);
                    System.out.println("Meeting : "+meetTitle);
                    System.out.println("Index : "+idx);
                    intent.putExtra("index", idx);
                    intent.putExtra("title",meetTitle);
                    intent.putExtra("attTitle",title);//attendance title
                    intent.putExtra("attendanceType",attendanceType);
                    startActivity(intent);

                }else if(title.contains("TV")){

                    Intent intent = new Intent(MeetingIndexActivity.this, TVScheduleActivity.class);
                    System.out.println("Meeting : " + meetTitle);
                    System.out.println("Index : " + idx);
                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    startActivity(intent);

                }else if(title.equalsIgnoreCase(AgentVisitUtil.AGREED_ACTIVITIES_FOR_NEXT_MEETING)){
                    if(meetingType.toLowerCase().contains("group") || justBrowse.equalsIgnoreCase("Y")){
                        Intent intent = new Intent(MeetingIndexActivity.this, CropSelectorActivity.class);

                        intent.putExtra("index",idx);
                        intent.putExtra("title",meetTitle);
                        intent.putExtra("detail",title);
                        intent.putExtra("farmer",farmer);
                        if( justBrowse.equalsIgnoreCase("Y")){
                            intent.putExtra("typeItem","Individual");
                        }
//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                        intent.putExtra("type","nma");
                        startActivity(intent);
                    }else{

                        Intent intent = new Intent(MeetingIndexActivity.this, FarmerActivitySelectFarmer.class);

                        intent.putExtra("index",idx);
                        intent.putExtra("title",meetTitle);
                        intent.putExtra("detail",title);
                        intent.putExtra("farmer",farmer);
//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                        intent.putExtra("type","nma");
                        startActivity(intent);
                    }
                }
                else if(type.equalsIgnoreCase("A")){
                    Intent intent = new Intent(MeetingIndexActivity.this, FarmerActivitySelectFarmer.class);
                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    intent.putExtra("detail",title);
                    intent.putExtra("farmer",farmer);
                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEIASUREMENT)){
                        intent.putExtra("type","farm-map");
                        startActivity(intent);
                    }

                    if(title.equalsIgnoreCase(AgentVisitUtil.SHARE_INPUT_PACKAGE) || title.equalsIgnoreCase(AgentVisitUtil.DELIVER_INPUTS)){
                        intent.putExtra("type","farm-input");
                        startActivity(intent);
                    }
                }



                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    public Intent processOnClickRequest(String type){
        if(type.equalsIgnoreCase("farm-map")){
            Intent intent= new Intent(MeetingIndexActivity.this, FarmMapping.class);
            intent.putExtra("farmer",farmer);
           return intent;
        }else if(type.equalsIgnoreCase("farm-input")){

            Intent intent= new Intent(MeetingIndexActivity.this, FarmerInputActivty.class);
            intent.putExtra("farmer",farmer);

            startActivity(intent);
        }
        return null;
    }

    public void processMakeAttendance(View view){

        Intent intent= new Intent(MeetingIndexActivity.this, IndividualMeetingAAttendance.class);
        intent.putExtra("farmer",farmer);
        intent.putExtra("title",meetingTitle);
        intent.putExtra("index",index);
        startActivity(intent);
    }
}