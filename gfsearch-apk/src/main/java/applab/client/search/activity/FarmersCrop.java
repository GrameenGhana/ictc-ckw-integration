package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.FarmersAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerInputs;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import android.text.TextWatcher;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmersCrop extends Activity {
    DatabaseHelper helper = null;
    List<Farmer> farmers =new ArrayList<Farmer>();
String crop="";
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.farm_crop);
        helper = new DatabaseHelper(getBaseContext());
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                crop = (String) extras.get("crop");

                farmers = helper.getSearchedFarmers(DatabaseHelperConstants.MAIN_CROP,crop);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        listView =(ListView)findViewById(R.id.lst_cropping);
        final String[] titles =  new String[farmers.size()];
        final String[] firstLetter =  new String[farmers.size()];
        boolean[] enabled = new boolean[farmers.size()];
        int cnt=0;
        for(Farmer f: farmers){
            titles[cnt]=f.getFullname();
            firstLetter[cnt]=String.valueOf(f.getFullname().charAt(0));
            enabled[cnt]=true;
            cnt++;

        }


        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(FarmersCrop.this, titles, firstLetter, enabled, getResources().getStringArray(R.array.text_colors));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                processOnClickRequest(farmers, i);
            }
        });

        EditText editText = (EditText) findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                int textlength = cs.length();

                final List<Farmer> farmSearchedList = new ArrayList<Farmer>();
                for(Farmer farmer: farmers){


                    if(farmer.getFullname().toLowerCase().contains(cs.toString().toLowerCase())){

                        farmSearchedList.add(farmer);
                    }
                }

                String[] title =  new String[farmSearchedList.size()];
                String[] firstLetters =  new String[farmSearchedList.size()];
                boolean[] enable = new boolean[farmSearchedList.size()];
                int cnt=0;

                for(Farmer f: farmSearchedList){
                    title[cnt]=f.getFullname();
                    firstLetters[cnt]=String.valueOf(f.getFullname().charAt(0));
                    enable[cnt]=true;
                    cnt++;
                }

                System.out.println("farmSearchedList : "+cnt);
                SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(FarmersCrop.this, title, firstLetters, enable, getResources().getStringArray(R.array.text_colors));
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent;
                        processOnClickRequest(farmSearchedList,i);
                    }

                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
    }

    public void processOnClickRequest(List<Farmer> fms,int i){

        Intent intent= new Intent(FarmersCrop.this, FarmerDetailActivity.class);
        intent.putExtra("farmer",fms.get(i));
        startActivity(intent);
    }
}