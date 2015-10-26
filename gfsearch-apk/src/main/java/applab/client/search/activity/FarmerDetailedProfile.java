package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.ClusterAdapter;
import applab.client.search.adapters.ProfileViewAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 10/22/15.
 */
public class FarmerDetailedProfile extends BaseActivity {
    DatabaseHelper helper;
    private ExpandableListView list;
    Farmer farmer;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_detailed_profile);

        helper = new DatabaseHelper(getBaseContext());

        super.setDetails(helper,"Farmer","Farm Details");

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");
                farmer = helper.findFarmer(farmer.getFarmID());
            }
        }catch (Exception e){

        }


        list = (ExpandableListView) findViewById(R.id.exp_detailed_profile);
        list.setGroupIndicator(null);
        final List<String> sections = new ArrayList<String>();
        sections.add("Biodata");
        sections.add("Production");
        sections.add("Post Harvest");


        sections.add("Baseline Post Harvest");
        Map<String,List<ItemWrapper>> wr = FarmerUtil.getFarmerDetails(farmer);

        ProfileViewAdapter adapter = new ProfileViewAdapter(FarmerDetailedProfile.this, sections, wr, list);
        list.setAdapter(adapter);
    }
}