package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.SimpleTextTextListAdapter;
import applab.client.search.model.Farmer;

/**
 * Created by skwakwa on 9/30/15.
 */
public class CropSelectorActivity extends Activity {

    ListView list = null;
    Farmer farmer;String detail="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_selector_activity);


        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CropSelectorActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("q", ((EditText) mCustomView.findViewById(R.id.bar_search_text)).getText().toString());

                startActivity(intent);
            }
        });

        String type="";



        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type");

            detail = extras.getString("detail");


            farmer = (Farmer) extras.get("farmer");


        } catch (Exception e) {

        }

        mTitleTextView.setText("Select Crop "+detail);


        final String[] titles =
                {"Maize","Cassava","Yam","Rice"};


        final String[] firstLetter = {"M", "C", "Y","R"};
        boolean[] enabled = {true, true, true,true};

        list = (ListView) findViewById(R.id.lst_crop_select);

        SimpleTextTextListAdapter adapter = new SimpleTextTextListAdapter(CropSelectorActivity.this, titles, firstLetter, enabled, getResources().getStringArray(R.array.text_colors));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent= new Intent(CropSelectorActivity.this, MainActivity.class);
                intent.putExtra("farmer",farmer);
                intent.putExtra("SEARCH_CROP",titles[i]);
                intent.putExtra("SEARCH_TITLE",detail);
                startActivity(intent);
            }

        });
    }
}