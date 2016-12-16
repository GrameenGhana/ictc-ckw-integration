package applab.client.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 9/29/15.
 */
public class MarketActivity extends BaseActivity {
    GridView list = null;
    public static String DETAILS_COMING_SOON = "Details Coming Soon";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_activity);


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Suppliers");
        /*LayoutInflater mInflater = LayoutInflater.from(this);
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Suppliers");

        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);*/
        //mActionBar.setCustomView(mCustomView);
        //mActionBar.setDisplayShowCustomEnabled(true);
        //, , Financial Institutions
        final String[] titles = {
                "Buyers - Specific Buyers",
                "Market Near You",
                "Detailed Prices",};


        final String[] firstLetter = {"B", "M", "D"};
        boolean[] enabled = {false, false, false};

        list = (GridView) findViewById(R.id.gridView4);

        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(MarketActivity.this, titles, firstLetter, enabled, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MarketActivity.this, BlankActivityView.class);
                intent.putExtra("title", titles[i]);
                intent.putExtra("desc", DETAILS_COMING_SOON + "  " + titles[i]);
                startActivity(intent);
            }
        });
        super.setDetails(new DatabaseHelper(getBaseContext()),"Market","Market");

    }
}
