package applab.client.search.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import applab.client.search.R;
import applab.client.search.adapters.BulkSmsRecyclerAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.BaseLogActivity;

/**
 * Created by aangjnr on 27/04/2017.
 */

public class BulkTextMessageActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    Button send;
    List<Farmer> mFarmers;
    BulkSmsRecyclerAdapter mAdapter;
    DatabaseHelper mDatabaseHelper;
    private BaseLogActivity baseLogActivity;
    String TAG = BulkTextMessageActivity.class.getSimpleName();

    List<String> numbers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_message);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        baseLogActivity = new BaseLogActivity(this);
        mDatabaseHelper = new DatabaseHelper(this);


        mFarmers = new ArrayList<>();
        numbers = new ArrayList<>();
        mRecycler = (RecyclerView) findViewById(R.id.bulk_messages_recyclerView);


        mFarmers = mDatabaseHelper.getFarmers();

        if(mFarmers != null){


            mAdapter = new BulkSmsRecyclerAdapter(this, mFarmers);
            mAdapter.setHasStableIds(true);
            LinearLayoutManager lll = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecycler.setLayoutManager(lll);
            mRecycler.hasFixedSize();
            mRecycler.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BulkSmsRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    String phone_number = formatPhoneNumber(mFarmers.get(position).getPhoneNumber());

                    if (phone_number != null && !phone_number.isEmpty()) {

                        Log.i(TAG, "Phone number is " + phone_number);


                        view = mRecycler.findViewHolderForAdapterPosition(position).itemView;
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.bulk_message_item_checkbox);
                        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.bulk_message_item_layout);

                        if (!checkBox.isChecked()) {
                            numbers.add(phone_number);
                            layout.setBackgroundColor(ContextCompat.getColor(BulkTextMessageActivity.this, R.color.material_grey_300));

                            checkBox.setChecked(true);

                        } else {

                            numbers.remove(phone_number);
                            layout.setBackgroundColor(ContextCompat.getColor(BulkTextMessageActivity.this, R.color.transparent));
                            checkBox.setChecked(false);
                        }
                    }
                    else{

                        Toast.makeText(BulkTextMessageActivity.this, mFarmers.get(position).getFirstName() + " has invalid phone number!", Toast.LENGTH_SHORT).show();

                    }



                }
            });



        }

        findViewById(R.id.send_bulk_sms_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send bulk message. But before that, check to see if message is not empty and farmer selection is greater than 0.




            }
        });


    }


    String formatPhoneNumber(String number){

        if(number.length() == 10)
            return number;

        else if(number.length() == 9)
            return "0" + number;

        else return null;
    }


    @Override
    protected void onStop() {

        baseLogActivity.setItemValues(mDatabaseHelper, TAG, "Bulk sms","","");
        super.onStop();


    }
}
