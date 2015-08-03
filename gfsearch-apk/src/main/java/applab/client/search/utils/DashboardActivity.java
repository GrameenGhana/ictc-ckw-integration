package applab.client.search.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.DashboardMenuAdapter;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class DashboardActivity extends Activity {
    private GridView grid_menu;
    private TableRow tableRow_communities;
    private TableRow tableRow_farmers;
    private TableRow tableRow_taroWorks;
    private TableRow tableRow_ckw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        grid_menu=(GridView) findViewById(R.id.gridView);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Dashboard");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        String[] titles={"Prices","ID Generator","Clusters","Communities"};
        tableRow_communities=(TableRow) findViewById(R.id.tableRow_communities);
        tableRow_farmers=(TableRow) findViewById(R.id.tableRow_farmers);
        tableRow_taroWorks=(TableRow) findViewById(R.id.tableRow_taroWorks);
        tableRow_ckw=(TableRow) findViewById(R.id.tableRow_ckw);
        int[] images={R.drawable.ic_cedi,R.drawable.ic_id,R.drawable.ic_clusters,R.drawable.ic_community};
        DashboardMenuAdapter adapter=new DashboardMenuAdapter(DashboardActivity.this,images,titles);
        grid_menu.setAdapter(adapter);
        tableRow_communities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CommunityActivity.class);
                startActivity(intent);
            }
        });
        tableRow_farmers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
                startActivity(intent);
            }
        });
        tableRow_ckw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tableRow_taroWorks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                startActivity(launchIntent);
            }
        });
        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                       intent=new Intent(DashboardActivity.this,PricesActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(DashboardActivity.this,ClusterActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(DashboardActivity.this,CommunityActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}