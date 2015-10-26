package applab.client.search.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import org.w3c.dom.Text;

/**
 * Created by skwakwa on 10/20/15.
 */
public class FarmerProfileActivity extends BaseActivity {
    DatabaseHelper dbHelper = null;
    Farmer farmer= null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(getBaseContext());

        setContentView(R.layout.activity_farmer_profile);
        super.setDetails(dbHelper,"Farmer","Farmer Profile");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String farmerId = (String) extras.get("farmerId");

            if(null == farmerId ||farmerId.isEmpty()) {
                farmer = (Farmer) extras.get("farmer");
                farmerId = farmer.getFarmID();
            }
            farmer = dbHelper.findFarmer(farmerId);


        }

        TextView t= (TextView) findViewById(R.id.txt_nname);
        t.setText(farmer.getNickname());
        t= (TextView) findViewById(R.id.txt_cluster);
        t.setText(farmer.getCluster());
        t= (TextView) findViewById(R.id.txt_mcrop);
        t.setText(farmer.getMainCrop());
        t= (TextView) findViewById(R.id.txt_aucultivation);
        t.setText(Html.fromHtml(farmer.getLandArea()+"m<sup>2</sup> Perimeter : "+farmer.getSizePlot()+" m "));
        t= (TextView) findViewById(R.id.txt_village);
        t.setText(farmer.getVillage());



        t= (TextView) findViewById(R.id.txt_last_season_yeild_acre);
        t.setText("");
        t= (TextView) findViewById(R.id.txt_this_sea_ypa);
        t.setText("");
        t= (TextView) findViewById(R.id.txt_this_seppt);
        t.setText("");
        t= (TextView) findViewById(R.id.txt_aucultivation);
        t.setText(Html.fromHtml(farmer.getLandArea()+" m<sup>2</sup> Perimeter : "+farmer.getSizePlot()+" m "));
        t= (TextView) findViewById(R.id.txt_village);
        t.setText(farmer.getVillage());



    }


}