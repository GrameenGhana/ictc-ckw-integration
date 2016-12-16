package applab.client.search.activity;

import android.support.v7.app.ActionBar;
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
import applab.client.search.adapters.SimpleTextListAdapterWithThumbnailAdapter;
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
    private String type;
    private String[] firstLetter;
    private boolean[] enabled;
    private int[] thumbs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.general_agent_calendar_activity);
        list = (ListView) findViewById(R.id.lst_agent_lst_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = (String) extras.get("type");

            }

        final String []   titles = AgentVisitUtil.getMeetingTitles(true,type);
        if(type.equalsIgnoreCase("Group")){
            firstLetter = new String []{"8","9","10","11"};
            enabled=new boolean[]{true,true,true,true};
            thumbs=new int[]{R.mipmap.planting,R.mipmap.fertilizer,R.mipmap.fertilizer,R.mipmap.harvesting};
        }else if (type.equalsIgnoreCase("individual")){
            firstLetter = new String []{"1","2","3","4","5","6","7"};
            enabled=new boolean[]{true,true,true,true,true,true,true};
            thumbs=new int[]{R.mipmap.pre_visit,R.mipmap.register,R.mipmap.planning,R.mipmap.field_measurement,R.mipmap.assessment,R.mipmap.fertilizer,R.mipmap.plan_update};
        }

        //final String []   firstLetter = {"1","2","3","4"};
        //boolean [] enabled={true,true,true,true};


        SimpleTextListAdapterWithThumbnailAdapter adapter = new SimpleTextListAdapterWithThumbnailAdapter(GeneralAgentCalendarActivity.this, titles,firstLetter,enabled, getResources().getStringArray(R.array.text_colors),thumbs);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if (titles[i].toLowerCase().contains("visit") ){
                    System.out.println("Individual item");
                    Intent intent = new Intent(getApplicationContext(), FarmerActivitySelectFarmer.class);
                    intent.putExtra("mi",Integer.parseInt( firstLetter[i]));
                    intent.putExtra("type","MI");
                    intent.putExtra("detail",titles[i]);
                    intent.putExtra("mt", titles[i]);
                    intent.putExtra("mid", "1");
                    intent.putExtra("mtype", type);
                    intent.putExtra("atd", 0);
                    intent.putExtra("farmerId", "");
                    startActivity(intent);
                }else {
                    System.out.println("Other Item");
                    Intent intent = new Intent(getApplicationContext(), MeetingIndexActivity.class);
                    intent.putExtra("mi",Integer.parseInt(firstLetter[i]));
                    intent.putExtra("mt", titles[i]);
                    intent.putExtra("mid", "1");
                    intent.putExtra("mtype", type);
                    intent.putExtra("atd", 0);
                    intent.putExtra("farmerId", "");
                    startActivity(intent);
                }
            }
        });
helper = new DatabaseHelper(getBaseContext());
        super.setDetails(helper,"Client","Agent Calendar");
    }






}