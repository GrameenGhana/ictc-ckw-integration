package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;

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
        setContentView(R.layout.indivvidual_meeting_activity);


        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Mark Attendance");


        helper = new DatabaseHelper(getBaseContext());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = (String) extras.get("title");
            meetingIndex = (Integer) extras.get("index");
             farm= (Farmer) extras.get("farmer");
        }

        TextView v =(TextView) findViewById(R.id.meetingName);
        v.setText(title);

        v =(TextView) findViewById(R.id.farmerName);
        v.setText(farm.getFullname());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
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


        String attendended =" '"+farm.getFarmID()+"'";

        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex),attendended,"individual",1);
        Toast.makeText(getBaseContext(),"Taken Attendance",Toast.LENGTH_LONG).show();
    }
}