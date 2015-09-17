package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.utils.IctcCKwUtil;

import java.util.Calendar;

/**
 * Created by skwakwa on 9/17/15.
 */
public class TVScheduleActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_schedule_activity);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("TV Schedule");


        String gtv = IctcCKwUtil.getNextDate(Calendar.SUNDAY);
        String readio = IctcCKwUtil.getNextDate(Calendar.SATURDAY);

        TextView t =(TextView)  findViewById(R.id.txt_gtv_date);
        t.setText(gtv+"\n3:00pm - 3:30pm");

        t =(TextView)  findViewById(R.id.txt_radio_bar);
        t.setText(readio+"\n7:00pm - 8:00pm");
        t =(TextView)  findViewById(R.id.txt_volta_star);
        t.setText(readio+"\n7:00pm - 8:00pm");

    }
}