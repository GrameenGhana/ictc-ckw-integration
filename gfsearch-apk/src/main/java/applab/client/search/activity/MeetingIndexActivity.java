package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.MeetingActivityAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;
import sun.management.Agent;

import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingIndexActivity extends Activity {
    private ListView list;

    int index;
    String meetingTitle;
    DatabaseHelper helper;
    String titler = "";
    String farmerId="";
    Farmer farmer=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_index_activity);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Meeting Activity");


        helper = new DatabaseHelper(getBaseContext());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.lst_meeting_index);
        Bundle extras = getIntent().getExtras();
        int meetingIndex = 0;

        if (extras != null) {
            meetingIndex = (Integer) extras.get("mi");
            titler = (String) extras.get("mt");
            farmerId = (String) extras.get("farmerId");
            if(null != farmerId) {
                farmer = helper.findFarmer(farmerId);
                TextView title = (TextView) findViewById(R.id.act_farmer_details);
                title.setText("Farmer >> "+farmer.getFullname());
            }index=meetingIndex;
            meetingTitle=titler;
        }

        TextView title = (TextView) findViewById(R.id.act_4_meeting);
        title.setText(titler+" #"+meetingIndex);

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
    showDialog(meetings.get(i).getActivityName(), meetings.get(i).getDescription(), meetings.get(i).getApplicationToHandle(), index, meetingTitle);
}    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void showDialog(final String title,final String  msg, final String type, final int idx, final String  meetTitle) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MeetingIndexActivity.this);

        builder.setTitle(title);

        builder.setMessage(msg);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equalsIgnoreCase("C")) {


//                    Intent intent = new Intent(MeetingIndexActivity.this, MainActivity.class);
//                    startActivity(intent);

                    Intent intent = new Intent(MeetingIndexActivity.this, FarmerActivitySelectFarmer.class);

                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    intent.putExtra("detail",title);
                    intent.putExtra("farmer",farmer);
//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                        intent.putExtra("type","ckw");
                        startActivity(intent);
//                    }

                }else if(type.equalsIgnoreCase("T")){
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                    startActivity(launchIntent);
                }else if(title.equalsIgnoreCase(AgentVisitUtil.TAKE_ATTENDANCE)){

                    Intent intent = new Intent(MeetingIndexActivity.this, ListCheckBoxActivity.class);
                    System.out.println("Meeting : "+meetTitle);
                    System.out.println("Index : "+idx);
                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    startActivity(intent);

                }else if(title.contains("TV")){

                    Intent intent = new Intent(MeetingIndexActivity.this, TVScheduleActivity.class);
                    System.out.println("Meeting : " + meetTitle);
                    System.out.println("Index : " + idx);
                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    startActivity(intent);

                }
                else if(type.equalsIgnoreCase("A")){
                    Intent intent = new Intent(MeetingIndexActivity.this, FarmerActivitySelectFarmer.class);

                    intent.putExtra("index",idx);
                    intent.putExtra("title",meetTitle);
                    intent.putExtra("detail",title);
                    intent.putExtra("farmer",farmer);
                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
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
}