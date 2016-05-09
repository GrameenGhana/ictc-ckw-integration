package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.FarmersAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.utils.IctcCKwUtil;

import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmerActivity extends BaseActivity {
    private ListView list;
    List<Farmer> myFarmers = null;

    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmerActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        mTitleTextView.setText("Farmers");
        mActionBar.setTitle("Farmers");
       // mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);

        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        String type = "farmer";
        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type");
        } catch (Exception e) {

        }

        if (type.equalsIgnoreCase("farmer"))
            myFarmers = helper.getFarmers();
        else if (type.equalsIgnoreCase("comm")) {
            String community = extras.getString("name");
            myFarmers = helper.getSearchedFarmers(DatabaseHelperConstants.COMMUNITY, community);
        } else if (type.equalsIgnoreCase("search")) {
            String q = extras.getString("q");
            myFarmers = helper.searchFarmer(q);
        }


        super.setDetails(helper,"Farmer","Farm Mapping");
        String[] names = new String[myFarmers.size()];
        String[] locations = new String[myFarmers.size()];
        String[] mainCrops = new String[myFarmers.size()];
        String[] groups = new String[myFarmers.size()];
        final String[] ids = new String[myFarmers.size()];
        int cnt = 0;

        for (Farmer f : myFarmers) {
            names[cnt] = f.getLastName() + ", " + f.getFirstName();
            locations[cnt] = f.getCommunity();
            mainCrops[cnt] = ("".equalsIgnoreCase(f.getMainCrop())) ? "Not Set " : f.getMainCrop();
            groups[cnt] = f.getFarmerBasedOrg();
            ids[cnt] = f.getId();
            cnt++;
        }

        list = (ListView) findViewById(R.id.listView);
        FarmersAdapter adapter = new FarmersAdapter(FarmerActivity.this, names, locations, mainCrops, groups,myFarmers);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;

                intent = new Intent(FarmerActivity.this, FarmerDetailActivity.class);
                intent.putExtra("farmer", myFarmers.get(i));
//                System.out.println("Farmer : "+myFarmers.get(i).getFirstName());
                startActivity(intent);

            }

        });
    }
}