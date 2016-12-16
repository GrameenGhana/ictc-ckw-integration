package applab.client.search.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.PriceListAdapter;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class PricesActivity extends BaseActivity {
    private ExpandableListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        list = (ExpandableListView) findViewById(R.id.expandableListView);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Prices");
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Prices");
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);

        //mActionBar.setCustomView(mCustomView);
        //mActionBar.setDisplayShowCustomEnabled(true);
        String[] maizePrices = {"Ejisu: GHc 100", "Fankyebe: 300", "Kejebi: 200", "Biakoye: 300", "Kintampo: 300"};
        String[] ricePrices = {"Ejisu: 100", "Fankyebe: 300", "Kejebi: 200", "Biakoye: 300", "Kintampo: 300"};
        String[] beansPrices = {"Ejisu: 100", "Fankyebe: 300", "Kejebi: 200", "Biakoye: 300", "Kintampo: 300"};
        int[] icons = {R.mipmap.ic_maize, R.mipmap.ic_rice, R.mipmap.ic_cassava};
        String[] groupTitles = {"Maize", "Rice", "Cassava"};
        PriceListAdapter adapter = new PriceListAdapter(PricesActivity.this, groupTitles, icons, maizePrices, ricePrices, beansPrices, list);
        list.setAdapter(adapter);

        super.setDetails(new DatabaseHelper(getBaseContext()),"Market","Prices");
    }
}