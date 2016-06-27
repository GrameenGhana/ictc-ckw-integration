package applab.client.search.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ParentExpandableListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;
import applab.client.search.utils.IctcCKwUtil;

import java.util.List;

/**
 * Created by skwakwa on 8/25/15.
 */
public class FarmManagementPlanActivity extends BaseActivity {
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
    private DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmp);
       ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Farmer Management Plan");
        LayoutInflater mInflater = LayoutInflater.from(this);

        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
      //  mTitleTextView.setText("Farmer Management Plan");
        setContentView(R.layout.activity_fmp);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");

                location = farmer.getCommunity();
                name = farmer.getLastName() + " , " + farmer.getFirstName();
            }
            mainCrop = farmer.getMainCrop();
            farmer=helper.findFarmer(farmer.getFarmID());
            ViewGroup view = (ViewGroup)getWindow().getDecorView();
            IctcCKwUtil.setFarmerDetails(view,R.id.default_view_profile_item,farmer.getFullname(),farmer,true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmManagementPlanActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        System.out.println("text view name" + farmer.getLastName() + ", " + farmer.getFirstName());

        //textViewName.setText(farmer.getLastName() + ", " + farmer.getFirstName());


        list = (ListView) findViewById(R.id.lst_fmp_item);
        expList = (ExpandableListView) findViewById(R.id.exp_fmp);

        DatabaseHelper dbHelper=new DatabaseHelper(getBaseContext());
        //dbHelper.getIndividualFarmerInputs(farmer.getFarmID())
        List<ItemWrapper> wr = FarmerUtil.getFarmManagementPlan(farmer,dbHelper.getIndividualFarmerInputs(farmer.getFarmID()));
        int[] thumbs={R.mipmap.production,R.mipmap.harvest,R.mipmap.farm_input,R.mipmap.farmer_budget};
        ParentExpandableListAdapter adapter = new ParentExpandableListAdapter(FarmManagementPlanActivity.this,wr,expList,thumbs);
        expList.setAdapter(adapter);
        expList.setGroupIndicator(null);
        super.setDetails(dbHelper, "Farmer", "Farmer Management Plan");
        super.baseLogActivity.setSection(farmer.getFullname());

    }

    public void mapFarm(View view){

        Intent i = new Intent(FarmManagementPlanActivity.this,FarmMapping.class);
        i.putExtra("farmer",farmer);
        startActivity(i);
//        return null;
    }

    public void ckwClick(View view){
        Intent i = new Intent(FarmManagementPlanActivity.this,CKWSearchActivity.class);
        i.putExtra("farmer",farmer);
        i.putExtra("SEARCH_CROP",farmer.getMainCrop());
        i.putExtra("SEARCH_TITLE","");
        startActivity(i);

    }
}