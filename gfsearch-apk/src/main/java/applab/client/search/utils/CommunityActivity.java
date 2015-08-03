package applab.client.search.utils;

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
import applab.client.search.adapters.CommunitiesAdapter;
import applab.client.search.adapters.DashboardMenuAdapter;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class CommunityActivity extends Activity {
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Communities");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list=(ListView) findViewById(R.id.listView);
        String[] names={"Ejisu","Fankyenebra","Mampong","Mankroase","Hohoe","Ejura"};
        String[] farmers={"10","20","30","40","50","60"};
        CommunitiesAdapter adapter=new CommunitiesAdapter(CommunityActivity.this,names,farmers);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(CommunityActivity.this,FarmerActivity.class);
                startActivity(intent);
            }
        });
    }
}