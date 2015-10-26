package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.storage.DatabaseHelper;

/**
 * Created by skwakwa on 9/29/15.
 */
public class SurveyList extends BaseActivity {
    ListView list=null;
    public static String DETAILS_COMING_SOON="Details Coming Soon";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_activity);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Data Collection");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        //, , Financial Institutions
        final String []   titles = {
                "Biodata",
                "Farmer Registration",
                "FMP Production New",
                "Technical Needs",
                "FMP Post Harvest New",
                "FMP Production Update New",
                "FMP PostHarvest Update New 2"};


        final String []   firstLetter = {"1","2","3","4","5","6","7"};
        boolean [] enabled={true,true,true,true,true,true,true};

        list =(ListView)findViewById(R.id.lst_supplier_listings);

        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(SurveyList.this, titles,firstLetter,enabled, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                    launchIntent.putExtra("survey_name",titles[i]);
                    startActivity(launchIntent);
                }catch(Exception e){
                    Toast.makeText(view.getContext(),"Tarowokrs not installed",Toast.LENGTH_LONG);

                }

            }
        });
        super.setDetails(new DatabaseHelper(getBaseContext()),"Supplier","Supplier");


    }

}