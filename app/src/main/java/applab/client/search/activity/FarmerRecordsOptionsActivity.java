package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import applab.client.search.R;
import applab.client.search.adapters.GridMenuAdapter;

public class FarmerRecordsOptionsActivity extends BaseActivity {

    private GridView grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_records_options);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Farmer Records");
        grid=(GridView) findViewById(R.id.gridView5);
        String[] titles = {"Farmer by Crop", "Farmer by Community", "Farmer by Cluster", "Farmer by Name"};
        int[] images = {R.mipmap.farmer_by_crop, R.mipmap.farmer_by_community,R.mipmap.farmer_by_cluster,  R.mipmap.farmer_by_name};
        GridMenuAdapter adapter = new GridMenuAdapter(FarmerRecordsOptionsActivity.this, images, titles);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                        intent=new Intent(FarmerRecordsOptionsActivity.this,FarmerByCrop.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(FarmerRecordsOptionsActivity.this,CommunityActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(FarmerRecordsOptionsActivity.this,ClusterActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(FarmerRecordsOptionsActivity.this,FarmerActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
