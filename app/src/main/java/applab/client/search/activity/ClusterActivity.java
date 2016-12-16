package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ClusterAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class ClusterActivity extends BaseActivity {
    private ExpandableListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clusters);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Clusters");
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Clusters");
         IctcCKwUtil.setActionbarUserDetails(this, mCustomView);


        //mActionBar.setCustomView(mCustomView);
       // mActionBar.setDisplayShowCustomEnabled(true);
        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        list = (ExpandableListView) findViewById(R.id.expandableListView);
        list.setGroupIndicator(null);
        final List<String> clusters = new ArrayList<String>();
        clusters.add("Experienced farmers");
        clusters.add("Moderately experienced farmers");
        clusters.add("Farmers on the rise");
        clusters.add("Moving from subsistence");
        clusters.add("No Cluster");
        final Map<String, List<Farmer>> clustersDate = new HashMap<String, List<Farmer>>();
        clustersDate.put(clusters.get(0), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "1"));
        clustersDate.put(clusters.get(1), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "2"));
        clustersDate.put(clusters.get(2), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "3"));
        clustersDate.put(clusters.get(3), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "4"));
        clustersDate.put(clusters.get(4), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, ""));

        System.out.println("Cluster I : " + clustersDate.get(clusters.get(0)).size());
        System.out.println("Cluster II : " + clustersDate.get(clusters.get(1)).size());
        System.out.println("Cluster III : " + clustersDate.get(clusters.get(2)).size());
        String[] names = {"Kojo Antwi", "Yaabrefi Koto", "Okonore Ananse", "Maame Yaa", "Kwaku Ananse", "Nkrabea Adanse", "Ndaase Nsiah"};
        String[] locations = {"Kejebi", "Ejisu", "Kintampo", "Makroase", "Mampong", "Hohoe", "Juapong"};
        String[] mainCrops = {"Maize", "Cassava", "Rice", "Beans", "Maize", "Maize", "Cassava"};
        String[] groups = {"Hohoe", "Lead Farmers", "Early Adopter, Lead Farmers", "Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers"};
        int[] icons = {R.mipmap.ic_cluster_clients, R.mipmap.ic_cluster_clients, R.mipmap.ic_cluster_clients, R.mipmap.ic_cluster_clients, R.mipmap.ic_cluster_clients};
        String[] groupTitles = {"Cluster 1", "Cluster 2", "Cluster 3", "Cluster 4"};


        ClusterAdapter adapter = new ClusterAdapter(ClusterActivity.this, clusters, clustersDate, icons, list);
        list.setAdapter(adapter);
   super.setDetails(helper,"Client","Clustering");

        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClusterActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());
                startActivity(intent);
            }
        });
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                System.out.println("onChild Click: " + i + " - " + i1);
                Intent intent;
                intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
                intent.putExtra("farmer", clustersDate.get(clusters.get(i)).get(i1));
                startActivity(intent);

                return false;
            }


        });

    }
}