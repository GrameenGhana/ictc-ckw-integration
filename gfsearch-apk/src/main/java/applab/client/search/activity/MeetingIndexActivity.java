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
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;

import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingIndexActivity extends Activity {
    private ListView list;

    DatabaseHelper helper;
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
        String titler = "";
        if (extras != null) {
            meetingIndex = (Integer) extras.get("mi");
            titler = (String) extras.get("mt");

        }

        TextView title = (TextView) findViewById(R.id.act_4_meeting);
        title.setText(titler+" #"+meetingIndex);

        final List<applab.client.search.model.MeetingActivity> meetings = AgentVisitUtil.getMeetingActivity(meetingIndex);
        final String []   titles = new String[meetings.size()];
        final String []   icons = new String[meetings.size()];
        System.out.println("Meeting Sixe : "+meetings.size());
        int cnt=0;
        for(MeetingActivity act : meetings){
            titles[cnt] = act.getActivityName();
            icons[cnt] = act.getApplicationToHandle();
            cnt++;
        }


        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(MeetingIndexActivity.this, titles,icons,getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    showDialog(meetings.get(i).getActivityName(), meetings.get(i).getDescription(), meetings.get(i).getApplicationToHandle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void showDialog(final String title,final String  msg, final String type) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MeetingIndexActivity.this);

        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equalsIgnoreCase("C")) {


                    Intent intent = new Intent(MeetingIndexActivity.this, MainActivity.class);
                    startActivity(intent);

                }else if(type.equalsIgnoreCase("T")){       Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                            startActivity(launchIntent);
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
}