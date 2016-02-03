package applab.client.search.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ParentListAdapter;
import applab.client.search.adapters.ProfileViewAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 11/20/15.
 */
public class FarmBudgetActivity extends Activity {
    DatabaseHelper helper;

    private ListView list;
    Farmer farmer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_subject_new_activity);
        helper = new DatabaseHelper(getBaseContext());

        TableLayout stk = (TableLayout) findViewById(R.id.tb_farm_budget);


        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");
                farmer = helper.findFarmer(farmer.getFarmID());
            }
        }catch (Exception e){

        }

        final List<String> sections = new ArrayList<String>();
        sections.add("Farm Details");
        sections.add("Agric Inputs");
        sections.add("Labour Cost");
        sections.add("Profit or Loss");


        List<ItemWrapper> wr = FarmerUtil.getFarmerSummaryBudget(farmer);

//        ParentListAdapter adapter = new ParentListAdapter(FarmBudgetActivity.this,wr);
//        list.setAdapter(adapter);
        processTableData(stk,wr);
    }

    public void processTableData(TableLayout stk,List<ItemWrapper> wr){




    int cnt=0;

        for(ItemWrapper item : wr){
            cnt++;
            TableRow tbrow0 = new TableRow(this);
            TextView key = new TextView(this);
            key.setTextSize(18);
            TextView val = new TextView(this);
            TextView secVal = new TextView(this);
            TextView terVal = new TextView(this);
            key.setLineSpacing(2,1);
            key.setTextSize(18);
            val.setTextSize(18);
            secVal.setTextSize(18);
            terVal.setTextSize(18);
            if(item.getKey().equalsIgnoreCase("")){
                key.setText(String.valueOf(item.getValue()));
                key.setTextColor(Color.parseColor("#ff0e4918"));
                key.setTypeface(null, Typeface.BOLD);
                val.setText("FMP");
                val.setTypeface(null, Typeface.BOLD);
                secVal.setText("BL");
                secVal.setTypeface(null, Typeface.BOLD);
                secVal.setText("UP");
                secVal.setTypeface(null, Typeface.BOLD);
            }else{
                key.setText(item.getKey());
                val.setText(String.valueOf(item.getValue()));
                secVal.setText(String.valueOf(item.getSecValue()));
                terVal.setText(String.valueOf(item.getTerValue()));
            }
            tbrow0.addView(key);
            tbrow0.addView(val);
            tbrow0.addView(secVal);
            tbrow0.addView(terVal);
            stk.addView(tbrow0);
            View v = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            v.setLayoutParams(params);
            v.setBackgroundColor(getResources().getColor(android.R.color.white));
            stk.addView(v);

        }
    }
}