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
import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/11/15.
 */
public class FarmerActivitySelectFarmer extends BaseActivity {
    private ListView list;
    List<Farmer> myFarmers = null;

    DatabaseHelper helper;

    String type = "farmer";
    String detail ="";
    Farmer farmer = null;
    String [] extraData;

    int meetingIndex;
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
        Button btnJustBrowse = (Button)findViewById(R.id.btn_just_browse);
        btnJustBrowse.setVisibility(Button.GONE);


        System.out.println("Just Browse");

        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type");

            meetingIndex = extras.getInt("index");
            System.out.println("TYpe : "+meetingIndex);
            if(type.equalsIgnoreCase("MI")){
                try {

                    System.out.println("MI  : "+type);
                    extraData= new  String[6];

                    extraData[0]=String.valueOf(extras.getInt("mi"));
                    extraData[1]=extras.getString("mt");
                    extraData[2]=extras.getString("mid");
                    extraData[3]=extras.getString("mtype");
                    extraData[4]=String.valueOf(extras.getInt("atd"));
                    System.out.println("MI Index : "+extraData[0]+" Dt : "+   extraData[1]+" mtype "+   extraData[3]);
                    btnJustBrowse.setVisibility(Button.VISIBLE);

                }catch(Exception e){
                    System.out.println("ExceptionMI Index e :"+e.getLocalizedMessage());
                    e.printStackTrace();
                }

            }
            detail = extras.getString("detail");


            farmer = (Farmer) extras.get("farmer");

            if(null!=farmer){
                processOnClickRequest(farmer);
            }

        } catch (Exception e) {

        }

        mTitleTextView.setText("Select Farmer for "+detail);



        super.setDetails(helper,"Farmer","Farmer Select",detail,"");

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
        final FarmersAdapter adapter = new FarmersAdapter(FarmerActivitySelectFarmer.this, names, locations, mainCrops, groups,myFarmers);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Farmer Sel : "+i);
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

                        processOnClickRequest(farmSearchedList,i);

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

            intent.putExtra("SEARCH_CROP", IctcCKwUtil.cropToCKWLabel(farmer.getMainCrop()));
            intent.putExtra("SEARCH_TITLE",detail);
            startActivity(intent);
        }else if(type.equalsIgnoreCase("MI")){

            System.out.println("Extra Data : "+extraData.length);
            Intent intent = new Intent(FarmerActivitySelectFarmer.this, MeetingIndexActivity.class);
            intent.putExtra("mi",Integer.parseInt(extraData[0]));
            intent.putExtra("mt", extraData[1]);
            intent.putExtra("mid",extraData[2]);
            intent.putExtra("mtype",extraData[3]);
            intent.putExtra("atd", Integer.parseInt(extraData[4]));
            intent.putExtra("farmerId", farmer.getFarmID());
            startActivity(intent);
        }else if(type.equalsIgnoreCase("nma")) {
            Intent intent = new Intent(FarmerActivitySelectFarmer.this, NextMeetingActivity.class);
            intent.putExtra("mi",meetingIndex);
            intent.putExtra("mtype","Individual");
            System.out.println("mimimi :"+meetingIndex+" mtype : Individual");
            intent.putExtra("farmer",farmer);
            intent.putExtra("crop", farmer.getMainCrop());
            startActivity(intent);
        }
        }



    public void processJustBrowse(View view){


            System.out.println("Extra Data : "+extraData.length);
            Intent intent = new Intent(FarmerActivitySelectFarmer.this, MeetingIndexActivity.class);
            intent.putExtra("mi",Integer.parseInt(extraData[0]));
            intent.putExtra("mt", extraData[1]);
            intent.putExtra("mid",extraData[2]);
            intent.putExtra("mtype",extraData[3]);
            intent.putExtra("atd", Integer.parseInt(extraData[4]));
            intent.putExtra("farmerId", "");
        intent.putExtra("jb", "Y");
            startActivity(intent);

    }

    public void processOnClickRequest(List<Farmer> farmers,int i){
        processOnClickRequest(farmers.get(i));

    }



}