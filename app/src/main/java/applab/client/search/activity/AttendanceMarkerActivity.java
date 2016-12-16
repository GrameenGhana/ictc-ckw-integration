package applab.client.search.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckBoxRecyclerAdapter;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by skwakwa on 8/30/15.
 */
public class AttendanceMarkerActivity extends BaseActivity {
    private ListView list;

    String title;
    int meetingIndex;
    DatabaseHelper helper;
    ListCheckboxAdapter adapter = null;
    List<Farmer> farmerList = null;
    List<String> farmerNames = null;
    private CheckBox cb;

    String attendanceType = "";
    String attendanceTitle = "";
    long stime;
    DatePickerFragment datePicker = null;
    TimePickerFragment timePicker = null;
    private LinkedHashMap<String, Integer> mapIndex;

    String selectedDate, selected_time, end_time;

    LinearLayoutManager mLayoutManager;
    View mView;
    ListAdapter mAdapter;
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Mark Attendance");


        LayoutInflater mInflater = LayoutInflater.from(this);
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        // mTitleTextView.setText("Mark Attendance");
        helper = new DatabaseHelper(getBaseContext());

        //mActionBar.setCustomView(mCustomView);
        // mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.lst_attendance);


        stime = System.currentTimeMillis();

        super.setDetails(helper, "Meeting", "Mark Group Attendance");

        TextView v = (TextView) findViewById(R.id.txt_meeting_attendance_header);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = (String) extras.get("title");
            meetingIndex = (Integer) extras.get("index");

            attendanceType = (String) extras.get("attendanceType");

