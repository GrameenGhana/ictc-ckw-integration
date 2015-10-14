package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.ClusterAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Clusters");

        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ExpandableListView) findViewById(R.id.expandableListView);
        list.setGroupIndicator(null);
        final List<String> clusters = new ArrayList<String>();
        clusters.add("Cluster 1");
        clusters.add("Cluster 2");
        clusters.add("Cluster 3");
        final Map<String, List<Farmer>> clustersDate = new HashMap<String, List<Farmer>>();
        clustersDate.put(clusters.get(0), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "0"));
        clustersDate.put(clusters.get(1), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "1"));
        clustersDate.put(clusters.get(2), helper.getSearchedFarmers(DatabaseHelperConstants.CLUSTER, "2"));

        System.out.println("Cluster I : " + clustersDate.get(clusters.get(0)).size());
        System.out.println("Cluster II : " + clustersDate.get(clusters.get(1)).size());
        System.out.println("Cluster III : " + clustersDate.get(clusters.get(2)).size());
        String[] names = {"Kojo Antwi", "Yaabrefi Koto", "Okonore Ananse", "Maame Yaa", "Kwaku Ananse", "Nkrabea Adanse", "Ndaase Nsiah"};
        String[] locations = {"Kejebi", "Ejisu", "Kintampo", "Makroase", "Mampong", "Hohoe", "Juapong"};
        String[] mainCrops = {"Maize", "Cassava", "Rice", "Beans", "Maize", "Maize", "Cassava"};
        String[] groups = {"Hohoe", "Lead Farmers", "Early Adopter, Lead Farmers", "Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers", "Early Adopters, Hohoe, Lead Farmers"};
        int[] icons = {R.drawable.ic_cluster, R.drawable.ic_cluster, R.drawable.ic_cluster};
        String[] groupTitles = {"Cluster 1", "Cluster 2", "Cluster 3"};
        ClusterAdapter adapter = new ClusterAdapter(ClusterActivity.this, clusters, clustersDate, icons, list);
        list.setAdapter(adapter);

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
//                switch(i1) {
//                    case 0:
//                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
//                        intent.putExtra("name","Kojo Antwi");
//                        intent.putExtra("location","Kejebi");
//                        intent.putExtra("crop","Maize");
//                        startActivity(intent);
//                        break;
//                    case 1:
//                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
//                        intent.putExtra("name","Yaabrefi Koto");
//                        intent.putExtra("location","Ejisu");
//                        intent.putExtra("crop","Cassava");
//                        startActivity(intent);
//                        break;
//                    case 2:
//                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
//                        intent.putExtra("name","Okonore Ananse");
//                        intent.putExtra("location","Kintampo");
//                        intent.putExtra("crop","Rice");
//                        startActivity(intent);
//                        break;
//                    default:
//                        intent = new Intent(ClusterActivity.this, FarmerDetailActivity.class);
//                        intent.putExtra("name","Kojo Antwi");
//                        intent.putExtra("location","Kejebi");
//                        intent.putExtra("crop","Maize");
//                        startActivity(intent);
//                }
                return false;
            }


        });

    }
}