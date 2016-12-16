package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.FarmersAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmerActivity extends BaseActivity {
    private ListView list;
    List<Farmer> myFarmers = null;

    DatabaseHelper helper;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);
        title=(TextView) findViewById(R.id.title);
        title.setText("Select farmer to view details");
        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FarmerActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        mTitleTextView.setText("Farmers");
        mActionBar.setTitle("Farmers");
       // mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);

        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        String type = "farmer";
        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type");
        } catch (Exception e) {

        }

        if (type.equalsIgnoreCase("farmer"))
            myFarmers = helper.getFarmers();
        else if (type.equalsIgnoreCase("comm")) {
            String community = extras.getString("name");
            //Community changed to village 
            myFarmers = helper.getSearchedFarmers(DatabaseHelperConstants.VILLAGE, community);
        } else if (type.equalsIgnoreCase("search")) {
            String q = extras.getString("q");
            myFarmers = helper.searchFarmer(q);
        }

        super.setDetails(helper,"Farmer","Farm Mapping");
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

        list = (ListView) findViewById(R.id.listView);
        FarmersAdapter adapter = new FarmersAdapter(FarmerActivity.this, names, locations, mainCrops, groups,myFarmers);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;

                intent = new Intent(FarmerActivity.this, FarmerDetailActivity.class);
                intent.putExtra("farmer", myFarmers.get(i));
//                System.out.println("Farmer : "+myFarmers.get(i).getFirstName());
                startActivity(intent);

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
                for(Farmer farmer: myFarmers){


                    if(farmer.getFullname().toLowerCase().contains(cs.toString().toLowerCase())){

                        farmSearchedList.add(farmer);
                    }
                }

                String[] title =  new String[farmSearchedList.size()];
                String[] firstLetters =  new String[farmSearchedList.size()];
                boolean[] enable = new boolean[farmSearchedList.size()];
                int cnt=0;
                final List<Farmer> fList = new ArrayList<Farmer>();

                for(Farmer f: farmSearchedList){
                    title[cnt]=f.getFullname();
                    firstLetters[cnt]=String.valueOf(f.getFullname().charAt(0));
                    enable[cnt]=true;
                    cnt++;
                    fList.add(f);
                }

                System.out.println("farmSearchedList : "+cnt);
                FarmersAdapter adapter = new FarmersAdapter(FarmerActivity.this, title, firstLetters, title, getResources().getStringArray(R.array.text_colors),farmSearchedList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        processOnClickRequest(fList,i);

                    }

                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
    }
    public void processOnClickRequest(List<Farmer> fms,int i){

        Intent intent= new Intent(FarmerActivity.this, FarmerDetailActivity.class);
        Farmer f = fms.get(i);

        System.out.println("List Po Fullnamed : "+f.getFullname()+"{"+f.getFarmID()+"}");
        System.out.println("List Po Fullnamed : "+f.getFullname()+"{"+f.getId()+"}");
        intent.putExtra("farmer",f);
        intent.putExtra("farmerId",f.getFarmID());
        startActivity(intent);
    }
}