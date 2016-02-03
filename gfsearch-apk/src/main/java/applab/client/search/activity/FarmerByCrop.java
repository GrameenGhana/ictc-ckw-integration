package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmerByCrop   extends BaseActivityGroup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.farmer_by_crop);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmerByCrop.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        mTitleTextView.setText("Farmers");
        TabHost tabHost = (TabHost)findViewById(R.id.tab_farmer_by_crop);
        tabHost.setup(this.getLocalActivityManager());
        String []  crops = {"Maize","Cassava","Yam","Rice"};
        Intent gencal  = null;
        for (String crop:crops){
            gencal  = new Intent(FarmerByCrop.this, FarmersCrop.class);
            gencal.putExtra("crop",crop);
            TabHost.TabSpec spec =tabHost.newTabSpec(crop).setIndicator(crop).setContent(gencal);
            tabHost.addTab(spec);
        }
        tabHost.setCurrentTab(0);
        DatabaseHelper dh = new DatabaseHelper(getBaseContext());
        super.setDetails(dh,"Client","Farmer By Crop");
        mActionBar.setCustomView(mCustomView);

        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
//        myInputs =  dbHelper.getIndividualFarmerInputs(farmer.getFarmID());
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}