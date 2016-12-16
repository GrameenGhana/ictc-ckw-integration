package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ParentExpandableListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;

import java.util.List;

/**
 * Created by skwakwa on 1/21/16.
 */
public class FarmerBaselineSummaryActivity extends BaseActivity {
    private TextView textViewMainCrop;
    private TextView textViewProportion;
    private TextView textViewPercentageSold;
    private String name;
    private String mainCrop;
    private String location;
    private TextView textViewLocation;
    Farmer farmer;
    ListView list;
    ExpandableListView expList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_farmer_baseline_summary);
    LayoutInflater mInflater = LayoutInflater.from(this);

//        helper = new DatabaseHelper(getBaseContext());

    final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
   // TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
   // mTitleTextView.setText("Farmer Management Plan");


    // mActionBar.setDisplayShowHomeEnabled(false);
    // mActionBar.setDisplayShowTitleEnabled(false);

    try {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            farmer = (Farmer) extras.get("farmer");

            location = farmer.getCommunity();
            name = farmer.getLastName() + " , " + farmer.getFirstName();

        }
        mainCrop = farmer.getMainCrop();

    } catch (Exception e) {
        e.printStackTrace();
    }


    Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
    mButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(FarmerBaselineSummaryActivity.this, FarmerActivity.class);
            intent.putExtra("type", "search");
            intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

            startActivity(intent);
        }
    });






    list = (ListView) findViewById(R.id.lst_bsum_item);
        expList =(ExpandableListView) findViewById(R.id.elv_elp);


    DatabaseHelper dbHelper=new DatabaseHelper(getBaseContext());
    //dbHelper.getIndividualFarmerInputs(farmer.getFarmID())
    List<ItemWrapper> wr = FarmerUtil.getFarmerBaseline(farmer, dbHelper.getIndividualFarmerInputs(farmer.getFarmID()));
        int[] thumbs=new int[wr.size()];


        for(int i=0;i<wr.size();i++){
            thumbs[i]=R.mipmap.ic_performance;
        }

        ParentExpandableListAdapter adapter = new ParentExpandableListAdapter(FarmerBaselineSummaryActivity.this,wr,expList,thumbs);
        expList.setGroupIndicator(null);
        expList.setAdapter(adapter);
    super.setDetails(dbHelper, "Farmer", "Previous Performance");
    super.baseLogActivity.setSection(farmer.getFullname());

}

    public void mapFarm(View view){

        Intent i = new Intent(FarmerBaselineSummaryActivity.this,FarmMapping.class);
        i.putExtra("farmer",farmer);
        startActivity(i);
//        return null;
    }

    public void ckwClick(View view) {
        Intent i = new Intent(FarmerBaselineSummaryActivity.this, CKWSearchActivity.class);
        i.putExtra("farmer", farmer);
        i.putExtra("SEARCH_CROP", farmer.getMainCrop());
        i.putExtra("SEARCH_TITLE", "");
        startActivity(i);
    }
    }
