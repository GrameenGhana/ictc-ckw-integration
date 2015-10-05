package applab.client.search.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import applab.client.search.R;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmerByCrop   extends ActivityGroup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.farmer_by_crop);

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
    }
}