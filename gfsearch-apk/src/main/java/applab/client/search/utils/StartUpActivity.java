package applab.client.search.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Payload;
import applab.client.search.services.LoginTask;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.storage.StorageManager;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.synchronization.SubmitListener;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Software Developer on 29/07/2015.
 */
public class StartUpActivity extends Activity {
    private Button login_button;
    LoginTask mAuthTask = null;
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Login");

        databaseHelper = new DatabaseHelper(getBaseContext());

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        final EditText userEdit = (EditText) findViewById(R.id.edit_us_name);
        final EditText passEdit = (EditText) findViewById(R.id.user_pwd);
        final TextView error = (TextView) findViewById(R.id.text_log_error);
        login_button = (Button) findViewById(R.id.main_button_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (userEdit.getText().toString().isEmpty() || passEdit.getText().toString().isEmpty()) {
//                    error.setText("Username and Password can't be empty");
                    Toast.makeText(getBaseContext(), "Username and Password can't be empty", Toast.LENGTH_LONG).show();
//                    error.setBackgroundColor(R.);
//                    error.setVisibility(TextView.VISIBLE);

                } else {
                    Intent intent = new Intent(StartUpActivity.this, DashboardActivity.class);
                    databaseHelper = new DatabaseHelper(getBaseContext());
                    /**
                     * saveFarmer(String firstName, String lastName, String nickname, String community, String village, String district,
                     String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                     String education, String cluster, String farmID)
                     */
//helper.saveFarmer("Kofi","Adarkwa","kadar","Kumasi","Ada","da","region","age","M","Single","2","2","shs","2","8i8i8i9i");

//                myFarmers.add(new Farmer(farmer.getString("fname"),farmer.getString("lName"),farmer.getString("nickname"),farmer.getString("community"),farmer.getString("village"),farmer.getString("district"),farmer.getString("region"),farmer.getString("age"),farmer.getString("gender"),farmer.getString("ms"),farmer.getString("noc"),farmer.getString("nod"),farmer.getString("edu"),farmer.getString("cluster"),farmer.getString("id")));

//                if (databaseHelper.farmerCount() < 1) {

                    getData(intent,"");
//            }
                    startActivity(intent);
                }
            }
        });
    }


    public void attemptLogin() {
//        try {
//            Payload payload = new Payload();
//            mAuthTask = new LoginTask(this);
//            mAuthTask.setLoginListener(this);
//            mAuthTask.execute(payload);
//        }catch (Exception e){
//            System.out.println("ICTC : "+e.getLocalizedMessage());
//        }


    }


    public void getData(final Intent intent, final String queryString,String msg ) {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";

        Toast.makeText(getBaseContext(),msg,
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL + "action=login";
                    if(queryString.length()>0)
                        url+="&"+queryString;

                    JSONObject j = new JSONObject();
                    System.out.println("URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    System.out.println("After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    System.out.println("Done");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                        serverResponse += line;
                    }

//                    System.out.println("Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse,"");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg,String type) {

                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String aResponse = msg.getData().getString("message");

                        if ((null != aResponse)) {

                            ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                            // test.setText(aResponse);

                            try {
                                JSONObject resp = new JSONObject(aResponse);

                                JSONArray farmers = (JSONArray) resp.get("farmer");

                                databaseHelper.resetFarmer();
                                int farmersCnt = 0;
                                for (int i = 0; i < farmers.length(); i++) {
                                    JSONObject farmer = farmers.getJSONObject(i);
//                                    System.out.println("Farmer Name  : " + farmer.getString("lname"));
//                                    cluster1List.add((String) j.get("farmer"));
//
                                    //   String []  keys =  {"fname","lname","nickname","community","village","district","ms","age","gender","ms","noc","nod","edu","cluster","id","maincrop"};
                                    String[] keys
                                            = {

                                            "fname", "lname", "nickname", "communy", "village", "district", "region", "age", "gender", "ms", "noc", "nod", "edu", "cluster", "id",
                                            "sizeplot", "labour", "date_land_ident", "loc_land", "target_area",
                                            "exp_price_ton", "variety", "target_nxt_season", "techneed1", "techneed2",
                                            "fbo",
                                            "landarea", "date_plant", "date_man_weed", "pos_contact", "mon_sell_start", "mon_fin_pro_sold",
                                            "maincrop"
                                    };
                                    String[] vals = new String[keys.length];
                                    int cnt = 0;
                                    for (String key : keys) {
                                        try {
                                            vals[cnt] = farmer.getString(key);
                                        } catch (Exception e) {
                                            vals[cnt] = "";
                                        }
                                        cnt++;
                                    }


                                    /**
                                     *
                                     String firstName, String lastName, String nickname, String community, String village, String district,
                                     String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                                     String education, String cluster, String farmID,
                                     String SIZE_PLOT,
                                     String LABOUR,
                                     String DATE_OF_LAND_IDENTIFICATION ,
                                     String LOCATION_LAND,
                                     String TARGET_AREA,
                                     String EXPECTED_PRICE_TON ,
                                     String VARIETY,
                                     String TARGET_NEXT_SEASON ,
                                     String TECH_NEEDS_I,
                                     String TECH_NEEDS_II ,
                                     String FARMER_BASE_ORG,

                                     String PLANTING_DATE,
                                     String LAND_AREA ,
                                     String DATE_MANUAL_WEEDING ,
                                     String POS_CONTACT ,
                                     String MONTH_SELLING_STARTS ,
                                     String MONTH_FINAL_PRODUCT_SOLD

                                     */

                                    databaseHelper.saveFarmer(vals[0], vals[1], vals[2], vals[3],
                                            vals[4], vals[5], vals[6], vals[7], vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14],
                                            vals[15], vals[16], vals[17], vals[18],
                                            vals[19], vals[20], vals[21], vals[22], vals[23], vals[24], vals[25], vals[26], vals[27], vals[28], vals[29], vals[30], vals[31], vals[32]);

//                                    farmersCnt++;
                                }


                                //sending data into cluster 1
                                /**  Intent cluster1Intent = new Intent(MainActivity.this, Cluster1.class);
                                 Bundle b = new Bundle();
                                 b.putStringArrayList("cluster1", (ArrayList<String>) cluster1List);
                                 cluster1Intent.putExtra("clusterbundle", b);
                                 startActivity(cluster1Intent);

                                 //sending data into cluster 2
                                 Intent cluster2Intent = new Intent(MainActivity.this, Cluster2.class);
                                 Bundle b2 = new Bundle();
                                 b2.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster2Intent.putExtra("clusterbundle",b2);
                                 startActivity(cluster2Intent);


                                 //sending data into cluster 3
                                 Intent cluster3Intent = new Intent(MainActivity.this, Cluster3.class);
                                 Bundle b3 = new Bundle();
                                 b3.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster3Intent.putExtra("clusterbundle",b3);
                                 startActivity(cluster3Intent);
                                 **/


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Data Recieved: ",

                                    Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {

                            //ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Not Got Response From Server.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }


}