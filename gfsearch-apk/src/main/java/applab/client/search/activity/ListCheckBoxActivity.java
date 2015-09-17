package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;

import java.util.List;

/**
 * Created by skwakwa on 8/30/15.
 */
public class ListCheckBoxActivity extends FragmentActivity {
    private ListView list;

    String title;
    int index;
    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Mark Attendance");

        helper = new DatabaseHelper(getBaseContext());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.lst_attendance);


        TextView v =(TextView) findViewById(R.id.txt_meeting_attendance_header);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = (String) extras.get("title");
            index = (Integer) extras.get("index");


        }
        v.setText(title);
        final List<Farmer> farmers = helper.getFarmers();

        ListCheckboxAdapter adapter = new ListCheckboxAdapter(ListCheckBoxActivity.this, farmers);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Selected "+farmers.get(i).getFullname());
            }
        });


    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"DatePicker");
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void markAttendanceSelect(View view){

        Toast.makeText(getBaseContext(),"Taken Attendance",Toast.LENGTH_LONG).show();
    }
}