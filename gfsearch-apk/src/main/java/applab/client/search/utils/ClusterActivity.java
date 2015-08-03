package applab.client.search.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.ClusterAdapter;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class ClusterActivity extends Activity {
    private ExpandableListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clusters);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Clusters");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list=(ExpandableListView) findViewById(R.id.expandableListView);
        String[] names={"Kojo Antwi","Yaabrefi Koto","Okonore Ananse","Maame Yaa","Kwaku Ananse","Nkrabea Adanse","Ndaase Nsiah"};
        String[] locations={"Kejebi","Ejisu","Kintampo","Makroase","Mampong","Hohoe","Juapong"};
        String[] mainCrops={"Maize","Cassava","Rice","Beans","Maize","Maize","Cassava"};
        String[] groups={"Hohoe","Lead Farmers","Early Adopter, Lead Farmers","Hohoe, Lead Farmers","Early Adopters, Hohoe, Lead Farmers","Early Adopters, Hohoe, Lead Farmers","Early Adopters, Hohoe, Lead Farmers"};
        int[] icons={R.drawable.ic_cluster,R.drawable.ic_cluster,R.drawable.ic_cluster};
        String[] groupTitles={"Cluster 1","Cluster 2","Cluster 3"};
        ClusterAdapter adapter=new ClusterAdapter(ClusterActivity.this,groupTitles,icons,names,locations,mainCrops,groups,list);
        list.setAdapter(adapter);
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent;
                switch(i1) {
                    case 0:
                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
                        intent.putExtra("name","Kojo Antwi");
                        intent.putExtra("location","Kejebi");
                        intent.putExtra("crop","Maize");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
                        intent.putExtra("name","Yaabrefi Koto");
                        intent.putExtra("location","Ejisu");
                        intent.putExtra("crop","Cassava");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
                        intent.putExtra("name","Okonore Ananse");
                        intent.putExtra("location","Kintampo");
                        intent.putExtra("crop","Rice");
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
                        intent.putExtra("name","Kojo Antwi");
                        intent.putExtra("location","Kejebi");
                        intent.putExtra("crop","Maize");
                        startActivity(intent);
                }
                return false;
            }


        });

    }
}