            attendanceTitle = (String) extras.get("attTitle");

        }

        JSONObject json = new JSONObject();
        try {

            json.put("meetingidx", meetingIndex);
            json.put("title", attendanceTitle);
            json.put("attendanceType", attendanceType);

        } catch (Exception e) {

        }


        super.setDetails(helper, "Meeting", title, "Mark Group Attendance ", json.toString());
        v.setText(title + " >> " + attendanceTitle);

        final List<Farmer> farmers = helper.getFarmers();
        farmerNames = new ArrayList<>();

        farmerList = farmers;

        adapter = new ListCheckboxAdapter(AttendanceMarkerActivity.this, farmers, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cb = (CheckBox) list.getChildAt(i).findViewById(R.id.lst_chk);




                if(cb.isChecked()){
                    //cb.setChecked(false);

                    farmerNames.remove(farmers.get(i).getFullname());

                    Toast.makeText(getBaseContext(), farmers.get(i).getFullname() +
                                    " is removed",
                            Toast.LENGTH_LONG).show();

                }
                else if (!cb.isChecked()){
                    //cb.setChecked(true);

                    farmerNames.add(farmers.get(i).getFullname().toString());
                    Toast.makeText(getBaseContext(), farmers.get(i).getFullname() +
                                    " is selected",
                            Toast.LENGTH_LONG).show();
                }

                cb.setChecked(!cb.isChecked());



               // Toast.makeText(getBaseContext(), "Clicked on Checkbox: " + farmers.get(i).getFullname() + " is ", Toast.LENGTH_LONG).show();

                System.out.println("Selected " + farmers.get(i).getFullname());
            }
        });


    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            selectedDate = String.valueOf(selectedYear) + "/" + String.valueOf(selectedMonth) + "/" + String.valueOf(selectedDay);

            Toast toast = Toast.makeText(getBaseContext(), selectedDate, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            selected_time = String.valueOf(i) + ":" + String.valueOf(i1);

            Toast toast = Toast.makeText(getBaseContext(), selected_time, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        }


    };

    private TimePickerDialog.OnTimeSetListener timePickerListener2
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            end_time = String.valueOf(i) + ":" + String.valueOf(i1);
            Toast toast = Toast.makeText(getBaseContext(), end_time, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    };


    public void showDatePickerDialog(View v) {
       /* datePicker =  new DatePickerFragment();
        datePicker.show(getFragmentManager(),"DatePicker");

        selectedDate = datePicker.getDateSet();
        Toast.makeText(getBaseContext(), selectedDate,Toast.LENGTH_LONG).show();*/


        DatePickerDialog date_picker_dialog = new DatePickerDialog(this, datePickerListener, 2017, 01, 1);
        date_picker_dialog.show();


    }


    public void showTimePickerDialog(View v) {
        if (v.getId() == R.id.sit) {

            TimePickerDialog time_picker_dialog = new TimePickerDialog(this, timePickerListener, 12, 0, true);
            time_picker_dialog.show();

        } else if (v.getId() == R.id.eit) {


            TimePickerDialog time_picker_dialog = new TimePickerDialog(this, timePickerListener2, 12, 0, true);
            time_picker_dialog.show();


        }

        /*timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");*/


    }


    public void markAttendanceSelect(View view) {

        boolean[] selected = adapter.getSelectedIndex();
        String attenden = " ";
        String notInAttendance = " ";
        for (int i = 0; i < farmerList.size(); i++) {
            Farmer f = farmerList.get(i);
            if (selected[i])
                attenden += "'" + f.getFarmID() + "',";
            else
                notInAttendance += "'" + f.getFarmID() + "',";
        }

        System.out.println();


        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex), attenden.substring(0, attenden.length() - 1), 1);
        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex), notInAttendance.substring(0, attenden.length() - 1), 0);


        JSONObject objs = new JSONObject();

        try {


            objs.put("meeting_index", meetingIndex);
            objs.put("title", title);
            objs.put("page", "Group Attendance Marked");
            objs.put("type", "Group");
            objs.put("section", title);

            objs.put("date", selectedDate);
            objs.put("time", selected_time);


            objs.put("attendanceType", attendanceType);
            objs.put("attendanceTitle", attendanceTitle);
            objs.put("attendees", attenden);
            objs.put("absentees", notInAttendance);
            objs.put("imei", IctcCKwUtil.getImei(getBaseContext()));
            objs.put("version", IctcCKwUtil.getAppVersion());
            objs.put("battery", IctcCKwUtil.getBatteryLevel(getBaseContext()));
            helper.insertCCHLog("Meeting", objs.toString(), stime, System.currentTimeMillis());

        } catch (Exception e) {

        }
        Toast.makeText(getBaseContext(), "Attendance marked", Toast.LENGTH_LONG).show();
    }

    public void markAttendanceSelect() {

        boolean[] selected = adapter.getSelectedIndex();
        String attenden = " ";
        String notInAttendance = " ";
        for (int i = 0; i < farmerList.size(); i++) {
            Farmer f = farmerList.get(i);
            if (selected[i])
                attenden += "'" + f.getFarmID() + "',";
            else
                notInAttendance += "'" + f.getFarmID() + "',";
        }

        System.out.println();


        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex), attenden.substring(0, attenden.length() - 1), 1);
        helper.markAttendanceByMeetingIndex(String.valueOf(meetingIndex), notInAttendance.substring(0, attenden.length() - 1), 0);


        JSONObject objs = new JSONObject();

        try {


            objs.put("meeting_index", meetingIndex);
            objs.put("title", title);
            objs.put("page", "Group Attendance Marked");
            objs.put("type", "Group");
            objs.put("section", title);

            objs.put("date", selectedDate);
            objs.put("time", selected_time);

            objs.put("attendanceType", attendanceType);
            objs.put("attendanceTitle", attendanceTitle);
            objs.put("attendees", attenden);
            objs.put("absentees", notInAttendance);
            objs.put("imei", IctcCKwUtil.getImei(getBaseContext()));
            objs.put("version", IctcCKwUtil.getAppVersion());
            objs.put("battery", IctcCKwUtil.getBatteryLevel(getBaseContext()));
            helper.insertCCHLog("Meeting", objs.toString(), stime, System.currentTimeMillis());

        } catch (Exception e) {

        }
        Toast toast = Toast.makeText(getBaseContext(), "Attendance marked", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public void showSummary(View v) {

        if (farmerNames != null & farmerNames.size() != 0) {

            if(selected_time != null & selectedDate != null & selectedDate != "" & selected_time != ""){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, farmerNames);


            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            mView = layoutInflaterAndroid.inflate(R.layout.custom_dialog_attendance_summary, null, false);

            TextView meetingDate = (TextView) mView.findViewById(R.id.meeting_date);
            TextView meetingStartTime = (TextView) mView.findViewById(R.id.meeting_time);
            TextView meetingEndTime = (TextView) mView.findViewById(R.id.end_time);


            meetingDate.setText(selectedDate);
            meetingStartTime.setText(selected_time);
            meetingEndTime.setText(end_time);


            listView = (ListView) mView.findViewById(R.id.listview);
            listView.setAdapter(adapter);


            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView)
                    .setTitle("Summary")
                    .setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            markAttendanceSelect();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });

            AlertDialog dialog = alertDialogBuilderUserInput.create();
            dialog.show();


        } else{

                Toast toast = Toast.makeText(getBaseContext(), "Please select date and time", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


        }



        else {

            Toast toast = Toast.makeText(getBaseContext(), "Please select farmers who attended", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        }
    }


}