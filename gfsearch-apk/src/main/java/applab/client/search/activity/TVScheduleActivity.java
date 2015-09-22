package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.storage.DatabaseHelper;
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
;        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);



        String gtv = IctcCKwUtil.getNextDate(Calendar.SUNDAY);
        String radio = IctcCKwUtil.getNextDate(Calendar.SATURDAY);

        TextView t =(TextView)  findViewById(R.id.txt_gtv_day);
        t.setText(gtv);

        t =(TextView)  findViewById(R.id.txt_radio_bar_day);
        t.setText(radio);

        t =(TextView)  findViewById(R.id.txt_volta_star_day);
        t.setText(radio);
        TextView tv = (TextView) findViewById(R.id.txt_call_meida_line);
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Calling  : 057665186");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057665186"));
                startActivity(intent);
                System.out.println("Done calling 057665186");
                startActivity(intent);
            }
        });

    }

    public void callNo(View view){

        System.out.println("Calling  : 057665186");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057665186"));
        startActivity(intent);
        System.out.println("Done calling 057665186");
    }
}