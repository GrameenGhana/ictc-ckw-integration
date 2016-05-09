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
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 9/29/15.
 */
public class ClientActivity extends BaseActivity {
    ListView list = null;
    public static String DETAILS_COMING_SOON = "Details Coming Soon";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_activity);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Clients");
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Clients");

        DatabaseHelper dh = new DatabaseHelper(getBaseContext());
        super.setDetails(dh, "Client", "Client Home");
       // mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);

        IctcCKwUtil.setActionbarUserDetails(this,mCustomView);
        //,, ,
        final String[] titles = {
                "Data Collection",
                "Clusters",
                "Meetings",
                "Farmer Records","Communities"};


        final String[] firstLetter = {"D", "C", "M","F","C"};
        boolean[] enabled = {true, true, true,true,true};

        list = (ListView) findViewById(R.id.lst_supplier_listings);

        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(ClientActivity.this, titles, firstLetter, enabled, getResources().getStringArray(R.array.text_colors));
        if(null == adapter)
            System.out.println("Adapter Null");
        if(null== list)
            System.out.println("List null");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                switch (i){
                    case 0:
                        intent = new Intent(ClientActivity.this, SurveyList.class);

                    break;
                    case 1:
                        intent = new Intent(ClientActivity.this, ClusterActivity.class);

                        break;
                    case 2:
                        intent = new Intent(ClientActivity.this, ScheduledMeetingsActivity.class);

                        break;
                    case 3:
                        intent = new Intent(ClientActivity.this, FarmerByCrop.class);

                        break;
                    case 4:
                        intent = new Intent(ClientActivity.this,CommunityActivity.class);

                        break;

                }
                startActivity(intent);

//                Intent intent = new Intent(ClientActivity.this, BlankActivityView.class);
//                intent.putExtra("title", titles[i]);
//                intent.putExtra("desc", DETAILS_COMING_SOON + "  " + titles[i]);
//                startActivity(intent);
            }
        });


    }
}
