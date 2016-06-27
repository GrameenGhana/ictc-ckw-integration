package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;


/**
 * Created by skwakwa on 9/29/15.
 */
public class SupplierActivity extends BaseActivity {
    GridView list=null;
public static String DETAILS_COMING_SOON="Details Coming Soon";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_activity);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);


        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Suppliers");
        IctcCKwUtil.setActionbarUserDetails(this, mCustomView);

        final String []   titles = {
                "Input suppliers",
                "Transport",
                "Financial Institutions",};


        final String []   firstLetter = {"I","T","F"};
        boolean [] enabled={false,false,false};

        list =(GridView)findViewById(R.id.gridView4);

        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(SupplierActivity.this, titles,firstLetter,enabled, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);
        super.setDetails(new DatabaseHelper(getBaseContext()),"Supplier","Supplier");
    }

}