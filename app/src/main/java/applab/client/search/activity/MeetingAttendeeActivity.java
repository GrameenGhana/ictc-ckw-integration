package applab.client.search.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;

import java.util.List;

/**
 * Created by skwakwa on 10/30/15.
 */
public class MeetingAttendeeActivity extends AppCompatActivity {
    String title;
    Farmer f;
    int index;
    DatabaseHelper h=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_attendee_activity);

        Bundle extras = getIntent().getExtras();
        int meetingIndex = 0;

        if(extras != null) {
            f= (Farmer) extras.get("farmer");
            title = (String)extras.get("title");
            index= extras.getInt("index");
        }

        h = new DatabaseHelper(getBaseContext());

        List<Farmer>  farmers = h.getFarmers();


    }
}