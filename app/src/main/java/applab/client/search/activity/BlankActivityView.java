package applab.client.search.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import applab.client.search.R;

/**
 * Created by skwakwa on 9/29/15.
 */
public class BlankActivityView extends AppCompatActivity {
    String title;
    String desc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_activity_view);


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                title = (String) extras.get("title");
                desc = (String) extras.get("desc");

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
  ;
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText(title);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        TextView tv = (TextView)findViewById(R.id.txt_blank_title);
        tv.setText(title);
        tv = (TextView)findViewById(R.id.txt_blank_desc);
        tv.setText(desc);



    }
}