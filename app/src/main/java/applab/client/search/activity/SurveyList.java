package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.adapters.ListWithThumbnailAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 9/29/15.
 */
public class SurveyList extends BaseActivity {
    GridView list=null;
    public static String DETAILS_COMING_SOON="Details Coming Soon";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_activity);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Data Collection");
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Data Collection");

        //mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
        //, , Financial Institutions
        final String []   titles = {
                "A1: REGISTER FARMER",
                "A2: PROFILE FARMER",
                "B1: PREVIOUS PERFORMANCE (PRODUCTION)",
                "B2: PREVIOUS PERFORMANCE (POST HARVEST",
                "B3: FARM CREDIT (PREVIOUS)",
                "C1: FARM PLAN (UPCOMING PRODUCTION)",

                "C2: FARM PLAN (UPCOMING POST-HARVEST)",
                "C3: FARM CREDIT (PLAN)",
                "D1: CONDUCT CROP ASSESSMENT",
                "E1: UPDATE CURRENT PLAN (AFTER PLANTING)",
                "E2: UPDATE CURRENT PLAN (AFTER HARVEST)",
                "E3: UPDATE CURRENT PLAN (AFTER PROCESSING)",
                "E4: UPDATE CURRENT PLAN (AFTER SELLING)",
                "E5: FARM CREDIT (UPDATE)"

        };
        String [] captions={"A1","A2","B1","B2","B3","C1","C2","C3","D1","E1","E2","E3","E4","E5"};

        String [] fLetters  = new String[titles.length];
        boolean [] enabled=new boolean[titles.length];
        String abcd="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i=0;i< titles.length;i++){
//            fLetters[i] = String.valueOf(abcd.charAt(i));
            enabled[i]=true;
        }


        final String []   firstLetter = captions;
//       {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};

        list =(GridView)findViewById(R.id.gridView4);
        int[] thumbs=new int[titles.length];


        for(int i=0;i<titles.length;i++){
            thumbs[i]=R.mipmap.taroworks;
        }

        ListWithThumbnailAdapter adapter = new ListWithThumbnailAdapter(SurveyList.this, titles,thumbs);
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