package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.adapters.CommunitiesAdapter;
import applab.client.search.model.Community;
import applab.client.search.model.CommunityCounterWrapper;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class CommunityActivity extends BaseActivity {
    private ListView list;

    DatabaseHelper helper;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Communities");
        LayoutInflater mInflater = LayoutInflater.from(this);
        title=(TextView) findViewById(R.id.title);
        title.setText("Select community to view farmers");
        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Communities");
       IctcCKwUtil.setActionbarUserDetails(this, mCustomView);

        helper = new DatabaseHelper(getBaseContext());
       // mActionBar.setCustomView(mCustomView);
        //mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.listView);
        final List<Community> comWr = helper.farmerCountByCommunityGroupName();
       // List<Community> names = new String[comWr.size()];
        String[] farmers = new String[comWr.size()];
        int cnt = 0;
       /* for (CommunityCounterWrapper wr : comWr) {
            names[cnt] = wr.getCommunity();
            System.out.println("Name of Community: "+names[cnt]);
            farmers[cnt] = String.valueOf(wr.getCounter());
            cnt++;
        }*/
        System.out.println(comWr.size());
        super.setDetails(helper,"Client","Community");
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });
        final CommunitiesAdapter adapter = new CommunitiesAdapter(CommunityActivity.this, comWr);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CommunityActivity.this, FarmerActivity.class);
                intent.putExtra("type", "comm");
                intent.putExtra("name", comWr.get(i).getName());
                startActivity(intent);
            }
        });

        EditText search=(EditText) findViewById(R.id.editText);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}