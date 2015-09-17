package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.FarmersAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import android.text.TextWatcher;

import applab.client.search.storage.DatabaseHelperConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/11/15.
 */
public class FarmerActivitySelectFarmer extends Activity {
    private ListView list;
    List<Farmer> myFarmers = null;

    DatabaseHelper helper;

    String type = "farmer";
    String detail ="";
    Farmer farmer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_activity_select_farmer);


        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmerActivitySelectFarmer.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });


        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type");

            detail = extras.getString("detail");


            farmer = (Farmer) extras.get("farmer");
            if(null!=farmer){
                processOnClickRequest(farmer);
            }

        } catch (Exception e) {

        }

        mTitleTextView.setText("Select Farmer for "+detail);




        EditText editText = (EditText) findViewById(R.id.txt_search_fsp);

        TextView tv= (TextView) findViewById(R.id.fsf_act_title);
        tv.setText(detail);


        myFarmers = helper.getFarmers();
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
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.lst_farmer_fsf);
        final FarmersAdapter adapter = new FarmersAdapter(FarmerActivitySelectFarmer.this, names, locations, mainCrops, groups);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                processOnClickRequest(myFarmers, i);

            }

        });



        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                int textlength = cs.length();

                final List<Farmer> farmSearchedList = new ArrayList<Farmer>();
                for(Farmer farmer: myFarmers){


                    if(farmer.getFullname().toLowerCase().contains(cs.toString().toLowerCase())){

                        farmSearchedList.add(farmer);
                    }
                }
                 FarmersAdapter adapter = new FarmersAdapter(FarmerActivitySelectFarmer.this,farmSearchedList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent;

                        processOnClickRequest(myFarmers,i);

                    }

                });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }


        });

    }



    public void processOnClickRequest(Farmer farmer){
        if(type.equalsIgnoreCase("farm-map")){

            Intent intent= new Intent(FarmerActivitySelectFarmer.this, FarmMapping.class);
            intent.putExtra("farmer",farmer);

            startActivity(intent);
        }else if(type.equalsIgnoreCase("farm-input")){

            Intent intent= new Intent(FarmerActivitySelectFarmer.this, FarmerInputActivty.class);
            intent.putExtra("farmer",farmer);

            startActivity(intent);
        }else if(type.equalsIgnoreCase("ckw")){

            Intent intent= new Intent(FarmerActivitySelectFarmer.this, MainActivity.class);
            intent.putExtra("farmer",farmer);
            intent.putExtra("SEARCH_CROP",farmer.getMainCrop());
            startActivity(intent);
        }
    }


    public void processOnClickRequest(List<Farmer> farmers,int i){
        processOnClickRequest(farmers.get(i));

    }



}