package applab.client.search.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import applab.client.search.R;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmerDetailActivity extends Activity {
    private TextView textViewName;
    private TextView textViewMainCrop;
    private TextView textViewProportion;
    private TextView textViewPercentageSold;
    private String name;
    private String mainCrop;
    private String location;
    private TextView textViewLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_summary);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Farmer Details");
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                name = extras.getString("name");
                mainCrop=extras.getString("crop");
                location=extras.getString("location");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        textViewName=(TextView) findViewById(R.id.textView_name);
        textViewName.setText(name);
        textViewMainCrop=(TextView) findViewById(R.id.textView_mainCrop);
        textViewMainCrop.setText(mainCrop);
        textViewProportion=(TextView) findViewById(R.id.textView_proportion);
        textViewLocation=(TextView) findViewById(R.id.textView_location);
        textViewLocation.setText(location);
        textViewPercentageSold=(TextView) findViewById(R.id.textView_percentageSold);
        if(mainCrop.equalsIgnoreCase("Rice")){
            Drawable rice = FarmerDetailActivity.this.getResources().getDrawable( R.drawable.ic_rice );
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(rice,null,null,null);
            textViewPercentageSold.setText(">50%");
        }else if(mainCrop.equalsIgnoreCase("Cassava")){
            Drawable cassava = FarmerDetailActivity.this.getResources().getDrawable( R.drawable.ic_cassava);
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(cassava,null,null,null);
            textViewPercentageSold.setText(">70%");
        }else if(mainCrop.equalsIgnoreCase("Maize")){
            Drawable maize = FarmerDetailActivity.this.getResources().getDrawable( R.drawable.ic_maize);
            textViewProportion.setCompoundDrawablesWithIntrinsicBounds(maize,null,null,null);
            textViewPercentageSold.setText(">80%");
        }

    }
}