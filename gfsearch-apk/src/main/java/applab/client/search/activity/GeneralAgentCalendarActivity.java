package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.ListCheckboxAdapter;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.AgentVisitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 0540106352
 * Created by skwakwa on 9/2/15.
 */
public class GeneralAgentCalendarActivity extends BaseActivity {

    private ListView list;

    DatabaseHelper helper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_agent_calendar_activity);



//        helper = new DatabaseHelper(getBaseContext());
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
        list = (ListView) findViewById(R.id.lst_agent_lst_view);

        final String []   titles = AgentVisitUtil.getMeetingTitles(true);


        final String []   firstLetter = {"1","2","3","4","5","6","7","8","9","10","11"};
        boolean [] enabled={true,true,true,true,true,true,true,true,true,true,true};



        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(GeneralAgentCalendarActivity.this, titles,firstLetter,enabled, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (titles[i].toLowerCase().contains("visit") ){
                    System.out.println("Individual item");
                    Intent intent = new Intent(GeneralAgentCalendarActivity.this, FarmerActivitySelectFarmer.class);
                    intent.putExtra("mi", (i + 1));
                    intent.putExtra("type","MI");
                    intent.putExtra("detail",titles[i]);
                    intent.putExtra("mt", titles[i]);
                    intent.putExtra("mid", "1");
                    intent.putExtra("mtype", "");
                    intent.putExtra("atd", 0);
                    intent.putExtra("farmerId", "");
                    startActivity(intent);
                }else {
                    System.out.println("Other Item");
                    Intent intent = new Intent(GeneralAgentCalendarActivity.this, MeetingIndexActivity.class);
                    intent.putExtra("mi", (i + 1));
                    intent.putExtra("mt", titles[i]);
                    intent.putExtra("mid", "1");
                    intent.putExtra("mtype", "");
                    intent.putExtra("atd", 0);
                    intent.putExtra("farmerId", "");
                    startActivity(intent);
                }}
        });
helper = new DatabaseHelper(getBaseContext());
        super.setDetails(new DatabaseHelper(getBaseContext()),"Client","Agent Calendar");
    }
}