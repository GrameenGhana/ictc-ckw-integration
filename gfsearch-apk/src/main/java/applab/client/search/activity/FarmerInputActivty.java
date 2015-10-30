package applab.client.search.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;
import applab.client.search.utils.ConnectionUtil;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by grameen on 9/9/15.
 */
public class FarmerInputActivty extends BaseActivity {

    DatabaseHelper helper = null;
    List<FarmerInputs> myInputs =new  ArrayList<FarmerInputs>();
    Farmer farmer=null;
    long stime =0l;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        String farmer_id = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        helper = new DatabaseHelper(getBaseContext());
        boolean includeHeader = false;
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");
                try{
                    includeHeader = extras.getBoolean("standalone");

                }catch(Exception e){}
                if(includeHeader) {
                    IctcCKwUtil.setFarmerDetails(getWindow().getDecorView().getRootView(), R.id.ccs_layout, farmer.getFullname(), farmer, true);
                }else{

                    IctcCKwUtil.setFarmerDetails(getWindow().getDecorView().getRootView(), R.id.ccs_layout, "", farmer, true);
                }
               myInputs =  helper.getIndividualFarmerInputs(farmer.getFarmID());

                System.out.println("MyInputs :"+myInputs.size());
                setValuesForFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String f = farmer_id;

        super.setDetails(helper,"Farmer","Farmer Input");
        stime = super.getStartTime();


