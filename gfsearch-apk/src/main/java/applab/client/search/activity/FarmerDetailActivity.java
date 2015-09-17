package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmerDetailActivity extends Activity {
    private TextView textViewName;
    private TextView textViewMainCrop;
    private TextView textViewProportion;
    private TextView textViewPercentageSold;
    private String name;
    private String mainCrop;
    private String location;
    private TextView textViewLocation;
    ListView list;
    DatabaseHelper dbHelper=null;
    Farmer farmer = null;
    List<Meeting> meetings = new ArrayList<Meeting>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_summary);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Farmer Details");

        dbHelper = new DatabaseHelper(getBaseContext());
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");

                farmer = dbHelper.findFarmer(farmer.getFarmID());
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
                Intent intent = new Intent(FarmerDetailActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        final Farmer myFarmer = farmer;
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
//        textViewName=(TextView) findViewById(R.id.textView_name);
//        textViewName.setText(name);
//        textViewMainCrop=(TextView) findViewById(R.id.textView_fp_mainCrop);
//        textViewMainCrop.setText(mainCrop);
        textViewProportion = (TextView) findViewById(R.id.textView_proportion);
//        textViewLocation=(TextView) findViewById(R.id.textView_location);
//
//        textViewLocation.setText(location);
//        System.out.println("FarmingiD : " + farmer.getId());

        Button fmpButton = (Button) findViewById(R.id.fmp_farmer);
        fmpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDetailActivity.this, FarmManagementPlanActivity.class);
                intent.putExtra("farmer", myFarmer);
                System.out.println(
                        "Farmer id" + myFarmer.getCluster()
                );
                startActivity(intent);
            }
        });
//}


        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewName.setText(farmer.getLastName() + ", " + farmer.getFirstName());
        textViewName = (TextView) findViewById(R.id.textView_fp_community);
        textViewName.setText(farmer.getCommunity());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fp_cluster);
        textViewMainCrop.setText(farmer.getCluster());

        System.out.println("Farmer CLuster : " + farmer.getCluster());

        textViewName = (TextView) findViewById(R.id.textView_fp_district);
        textViewName.setText(farmer.getDistrict());

        textViewName = (TextView) findViewById(R.id.textView_fp_fbo);
        textViewName.setText(farmer.getFarmerBasedOrg());


        textViewName = (TextView) findViewById(R.id.textView_fp_labour);
        textViewName.setText(farmer.getLabour());


        textViewName = (TextView) findViewById(R.id.textView_fp_land_identification);
        textViewName.setText(farmer.getDateOfLandIdentification());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fp_land_loc);
        textViewMainCrop.setText(farmer.getLocationOfLand());

        textViewName = (TextView) findViewById(R.id.textView_fp_main_contact_sales);
        textViewName.setText(farmer.getPosContact());

        textViewName = (TextView) findViewById(R.id.textView_fp_mainCrop);
        textViewName.setText(farmer.getMainCrop());


        textViewName = (TextView) findViewById(R.id.textView_fp_manual_weed_control);
        textViewName.setText(farmer.getDateManualWeeding());


        textViewName = (TextView) findViewById(R.id.textView_fp_month_final_product_sold);
        textViewName.setText(farmer.getMonthFinalProductSold());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fp_month_sale_begin);
        textViewMainCrop.setText(farmer.getMonthSellingStarts());

        textViewName = (TextView) findViewById(R.id.textView_fp_plant_date);
        textViewName.setText(farmer.getPlantingDate());

        textViewName = (TextView) findViewById(R.id.textView_fp_plot_size);
        textViewName.setText(farmer.getSizePlot());


        textViewName = (TextView) findViewById(R.id.textView_fp_price_final_batch_sold);
        textViewName.setText(farmer.getExpectedPriceInTon());


        textViewName = (TextView) findViewById(R.id.textView_fp_target_area);
        textViewName.setText(farmer.getTargetArea());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fp_target_next_season);
        textViewMainCrop.setText(farmer.getTargetNextSeason());

        textViewName = (TextView) findViewById(R.id.textView_fp_target_per_acre);
        textViewName.setText(farmer.getTargetArea());

        textViewName = (TextView) findViewById(R.id.textView_fp_tech_needs);
        textViewName.setText(farmer.getTechNeeds1());


        textViewName = (TextView) findViewById(R.id.textView_fp_variety);
        textViewName.setText(farmer.getVariety());


        textViewName = (TextView) findViewById(R.id.textView_fp_village);
        textViewName.setText(farmer.getVillage());

        textViewName = (TextView) findViewById(R.id.textView_fp_labour);
        textViewName.setText(farmer.getLabour());
        if (mainCrop.equalsIgnoreCase("Rice")) {
            Drawable rice = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_rice);
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(rice, null, null, null);
//            textViewPercentageSold.setText(">50%");
        } else if (mainCrop.equalsIgnoreCase("Cassava")) {
            Drawable cassava = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_cassava);
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(cassava, null, null, null);
//            textViewPercentageSold.setText(">70%");
        } else if (mainCrop.equalsIgnoreCase("Maize")) {
            Drawable maize = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_maize);
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(maize, null, null, null);
//            textViewPercentageSold.setText(">80%");
        }



        list = (ListView) findViewById(R.id.lst_ind_farm_visit);
        meetings =  dbHelper.getFarmerMeetings(farmer.getFarmID());

        System.out.println("Meeting Indexx : "+meetings.size());
        System.out.println("Ktd : "+list.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(FarmerDetailActivity.this, meetings, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmerDetailActivity.this,MeetingIndexActivity.class);
                intent.putExtra("mi",meetings.get(i).getMeetingIndex());
                intent.putExtra("mt", meetings.get(i).getTitle());
                intent.putExtra("farmerId", meetings.get(i).getFarmer());
                startActivity(intent);

            }
        });
    }

    public void mapFarm(View view) {
//        Intent intent = new Intent(FarmerDetailActivity.this, ListCheckBoxActivity.class);
        Intent intent = new Intent(FarmerDetailActivity.this, FarmMapping.class);
        intent.putExtra("type","search");
//        intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());
        intent.putExtra("farmer",farmer);
        startActivity(intent);
    }

    public void farmerInput(View view) {
//        Intent intent = new Intent(FarmerDetailActivity.this, ListCheckBoxActivity.class);
        Intent intent = new Intent(FarmerDetailActivity.this, FarmerInputActivty.class);
        intent.putExtra("farmer",farmer);
//        intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

        startActivity(intent);
    }
}