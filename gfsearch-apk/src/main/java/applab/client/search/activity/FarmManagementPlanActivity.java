package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;

/**
 * Created by skwakwa on 8/25/15.
 */
public class FarmManagementPlanActivity extends Activity {
    private TextView textViewName;
    private TextView textViewMainCrop;
    private TextView textViewProportion;
    private TextView textViewPercentageSold;
    private String name;
    private String mainCrop;
    private String location;
    private TextView textViewLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmp);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

//        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Farmer Management Plan");
        setContentView(R.layout.activity_fmp);

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        Farmer farmer = null;
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
                Intent intent = new Intent(FarmManagementPlanActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
//        textViewName=(TextView) findViewById(R.id.textView_name);
//        textViewName.setText(name);
//        textViewMainCrop=(TextView) findViewById(R.id.textView_fmp_mainCrop);
//        textViewMainCrop.setText(mainCrop);
//        textViewProportion=(TextView) findViewById(R.id.textView_proportion);
//        textViewLocation=(TextView) findViewById(R.id.textView_location);
//
//        textViewLocation.setText(location);
//        textViewPercentageSold=(TextView) findViewById(R.id.textView_percentageSold);
//


        textViewName = (TextView) findViewById(R.id.textView_fmp_name);
        System.out.println("text view name" + farmer.getLastName() + ", " + farmer.getFirstName());
        try {
            System.out.println("Text View : " + textViewName.toString());
        } catch (Exception e) {
            System.out.println("Excception e: " + e.getLocalizedMessage());
        }
        textViewName.setText(farmer.getLastName() + ", " + farmer.getFirstName());
        textViewName = (TextView) findViewById(R.id.textView_fmp_community);
//        textViewName.setText(farmer.getCommunity());


        textViewName = (TextView) findViewById(R.id.textView_fmp_fbo);
        textViewName.setText(farmer.getFarmerBasedOrg());


        textViewName = (TextView) findViewById(R.id.textView_fmp_labour);
        textViewName.setText(farmer.getLabour());


        textViewName = (TextView) findViewById(R.id.textView_fmp_land_identification);
        textViewName.setText(farmer.getDateOfLandIdentification());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fmp_land_loc);
        textViewMainCrop.setText(farmer.getLocationOfLand());

        textViewName = (TextView) findViewById(R.id.textView_fmp_main_contact_sales);
        textViewName.setText(farmer.getPosContact());

        textViewName = (TextView) findViewById(R.id.textView_fmp_mainCrop);
        textViewName.setText(farmer.getMainCrop());


        textViewName = (TextView) findViewById(R.id.textView_fmp_manual_weed_control);
        textViewName.setText(farmer.getDateManualWeeding());


        textViewName = (TextView) findViewById(R.id.textView_fmp_month_final_product_sold);
        textViewName.setText(farmer.getMonthFinalProductSold());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fmp_month_sale_begin);
        textViewMainCrop.setText(farmer.getMonthSellingStarts());

        textViewName = (TextView) findViewById(R.id.textView_fmp_plant_date);
        textViewName.setText(farmer.getPlantingDate());

        textViewName = (TextView) findViewById(R.id.textView_fmp_plot_size);
        textViewName.setText(farmer.getSizePlot());


        textViewName = (TextView) findViewById(R.id.textView_fmp_price_final_batch_sold);
        textViewName.setText(farmer.getExpectedPriceInTon());


        textViewName = (TextView) findViewById(R.id.textView_fmp_target_area);
        textViewName.setText(farmer.getTargetArea());
        textViewMainCrop = (TextView) findViewById(R.id.textView_fmp_target_next_season);
        textViewMainCrop.setText(farmer.getTargetNextSeason());

        textViewName = (TextView) findViewById(R.id.textView_fmp_target_per_acre);
        textViewName.setText(farmer.getTargetArea());

        textViewName = (TextView) findViewById(R.id.textView_fmp_tech_needs);
        textViewName.setText(farmer.getTechNeeds1());


        textViewName = (TextView) findViewById(R.id.textView_fmp_variety);
        textViewName.setText(farmer.getVariety());


        textViewName = (TextView) findViewById(R.id.textView_fmp_labour);
        textViewName.setText(farmer.getLabour());
    }
}