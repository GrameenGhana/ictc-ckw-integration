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
import applab.client.search.utils.IctcCKwUtil;

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
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        //, , Financial Institutions
        final String []   titles = {
                "FARMER REGISTRATION",
                "PROFILING",
                "BASELINE PRODUCTION",
                "BASELINE POST HARVEST",
                "FMP PRODUCTION",
                "FMP POST-HARVEST",

                "FIELD CROP ASSESSMENT",
                "FMP PRODUCTION UPDATE 1",
                "FMP PRODUCTION UPDATE 2",
                "FMP POST HARVEST UPDATE 1",
                "FMP POST HARVEST UPDATE 2"
        };
        String [] captions={"A","B","C","D","E","F","G","H1","H2","I1","I2"};

        String [] fLetters  = new String[titles.length];
        boolean [] enabled=new boolean[titles.length];
        String abcd="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i=0;i< titles.length;i++){
//            fLetters[i] = String.valueOf(abcd.charAt(i));
            enabled[i]=true;
        }


        final String []   firstLetter = captions;
//       {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};

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
        super.setDetails(new DatabaseHelper(getBaseContext()),"Data Collection","Data Collection    ");


    }

}