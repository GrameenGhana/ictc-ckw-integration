package applab.client.search.activity;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by skwakwa on 8/30/15.
 */
public class AttendanceMarkerActivity extends BaseFragmentActivity {
    private ListView list;

    String title;
    int meetingIndex;
    DatabaseHelper helper;
    ListCheckboxAdapter adapter = null;
     List<Farmer> farmerList = null;
    private CheckBox cb;

    String attendanceType="";
    String attendanceTitle="";
    long stime;
    DatePickerFragment datePicker = null;
    TimePickerFragment timePicker = null;
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


        stime = System.currentTimeMillis();

        super.setDetails(helper,"Meeting","Mark Group Attendance");

        TextView v =(TextView) findViewById(R.id.txt_meeting_attendance_header);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = (String) extras.get("title");
            meetingIndex = (Integer) extras.get("index");

            attendanceType = (String) extras.get("attendanceType");

            attendanceTitle = (String) extras.get("attTitle");

        }

        JSONObject json = new JSONObject();
        try {

            json.put("meetingidx",meetingIndex);
            json.put("title",attendanceTitle);
            json.put("attendanceType",attendanceType);

        }catch (Exception  e){

        }
        super.setDetails(helper,"Meeting",title,"Mark Group Attendance ",json.toString());
        v.setText(title+" >> "+attendanceTitle);
        final List<Farmer> farmers = helper.getFarmers();

        farmerList = farmers;
      adapter = new ListCheckboxAdapter(AttendanceMarkerActivity.this, farmers,getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cb=(CheckBox)list.getChildAt(i).findViewById(R.id.lst_chk);
                    cb.setChecked(!cb.isChecked());
                Toast.makeText(getBaseContext(),
                                "Clicked on Checkbox: " + farmers.get(i).getFullname() +
                                        " is " ,
                                Toast.LENGTH_LONG).show();
                System.out.println("Selected " + farmers.get(i).getFullname());
            }
        });


    }

    public void showDatePickerDialog(View v) {
        datePicker =  new DatePickerFragment();
        datePicker.show(getFragmentManager(),"DatePicker");

        Toast.makeText(getBaseContext(),datePicker.getDateSet(),Toast.LENGTH_LONG).show();
    }



    public void showTimePickerDialog(View v) {
        timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void markAttendanceSelect(View view){

        boolean [] selected = adapter.getSelectedIndex();
        String attenden =" ";
        String notInAttendance=" ";
        for(int i=0;i<farmerList.size();i++){
            Farmer f = farmerList.get(i);
            if(selected[i])
                attenden+="'"+f.getFarmID()+"',";
            else
                notInAttendance+="'"+f.getFarmID()+"',";
        }

        System.out.println();


        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex),attenden.substring(0,attenden.length()-1),1);
        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex),notInAttendance.substring(0,attenden.length()-1),0);


        JSONObject objs = new JSONObject();

        try {


            objs.put("meeting_index",meetingIndex);
            objs.put("title",title);
            objs.put("page","Group Attendance Marked");
            objs.put("type","Group");
            objs.put("section",title);

            objs.put("date",datePicker.getDateSet());
            objs.put("time",datePicker.getDateSet());


            objs.put("attendanceType",attendanceType);
            objs.put("attendanceTitle",attendanceTitle);
            objs.put("attendees",attenden);
            objs.put("absentees",notInAttendance);
            objs.put("imei", IctcCKwUtil.getImei(getBaseContext()));
            objs.put("version",IctcCKwUtil.getAppVersion());
            objs.put("battery",IctcCKwUtil.getBatteryLevel(getBaseContext()));
            helper.insertCCHLog("Meeting",objs.toString(),stime, System.currentTimeMillis());

        }catch(Exception e ){

        }
        Toast.makeText(getBaseContext(),"Taken Attendance",Toast.LENGTH_LONG).show();
    }
}