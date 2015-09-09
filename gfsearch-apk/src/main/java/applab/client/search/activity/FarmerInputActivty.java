package applab.client.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.utils.ConnectionUtil;

/**
 * Created by grameen on 9/9/15.
 */
public class FarmerInputActivty extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Farmer farmer = null;
        String farmer_id = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");

                farmer_id = farmer.getId();


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        final String f= farmer_id;



        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
             CheckBox seed = (CheckBox) findViewById(R.id.seedBox);
             CheckBox fertilizer = (CheckBox) findViewById(R.id.fertilizercheckbox);
             CheckBox ploughing = (CheckBox) findViewById(R.id.ploughingBox);
             EditText seedbags = (EditText) findViewById(R.id.seedbags);
             EditText fertilizerBags = (EditText) findViewById(R.id.fertilizerbags);
             String fertilizerInput = null;
             String seedInput = null;
             String ploughingInput = null;
            public void onClick(View view) {
                if (seed.isEnabled())
                    seedInput = seedbags.getText().toString();
                if (fertilizer.isEnabled())
                    fertilizerInput = fertilizerBags.getText().toString();
                if (ploughing.isEnabled())
                    ploughingInput = "Yes";

                String parameterurl = "fid="+f+"&s=" + seedInput + "&f=" + fertilizerInput + "&p=" + ploughingInput;
                System.out.println(parameterurl);
                ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, parameterurl, "fi", "input sent to server");


            }


        });


    }

}