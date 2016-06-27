package applab.client.search.activity;

import android.app.ActionBar;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ListWithThumbnailAdapter;
import applab.client.search.adapters.MeetingInvAdapter;
import applab.client.search.adapters.UnitsListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.model.Meeting;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmerDetailActivity extends BaseActivityGroup {
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

    List<FarmerInputs> myInputs =new ArrayList<FarmerInputs>();
    private TabHost tabHost;
    private LinearLayout linearlayout_crop;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_farmer_summary);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Farmer Details");
        LayoutInflater mInflater = LayoutInflater.from(this);
        //tabHost = (TabHost) findViewById(R.id.tabHost3);
        listView=(ListView) findViewById(R.id.listView);
        String[] items={"Farmer Profile","Farm Management Plan","Farm Input","Farmer Budget"};
        int[] thumbs={R.mipmap.famer_profile,R.mipmap.farmer_management_plan,
                R.mipmap.farm_input,R.mipmap.farmer_budget};
        ListWithThumbnailAdapter adapter= new ListWithThumbnailAdapter(FarmerDetailActivity.this,items,thumbs);
        listView.setAdapter(adapter);
        //linearlayout_crop=(LinearLayout) findViewById(R.id.linearlayout_crops);
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Farmer Details");

        dbHelper = new DatabaseHelper(getBaseContext());
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String farmerId = (String) extras.get("farmerId");

                farmer = (Farmer) extras.get("farmer");

                if(null == farmerId ||farmerId.isEmpty()) {
                    farmer = (Farmer) extras.get("farmer");
                    System.out.println("Farmer F : "+farmer.getFarmID());
                    System.out.println("Farmer F1 : "+farmer.getId());
                    farmerId = farmer.getFarmID();
                    System.out.println("Farmer Id : "+farmerId);

                }

                System.out.println("Farming Id : "+farmerId);

                farmer = dbHelper.findFarmer(farmerId);
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
        myInputs =  dbHelper.getIndividualFarmerInputs(farmer.getFarmID());

        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        TextView names = (TextView) findViewById(R.id.textView_name);
        TextView locations = (TextView) findViewById(R.id.textView_location);
        TextView group = (TextView) findViewById(R.id.textView_groups);
        ImageView icon = (ImageView) findViewById(R.id.imageView_icon);
        String crop = farmer.getMainCrop();



        names.setText(farmer.getLastName() + " " + farmer.getFirstName());
        locations.setText(farmer.getCommunity()+", "+farmer.getDistrict()+", "+farmer.getRegion());
        group.setText("Farmer");
        textViewProportion = (TextView) findViewById(R.id.textView_proportion);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                        intent=new Intent(FarmerDetailActivity.this, FarmerProfileActivity.class);
                        intent.putExtra("farmer", farmer);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(FarmerDetailActivity.this, FarmManagementPlanActivity.class);
                        intent.putExtra("farmer", farmer);
                        intent.putExtra("type", "search");
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(FarmerDetailActivity.this, FarmerInputActivty.class);
                        intent.putExtra("standalone",true);
                        intent.putExtra("farmer", farmer);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(FarmerDetailActivity.this, FarmBudgetActivity.class);
                        intent.putExtra("farmer", farmer);
                        startActivity(intent);
                        break;
                }
            }
        });
        /*
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(FarmerDetailActivity.this,false);
        mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
        tabHost.setup(mLocalActivityManager);
        FragmentTabHost.TabSpec spec = tabHost.newTabSpec("tag");
        spec.setIndicator("Farmer Profile");
        spec.setContent(new Intent(FarmerDetailActivity.this, FarmerProfileActivity.class)
                .putExtra("farmer", farmer));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tag1");
        spec.setIndicator("Farm Management Plan");
        spec.setContent(new Intent(FarmerDetailActivity.this, FarmManagementPlanActivity.class)
                .putExtra("type", "search")
                .putExtra("farmer", farmer));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tag2");
        spec.setIndicator("Farm Input");
        spec.setContent(new Intent(FarmerDetailActivity.this,  FarmerInputActivty.class)
                //.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString())
                .putExtra("farmer", farmer));
        tabHost.addTab(spec);


        spec = tabHost.newTabSpec("tag3");
        spec.setIndicator("Farmer Budget");
        spec.setContent(new Intent(FarmerDetailActivity.this, FarmBudgetActivity.class)
                //.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString())
                .putExtra("farmer", farmer));
        tabHost.addTab(spec);


        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                if (tabHost.getCurrentTab() == 1) {
                    linearlayout_crop.setVisibility(View.GONE);
                } else if (tabHost.getCurrentTab() == 2) {
                    linearlayout_crop.setVisibility(View.GONE);
                }else if(tabHost.getCurrentTab()==0){
                    linearlayout_crop.setVisibility(View.VISIBLE);
                }
            }
        });
*/

        super.setDetails(dbHelper,"Farmer","Farmer Details");
        super.baseLogActivity.setSection(name);
        IctcCKwUtil.setFarmerDetails(getWindow().getDecorView().getRootView(),R.id.profile_container,farmer.getFullname(),farmer);
