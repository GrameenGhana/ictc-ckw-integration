package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import applab.client.search.R;
import applab.client.search.adapters.ProfileViewAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 11/24/15.
 */
public class FarmerProfileSecionActivity extends Activity {
    DatabaseHelper dbHelper = null;
    Farmer farmer= null;


    private ExpandableListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(getBaseContext());

        String sectionName="";
        setContentView(R.layout.activity_farmer_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String farmerId = (String) extras.get("farmerId");

            if(null == farmerId ||farmerId.isEmpty()) {
                farmer = (Farmer) extras.get("farmer");
                farmerId = farmer.getFarmID();
                sectionName = (String)extras.get("section");
            }
            farmer = dbHelper.findFarmer(farmerId);


        }

        list = (ExpandableListView) findViewById(R.id.exp_summary_profile);

        list.setGroupIndicator(null);
        final List<String> sections = new ArrayList<String>();
        sections.add(sectionName);
        Map<String,List<ItemWrapper>> wr = FarmerUtil.getFarmerDetails(farmer);


        List<ItemWrapper> itm = wr.get(sectionName);
        wr= new HashMap<String, List<ItemWrapper>>();
        wr.put(sectionName,itm);
        ProfileViewAdapter adapter = new ProfileViewAdapter(FarmerProfileSecionActivity.this, sections, wr, list,true);
        list.setAdapter(adapter);



    }


    public void viewFullProfile(View view){

        Intent t= new Intent(this, FarmerDetailedProfile.class);
        t.putExtra("farmer",farmer);
        startActivity(t);
    }
}