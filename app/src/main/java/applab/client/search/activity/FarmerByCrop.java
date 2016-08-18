package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.adapters.GridMenuAdapter;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.IctcCKwUtil;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmerByCrop   extends BaseActivity{
    private GridView grid;
    private String[] text;
    private int[] thumbs;
    private GridMenuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_by_crop);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Farmer By Crop");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        grid = (GridView) findViewById(R.id.gridView3);
        thumbs = new int[]{R.mipmap.maize, R.mipmap.rice, R.mipmap.soyabean, R.mipmap.cassava,R.mipmap.yam};
        text = new String[]{"Maize","Rice","Soyabean", "Cassava", "Yam"};
        adapter = new GridMenuAdapter(FarmerByCrop.this, thumbs, text);
        grid.setAdapter(adapter);
        //TabHost tabHost = (TabHost)findViewById(R.id.tab_farmer_by_crop);
        //tabHost.setup(this.getLocalActivityManager());
       // Intent gencal  = null;
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(FarmerByCrop.this, FarmersCrop.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(FarmerByCrop.this, FarmersCrop.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(FarmerByCrop.this, FarmersCrop.class);
                        intent.putExtra("type", "individual");
                        //intent.putExtra("crop", text[i]);
                        intent.putExtra("crop","Soya");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(FarmerByCrop.this, FarmersCrop.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(FarmerByCrop.this, FarmersCrop.class);
                        intent.putExtra("type", "individual");
                        intent.putExtra("crop", text[i]);
                        startActivity(intent);
                        break;
                }
            }
        });


        DatabaseHelper dh = new DatabaseHelper(getBaseContext());
        super.setDetails(dh,"Client","Farmer By Crop");
       // mActionBar.setCustomView(mCustomView);


//        myInputs =  dbHelper.getIndividualFarmerInputs(farmer.getFarmID());


    }
}