//        textViewLocation=(TextView) findViewById(R.id.textView_location);
//
//        textViewLocation.setText(location);
//        System.out.println("FarmingiD : " + farmer.getId());

        /*
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
        });*/
//}

/*
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


        textViewName = (TextView) findViewById(R.id.textView_fp_mnanual_weed_control);
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
        textViewName.setText(farmer.getLabour());*/
//        if (mainCrop.equalsIgnoreCase("Rice")) {
//            Drawable rice = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_rice);
//            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(rice, null, null, null);
////            textViewPercentageSold.setText(">50%");
//        } else if (mainCrop.equalsIgnoreCase("Cassava")) {
//            Drawable cassava = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_cassava);
//            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(cassava, null, null, null);
////            textViewPercentageSold.setText(">70%");
//        } else if (mainCrop.equalsIgnoreCase("Maize")) {
//            Drawable maize = FarmerDetailActivity.this.getResources().getDrawable(R.drawable.ic_maize);
//            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(maize, null, null, null);
////            textViewPercentageSold.setText(">80%");
//        }


//        TabHost tabHost = (TabHost)findViewById(R.id.tabHost3);
//        tabHost.setup(this.getLocalActivityManager());
//        Intent gencal  = null;
//
//        gencal  = new Intent(FarmerDetailActivity.this, FarmerMeetingActivity.class);
//        gencal.putExtra("farmer", farmer.getFarmID());
//        TabHost.TabSpec spec =tabHost.newTabSpec("Meetings").setIndicator("Meetings").setContent(gencal);
//        tabHost.addTab(spec);
//
//
//        gencal  = new Intent(FarmerDetailActivity.this, FarmerDetailInputActivity.class);
//        gencal.putExtra("farmer", farmer.getFarmID());
//        spec =tabHost.newTabSpec("Farm Inputs").setIndicator("Farm Inputs").setContent(gencal);
//        tabHost.addTab(spec);
//
//
//
//        tabHost.setCurrentTab(0);
    }
/*
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
    }*/

    public void setValuesForFields(){

        /**
         *  FarmerInputReceivedWrapper seedsReceived = searchNeeds(farmers, "seeds");
         FarmerInputReceivedWrapper fertReceived = searchNeeds(farmers, "fertiliser");
         FarmerInputReceivedWrapper ploughReceived = searchNeeds(farmers, "plough");
         */
        String  [] details= new String[myInputs.size()];
        String [] firstLetters= new String[myInputs.size()];
        String [] units= new String[myInputs.size()];
        boolean  [] enabled = new boolean[myInputs.size()];
        Arrays.fill(enabled, true);

        int cnt=0;
        for(FarmerInputs fi : myInputs){
            String title="";
            String xt="";String act=String.valueOf(fi.getQty());
            if(fi.getQty()<1.0){
                xt=" Not Given ";
                act="NA";
            }else{
                act= IctcCKwUtil.formatDoubleNoDecimal(fi.getQty());
            }
            if(fi.getName().equalsIgnoreCase("plough")){
                units[cnt]="";
            }else{
                units[cnt]="bags";
            }
            firstLetters[cnt]=act;

            details[cnt]= fi.getName().toUpperCase()+xt;

            cnt++;
        }
      ListView  listView = (ListView)findViewById(R.id.lst_inputs);

        ListAdapter adapter = new UnitsListAdapter(FarmerDetailActivity.this, details,firstLetters ,units,enabled,getResources().getStringArray(R.array.text_colors));
        listView.setAdapter(adapter);


    }
    public void setMeetingList(){
        list = (ListView) findViewById(R.id.list_ind_meet);
        meetings =  dbHelper.getFarmerMeetings(farmer.getFarmID());
        meetings = IctcCKwUtil.sortMeeting(meetings);

        System.out.println("Meeting Indexx : "+meetings.size());
        System.out.println("Ktd : "+list.toString());


        String  [] colors =  getResources().getStringArray(R.array.text_colors);
        MeetingInvAdapter adapter = new MeetingInvAdapter(FarmerDetailActivity.this, meetings, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmerDetailActivity.this,MeetingIndexActivity.class);
                intent.putExtra("mi",meetings.get(i).getMeetingPosition());
                intent.putExtra("mid",meetings.get(i).getId());
                intent.putExtra("mtype",meetings.get(i).getType());
                intent.putExtra("atd",meetings.get(i).getAttended());
                System.out.println("Sendt ID : "+meetings.get(i).getId());
                intent.putExtra("mt", meetings.get(i).getTitle());
                intent.putExtra("farmerId", meetings.get(i).getFarmer());
                startActivity(intent);

            }
        });
    }

    public void selectFarmInput(View view){
        tabHost.setCurrentTab(2);

    }
}