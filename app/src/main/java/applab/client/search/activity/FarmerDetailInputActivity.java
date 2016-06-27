package applab.client.search.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skwakwa on 9/23/15.
 */
public class FarmerDetailInputActivity extends BaseActivity {
    DatabaseHelper helper = null;
    List<FarmerInputs> myInputs =new ArrayList<FarmerInputs>();
    private ExpandableListView list;


    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        String farmer_id = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_detail_input_activity);

        helper = new DatabaseHelper(getBaseContext());
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String farmer = (String) extras.get("farmer");


                myInputs =  helper.getIndividualFarmerInputs(farmer);

                System.out.println("MyInputs :"+myInputs.size());
                setValuesForFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String f = farmer_id;
        super.setDetails(helper,"Farmer","Farmer Details Input");

    }


    public void setValuesForFields(){

        /**
         *  FarmerInputReceivedWrapper seedsReceived = searchNeeds(farmers, "seeds");
         FarmerInputReceivedWrapper fertReceived = searchNeeds(farmers, "fertiliser");
         FarmerInputReceivedWrapper ploughReceived = searchNeeds(farmers, "plough");
         */
        String  [] details= new String[myInputs.size()];
        String [] firstLetters= new String[myInputs.size()];
        boolean  [] enabled = new boolean[myInputs.size()];
        Arrays.fill(enabled,true);

        int cnt=0;
        for(FarmerInputs fi : myInputs){
            String title="";
            firstLetters[cnt]=String.valueOf(fi.getQty());
            details[cnt]= fi.getName();
        }
        listView = (ListView)findViewById(R.id.lst_farmer_inputs);

        ListAdapter adapter = new SimpleTextTextListAdapter(FarmerDetailInputActivity.this, details,firstLetters ,enabled,getResources().getStringArray(R.array.text_colors));
        listView.setAdapter(adapter);


    }
}