package applab.client.search.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TabHost;
import applab.client.search.R;
import applab.client.search.adapters.GridMenuAdapter;

/**
 * Created by skwakwa on 9/15/15.
 */
public class IndividualMeetingByCrop  extends BaseActivity {
    private GridView grid;
    private int[] thumbs;
    private String[] text;
    private GridMenuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_meeting_by_group_activity);
        grid = (GridView) findViewById(R.id.gridView2);
        thumbs = new int[]{R.mipmap.maize, R.mipmap.cassava, R.mipmap.yam, R.mipmap.rice,R.mipmap.soyabean};
        text = new String[]{"Maize", "Cassava", "Yam", "Rice","Soyabeans"};
        adapter = new GridMenuAdapter(IndividualMeetingByCrop.this, thumbs, text);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                }

            }
        });
    }
        /*TabHost tabHost = (TabHost)findViewById(R.id.tabHost2);
        tabHost.setup(this.getLocalActivityManager());
        String []  crops = {"Maize","Cassava","Yam","Rice"};
        Intent gencal  = null;
        for (String crop:crops){
             gencal  = new Intent(IndividualMeetingByCrop.this, AgentMeetings.class);
            gencal.putExtra("type","individual");
            gencal.putExtra("crop",crop);
            TabHost.TabSpec spec = tabHost.newTabSpec(crop).setIndicator(crop).setContent(gencal);
            tabHost.addTab(spec);
        }
        tabHost.setCurrentTab(0);
    }*/
}