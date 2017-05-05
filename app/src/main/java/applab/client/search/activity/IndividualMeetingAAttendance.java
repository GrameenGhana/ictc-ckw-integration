package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by skwakwa on 9/23/15.
 */
public class IndividualMeetingAAttendance extends BaseActivity {

    private ListView list;

    String title;
    int meetingIndex;
    DatabaseHelper helper;
    ListCheckboxAdapter adapter = null;
    String meetingType="";
    Farmer farm = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.indivvidual_meeting_activity);


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Mark Attendance");


        /*LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Mark Attendance");*/

        //IctcCKwUtil.setActionbarUserDetails(this,mCustomView);
        helper = new DatabaseHelper(getBaseContext());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = (String) extras.get("title");
            meetingIndex = (Integer) extras.get("index");
             farm= (Farmer) extras.get("farmer");
            meetingType=(String) extras.get("type");
        }

        TextView v =(TextView) findViewById(R.id.meetingName);
        v.setText(title);

       // v =(TextView) findViewById(R.id.farmerName);
       // v.setText(farm.getFullname());

        farm=helper.findFarmer(farm.getFarmID());
        ViewGroup view = (ViewGroup)getWindow().getDecorView();
        IctcCKwUtil.setFarmerDetails(view,R.id.ll_farmer_details,farm.getFullname(),farm,true);
      //  mActionBar.setCustomView(mCustomView);
      //  mActionBar.setDisplayShowCustomEnabled(true);

        String title = "";
        if(null == farm)
            title="";
       // IctcCKwUtil.setFarmerDetails(getWindow().getDecorView().getRootView(),R.id.ll_farmer_details,title,farm,true);
        super.setDetails(helper,"Farmer","Farm Individual Meeting");
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"DatePicker");

        Toast.makeText(getBaseContext(), newFragment.getDateSet(), Toast.LENGTH_LONG).show();
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void markAttendanceSelect(View view){


        String attendended = " '" + farm.getFarmID()+"'";

        MeetingActivity  mt = AgentVisitUtil.getMeetingDetails(meetingIndex,meetingType);

        System.out.println("Attendance  : "+(mt.getMeetingIndex())+"  | "+attendended+" | Individual ");
        helper.markAttendanceByMeetingIndex(String.valueOf(mt.getMeetingIndex()), attendended, "individual", 1);


        Toast.makeText(getBaseContext(),"Taken Attendance",Toast.LENGTH_LONG).show();
    }
}