        Button save = (Button) findViewById(R.id.btnInputSave);
        save.setOnClickListener(new View.OnClickListener() {
             CheckBox seed = (CheckBox) findViewById(R.id.seedBox);
             CheckBox fertilizer = (CheckBox) findViewById(R.id.fertilizercheckbox);
             CheckBox ploughing = (CheckBox) findViewById(R.id.ploughingBox);
             EditText seedbags = (EditText) findViewById(R.id.seedbags);
            EditText fertilizerBags = (EditText) findViewById(R.id.fertilizerbags);

             String fertilizerInput = "0";
             String seedInput = "0";
             String ploughingInput = "0";

            public void onClick(View view) {

//              if (seed.isChecked())
                    seedInput = seedbags.getText().toString();
//              if (fertilizer.isChecked())
                    fertilizerInput = fertilizerBags.getText().toString();
                if (ploughing.isChecked())
                    ploughingInput = "Yes";

                System.out.println("Plough Enabled : "+ploughingInput);

                String parameterurl = "fid="+f+"&s=" + seedInput + "&f=" + fertilizerInput + "&p=" + ploughingInput;
                System.out.println(parameterurl);
//                ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, parameterurl, "fi", "input sent to server");
//

                String v="";
                if(myInputs.size()>0){
                    JSONArray inputs = new JSONArray();
                    for(FarmerInputs fi : myInputs){
                        if(fi.getName().equalsIgnoreCase("seeds")){
                           fi.setQty(Double.parseDouble(seedInput));
                        }else if(fi.getName().equalsIgnoreCase("fertiliser")){
                            fi.setQty(Double.parseDouble(fertilizerInput));
                        }else if(fi.getName().equalsIgnoreCase("plough")){
                            if(ploughing.isChecked()){
                                fi.setQty(1.0);
                            }
                            else{fi.setQty(0);
                            }
                        }
v="Saved Successfully";
                        System.out.println("Saving : "+fi.getId()+" <> "+fi.getName()+fi.getQty());
                        try {
                            inputs.put(fi.getJson());
                        }catch(Exception e){

                        }

                        helper.updateFarmInput(fi);


                    }

                    JSONObject objs = new JSONObject();

                    try {


                        objs.put("user_id",farmer.getFarmID());
                        objs.put("page","Farmer Input Received");
                        objs.put("type","edit");
                        objs.put("section","");
                        objs.put("farm_inputs",inputs);
                        objs.put("imei", IctcCKwUtil.getImei(getBaseContext()));
                        objs.put("version",IctcCKwUtil.getAppVersion());
                        objs.put("battery",IctcCKwUtil.getBatteryLevel(getBaseContext()));
                        helper.insertCCHLog("Farmer",objs.toString(),stime, System.currentTimeMillis());

                    }catch(Exception e ){

                    }

                }else{


                    System.out.println("Saving FI");
                    if(seedInput.isEmpty()) {
                        seedInput="0";
                    v="Some Values where Empty";
                    }
                    if(fertilizerInput.isEmpty()) {
                        fertilizerInput = "0";
                        v="Some Values where Empty";
                    }
                    FarmerInputs farmSeeds = new FarmerInputs(0,"seeds",new Date(),"0",Double.parseDouble(seedInput), farmer.getFarmID());
                    FarmerInputs farmFert = new FarmerInputs(0,"fertiliser",new Date(),"0",Double.parseDouble(fertilizerInput), farmer.getFarmID());
                    double pValue=0;
                    if(ploughingInput.equalsIgnoreCase("Yes"))
                        pValue=1;
                    FarmerInputs farmplough = new FarmerInputs(0,"plough",new Date(),"0",pValue, farmer.getFarmID());

                    helper.saveFarmInput(farmSeeds);
                    System.out.println("FI Seeds");

                    helper.saveFarmInput(farmFert);
                    System.out.println("FI Fertiliser");
                    helper.saveFarmInput(farmplough);
                    System.out.println("Fi Plough");
                    JSONObject objs = new JSONObject();
                    JSONArray inputs = new JSONArray();
                    try {
                        inputs.put(farmFert.getJson());
                        inputs.put(farmplough.getJson());
                        inputs.put(farmSeeds.getJson());

                        objs.put("user_id",farmer.getFarmID());
                        objs.put("page","Farmer Input");
                        objs.put("type","edit");
                        objs.put("section","");
                        objs.put("farm_inputs",inputs);
                        objs.put("imei", IctcCKwUtil.getImei(getBaseContext()));
                        objs.put("version",IctcCKwUtil.getAppVersion());
                        objs.put("battery",IctcCKwUtil.getBatteryLevel(getBaseContext()));
                        helper.insertCCHLog("Farmer",objs.toString(),stime, System.currentTimeMillis());

                    }catch(Exception e ){

                    }


                    myInputs = helper.getIndividualFarmerInputs(farmer.getFarmID());
                }

                try {
                    showDialog("Input Successfully Saved",v+"\nInput Saved View farmer Profile");
                }catch(Exception e){


                }

            }
        });

    }

    public void setValuesForFields(){
        CheckBox seed = (CheckBox) findViewById(R.id.seedBox);
        CheckBox fertilizer = (CheckBox) findViewById(R.id.fertilizercheckbox);
        CheckBox ploughing = (CheckBox) findViewById(R.id.ploughingBox);
        EditText seedbags = (EditText) findViewById(R.id.seedbags);
        EditText fertilizerBags = (EditText) findViewById(R.id.fertilizerbags);
        /**
         *  FarmerInputReceivedWrapper seedsReceived = searchNeeds(farmers, "seeds");
         FarmerInputReceivedWrapper fertReceived = searchNeeds(farmers, "fertiliser");
         FarmerInputReceivedWrapper ploughReceived = searchNeeds(farmers, "plough");
         */
        System.out.println("Seting Values for  : "+myInputs.size());
        for(FarmerInputs fi : myInputs){
            System.out.println("InputName  : "+fi.getName());
            System.out.println("InputQty  : "+fi.getQty());

            if(fi.getName().equalsIgnoreCase("seeds")){
                seedbags.setText(String.valueOf(fi.getQty()));
                if(fi.getQty()>0){
                    seed.setChecked(true);
                }
            }else if(fi.getName().equalsIgnoreCase("fertiliser")){
                fertilizerBags.setText(String.valueOf(fi.getQty()));
                if(fi.getQty()>0){
                    fertilizer.setChecked(true);
                }
            }else if(fi.getName().equalsIgnoreCase("plough")){

                if(fi.getQty()>0){
                    ploughing.setChecked(true);
                }
            }

        }

    }


    public void showDialog(final String title,final String  msg) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(FarmerInputActivty.this);

        builder.setTitle(title);

        builder.setMessage(msg);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



//                    Intent intent = new Intent(MeetingIndexActivity.this, MainActivity.class);
//                    startActivity(intent);

                Intent intent = new Intent(FarmerInputActivty.this, FarmerDetailActivity.class);


//                    if(title.equalsIgnoreCase(AgentVisitUtil.COLLECT_FARM_MEASUREMENT)){
                    intent.putExtra("farmer",farmer);
                    startActivity(intent);
//                    }





                dialog.dismiss();
            }
        });

//        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                dialog.dismiss();
//            }
//        });

        builder.show();
    }


}