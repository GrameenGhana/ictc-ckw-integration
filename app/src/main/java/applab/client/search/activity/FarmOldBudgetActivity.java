package applab.client.search.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import applab.client.search.R;
import applab.client.search.adapters.ParentListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.FarmerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 11/20/15.
 */
public class FarmOldBudgetActivity extends AppCompatActivity {
    DatabaseHelper helper;

    private ListView list;
    Farmer farmer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_subject_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new DatabaseHelper(getBaseContext());

        list = (ListView) findViewById(R.id.lst_farm_expense);

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
        list = (ListView) findViewById(R.id.lst_farm_expense);


        List<ItemWrapper> wr = FarmerUtil.getFarmerSummaryBudget(farmer);
        ParentListAdapter adapter = new ParentListAdapter(FarmOldBudgetActivity.this,wr);
        list.setAdapter(adapter);
    }
}