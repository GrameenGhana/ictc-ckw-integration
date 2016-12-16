package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.FarmersAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import android.text.TextWatcher;

import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/11/15.
 */
public class FarmerActivitySelectFarmer extends BaseActivity {
    private ListView list;
    private Button instructions_button;
    List<Farmer> myFarmers = null;

    DatabaseHelper helper;

    String type = "farmer";
    String detail ="";
    Farmer farmer = null;
    String [] extraData;

    int meetingIndex;
    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_activity_select_farmer);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        helper = new DatabaseHelper(getBaseContext());
        extras = getIntent().getExtras();
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        instructions_button=(Button) findViewById(R.id.instructions);
        instructions_button.setVisibility(View.VISIBLE);
        instructions_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                detail = extras.getString("detail");

                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerActivitySelectFarmer.this);
                builder.setTitle("Meeting Instructions");

                ListView modeList = new ListView(FarmerActivitySelectFarmer.this);
                String[] stringArray = new String[0];
                if(detail.equalsIgnoreCase("Pre-Visit")){
                    stringArray= new String[] { "1.\tSensitize farmers about the Agrotech project and your intended activities",
                                                "2.\tExplain the objectives of the project",
                                                "3.\tInform farmers that they need to be registered & profiled before the planting season, \n" +
                                                        "to receive specialized services throughout the season\n",
                                                "4.\tInform farmers how they will benefit by participating on the project (check out the farmer value proposition)",
                            "5.\tInform farmers that you will be embarking on the registration & profiling exercise, as well as discussing farm planning & procurement."};
                }else if(detail.contains("VISIT 1")){
                    stringArray= new String[] { "1.\tRegister and profile all your 100 farmers before the planting season ",
                            "2.\tExplain to the farmer that you are going to ask questions to understand their \n" +
                                    "previous production performance\n",
                            "3.\tExplain to the farmer that you are going to ask questions to understand their \n" +
                                    "previous post-harvest experience\n",
                            "4.\tProvide input package if available.",
                            "5.\tProvide information on next steps ",
                            "6.\tInform farmer that you will be discussing farm planning & planting activities the next \n" +
                            "time you visit them\n"};
                }else if(detail.contains("VISIT 2")){
                    stringArray= new String[] { "1.\tGo over the farm planning questions with the farmer and explain what it involves ",
                            "2.\tElaborate on the upcoming farm plan activities which include planning & \n" +
                                    "procurement, as well as land preparation and planting\n",
                            "3.\tProvide technical advice on the importance of farm planning, proper land \n" +
                                    "preparation, planting and seed selection\n",
                            "4.\tDiscuss land preparation, tractor services and other input needs e.g seed/fertilizers",
                            "5.\tShare Radio & TV schedules with farmer & invite them to the multimedia meeting,\n" +
                                    "to view videos on land preparation, planting, as well as listen discuss the radio prog.\n",
                            "6.\tRequest farmers to mention their specific technical needs",
                    "7.\tInform farmer that you will be discussing weed control and fertilizer application during your next visit."};
                }else if(detail.contains("VISIT 3")){
                    stringArray= new String[] { "1.\tFollow the instructions provided on field mapping.",
                            "2.\tPlease indicate the number of kgs of each input that has been provided to the farmer\n" +
                                    "Confirm delivery of inputs\n",
                            "3.\tDiscuss farm operations using FMP PRODUCTION UPDATE 1",
                            "4.\tUpcoming activities on farm plan include weeding and fertilizer application",
                            "5.\tProvide technical advice on weeding and fertilizer application, by following the link to content",
                            "6.\tCollect request for assistance",
                    "7.\tShare Radio & TV schedules with farmer & invite them to the multimedia meeting,\n" +
                            "to view videos on weeding, fertilizer application, and to discuss the radio program\n",
                    "8.\tInform farmer that you will be discussing more on weed control, fertilizer \n" +
                            "application and conducting a crop assessment. \n"};
                }else if(detail.contains("VISIT 4")){
                    stringArray= new String[] { "1.\tCrop inspection will require you to visit the farmer’s field and follow instructions given",
                            "2.\tUpcoming activities on farm plan will be managing weeds in your farm & planning to \n" +
                                    "do your final fertilizer application\n",
                            "3.\tProvide technical advice on weeding, fertilizer application as well as harvesting and post-harvest processing. ",
                            "4.\tShare Radio & TV schedules with farmer & invite them to the multimedia meeting,\n" +
                                    "to view videos on harvesting & post-harvest handling as well as radio program\n",
                            "5.\tInform farmer that you will be discussing more on the final fertilizer application & post-harvest processing"};
                }else if(detail.contains("VISIT 5")) {
                    stringArray = new String[]{"1.\tDiscuss farm operations using FMP PRODUCTION UPDATE 2",
                            "2.\tDiscuss food security",
                            "3.\tUpcoming activities on farm plan include final fertilizer application in case of maize.",
                            "4.\tProvide technical advice on final fertilizer application, harvesting, & \n" +
                                    "post-harvest processing by following the link to content\n",
                            "5.\tShare Radio & TV schedules with farmer but do not invite them for another \n" +
                                    "multi-media meeting\n",
                            "6.\tCollect request for assistance ",
                            "7.\tInform farmer that you will be discussing field operations undertaken and credit repayments during your next visit."};
                }else if(detail.contains("VISIT 6")){
                    stringArray= new String[] { "1.\tDiscuss farm operations using FMP PRODUCTION UPDATE 3,4,5",
                            "2.\tReconcile credit and repayments",
                            "3.\tProvide farmers with records on their progress so far by going back to farm \n" +
                                    "records under which is under the farmer tab \n",
                            "4.\tCollect farmer feedback"};
                }

                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(FarmerActivitySelectFarmer.this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                modeList.setAdapter(modeAdapter);

                builder.setView(modeList);
                final Dialog dialog = builder.create();

                dialog.show();
            }
        });
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

        mActionBar.setTitle("Select Farmer for "+detail);



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
      //  mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);
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

            Intent intent= new Intent(FarmerActivitySelectFarmer.this, CKWSearchActivity.class);
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