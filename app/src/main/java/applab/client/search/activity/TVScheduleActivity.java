package applab.client.search.activity;

import android.Manifest;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.adapters.TvScheduleAdaptor;
import applab.client.search.model.wrapper.CommunicationSchedule;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

import java.util.*;

/**
 * Created by skwakwa on 9/17/15.
 */
public class TVScheduleActivity extends BaseActivity {
    ExpandableListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_schedule_activity);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("TV Schedule");
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("TV Schedule");
        ;
        //mActionBar.setCustomView(mCustomView);
        //mActionBar.setDisplayShowCustomEnabled(true);
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);

        list = (ExpandableListView) findViewById(R.id.exp_comm_sch);
        list.setGroupIndicator(null);
        List<CommunicationSchedule> cons = new ArrayList<CommunicationSchedule>();
        // CommunicationSchedule(int icon,String title,String nextDate,String time,String language)
        cons.add(new CommunicationSchedule(R.mipmap.ic_tv, "GTV", IctcCKwUtil.getNextDate(Calendar.SUNDAY), "3:00 - 4:00", "English"));
        cons.add(new CommunicationSchedule(R.mipmap.ic_radio, "Volta Star Radio", IctcCKwUtil.getNextDate(Calendar.SATURDAY), "7:00 - 8:00", "Twi"));
        cons.add(new CommunicationSchedule(R.mipmap.ic_radio, "Brong Ahafo Radio", IctcCKwUtil.getNextDate(Calendar.SATURDAY), "7:00 - 8:00", "Ewe & Twi"));


        List<CommunicationSchedule> gtv = new ArrayList<CommunicationSchedule>();
        gtv.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Introduction", IctcCKwUtil.getNextDate(Calendar.SUNDAY), "3:00 - 4:00", "English"));
        gtv.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Title I", IctcCKwUtil.getNextDate(Calendar.SUNDAY, 1), "7:00 - 8:00", "English"));
        gtv.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "title 2", IctcCKwUtil.getNextDate(Calendar.SUNDAY, 2), "7:00 - 8:00", "English"));

        List<CommunicationSchedule> vstar = new ArrayList<CommunicationSchedule>();
        vstar.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Introduction", IctcCKwUtil.getNextDate(Calendar.SATURDAY), "3:00 - 4:00", "English"));
        vstar.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Title I", IctcCKwUtil.getNextDate(Calendar.SATURDAY, 1), "7:00 - 8:00", "English"));
        vstar.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "title 2", IctcCKwUtil.getNextDate(Calendar.SATURDAY, 2), "7:00 - 8:00", "English"));

        List<CommunicationSchedule> rBa = new ArrayList<CommunicationSchedule>();

        rBa.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Introduction", IctcCKwUtil.getNextDate(Calendar.SATURDAY), "3:00 - 4:00", "English"));
        rBa.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "Title I", IctcCKwUtil.getNextDate(Calendar.SATURDAY, 1), "7:00 - 8:00", "English"));
        rBa.add(new CommunicationSchedule(R.mipmap.mobile_app_icon, "title 2", IctcCKwUtil.getNextDate(Calendar.SATURDAY, 2), "7:00 - 8:00", "English"));

        String gtvs = IctcCKwUtil.getNextDate(Calendar.SUNDAY);
        String radio = IctcCKwUtil.getNextDate(Calendar.SATURDAY);

        Map<String, List<CommunicationSchedule>> commSchedules = new HashMap<String, List<CommunicationSchedule>>();
        commSchedules.put("0", gtv);
        commSchedules.put("1", vstar);
        commSchedules.put("2", rBa);


        TvScheduleAdaptor adapter = new TvScheduleAdaptor(TVScheduleActivity.this, cons, commSchedules, getResources().getStringArray(R.array.text_colors), list);
        list.setAdapter(adapter);


        TextView tv = (TextView) findViewById(R.id.txt_call_meida_lines);
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Calling  : 057665186");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057665186"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                System.out.println("Done calling 057665186");
                startActivity(intent);
            }
        });
//
        super.setDetails(new DatabaseHelper(getBaseContext()), "Supplier", "Supplier");


    }

    public void callNo(View view) {

        System.out.println("Calling  : 057665186");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057665186"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
        System.out.println("Done calling 057665186");
    }
}