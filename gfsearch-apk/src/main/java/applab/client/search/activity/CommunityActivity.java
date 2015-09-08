package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.CommunitiesAdapter;
import applab.client.search.model.CommunityCounterWrapper;
import applab.client.search.storage.DatabaseHelper;

import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class CommunityActivity extends Activity {
    private ListView list;

    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Communities");

        helper = new DatabaseHelper(getBaseContext());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.listView);
        final List<CommunityCounterWrapper> comWr = helper.farmerCountByCommunityGroup();
        String[] names = new String[comWr.size()];
        String[] farmers = new String[comWr.size()];
        int cnt = 0;
        for (CommunityCounterWrapper wr : comWr) {
            names[cnt] = wr.getCommunity();
            farmers[cnt] = String.valueOf(wr.getCounter());
            cnt++;
        }

        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        CommunitiesAdapter adapter = new CommunitiesAdapter(CommunityActivity.this, names, farmers);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CommunityActivity.this, FarmerActivity.class);
                intent.putExtra("type", "comm");
                intent.putExtra("name", comWr.get(i).getCommunity());
                startActivity(intent);
            }
        });
    }
}