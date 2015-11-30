package applab.client.search.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.services.LoginTask;
import applab.client.search.services.TrackerService;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import applab.client.search.utils.AgentVisitUtil;
import applab.client.search.utils.ConnectionUtil;
import applab.client.search.utils.IctcCKwUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Software Developer on 29/07/2015.
 */
public class StartUpActivity extends BaseActivity {
    private Button login_button;
    LoginTask mAuthTask = null;
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar mActionBar = getActionBar();
//        mActionBar.setDisplayShowHomeEnabled(false);
//        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//
//        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
//        mTitleTextView.setText("Login");

        databaseHelper = new DatabaseHelper(getBaseContext());
        super.setDetails(databaseHelper,"Dashboard","Startup");


        Intent service = new Intent(this, TrackerService.class);
        Bundle tb = new Bundle();
        tb.putBoolean("backgroundData", true);
        service.putExtras(tb);
        this.startService(service);
        databaseHelper.alterFarmerTable();
        if(databaseHelper.farmerCount()>0)
        {
            if(databaseHelper.getMeetingSettingCount()==0){

                AgentVisitUtil.setMeetingSettings(databaseHelper);
            }
            Intent intent = new Intent(StartUpActivity.this, DashboardActivity.class);
            startActivity(intent);
        }



//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);


        final EditText userEdit = (EditText) findViewById(R.id.edit_us_name);
        final EditText passEdit = (EditText) findViewById(R.id.user_pwd);
        final TextView error = (TextView) findViewById(R.id.text_log_error);
        login_button = (Button) findViewById(R.id.main_button_login); try {
            Bundle b = getIntent().getExtras();
            String err = b.getString("err");
            String usname = b.getString("us");
            if(!err.isEmpty()){
                TextView tv =    ((TextView) findViewById(R.id.txt_pass_response));
                tv.setText("Wrong Username or Password Please Try Again");
                userEdit.setText(usname);
            }
        }catch(Exception e){


        }
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (userEdit.getText().toString().isEmpty() || passEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Username and Password can't be empty", Toast.LENGTH_LONG).show();
               } else {
                    Intent intent = new Intent(StartUpActivity.this, DashboardActivity.class);
                    databaseHelper = new DatabaseHelper(getBaseContext());

                    ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");

                    startActivity(intent);
//                    ConnectionUtil.refreshFarmerInfo(getBaseContext(),intent,"us=" + userEdit.getText() + "&pwd=" + passEdit.getText(),"login","Login Please wait");
//                    getData(intent, "us=" + userEdit.getText() + "&pwd=" + passEdit.getText(), "login","Login Please wait",userEdit.getText().toString());
//
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


    public void getData(final Intent intent, final String queryString, final String type,String msg , final String us) {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";

        Toast.makeText(getBaseContext(),msg,
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL + "action="+type;
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

                    System.out.println("Serrver Rsponse3  : "+serverResponse);


                    if(serverResponse.isEmpty()){
                        System.out.println("Server Rsponse Empty");
                        Intent i = new Intent(getBaseContext(),StartUpActivity.class);
                        i.putExtra("err","Invalid Username or Password");
                        i.putExtra("us",us);
                        startActivity(i);
                    }else
                    {threadMsg(serverResponse,type);}
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
                    b.putString("type", type);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String type  = msg.getData().getString("type");
                        String aResponse = msg.getData().getString("message");

                        System.out.println("Server Response : "+aResponse);
                        if(type.equalsIgnoreCase("login")){

                            try {
//
                                if ((null != aResponse)) {
                                    JSONObject resp = new JSONObject(aResponse);
                                    if(resp.getString("rc").equalsIgnoreCase("00")){
                                        Intent intent1 = new Intent(getBaseContext(),DashboardActivity.class);
                                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                        //getData(intent, "", "details", "Login Successful, Loading Agent Data");
                                        ConnectionUtil.refreshFarmerInfo(getBaseContext(), intent1, "", "fdetails", "Loading Farmer Data");
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getBaseContext(),"Wrong Username or password",Toast.LENGTH_LONG).show();
                                        ((TextView)findViewById(R.id.txt_pass_response)).setText("Wrong Username or Password Please Try Again");
                                    }
                                }else{

                                    ((TextView)findViewById(R.id.txt_pass_response)).setText("Wrong Username or Password Please Try Again");
                                }
                                }catch(Exception e){

                            }

                        }else if(type.equalsIgnoreCase("details")){
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
                                        Farmer farmerDetail = new Farmer();

                                        JSONObject fItem = farmer.getJSONObject("farmer");
                                        JSONObject bioData = fItem.getJSONObject("biodata");

                                        /**


                                         "region": "1. Ashanti",
                                         "maritalstatus": "1. Single",
                                         "village": "Wwww",
                                         "nickname": "Cj",
                                         "": "0",
                                         "": "Rice",
                                         "age": "25",
                                         "community": "Xxxx",
                                         "gender": "2. Male",
                                         "lastname": "Osei",
                                         "numberofdependants": "0",
                                         "firstname": "cecil",
                                         "education": "1. No formal schooling",
                                         "farmerid": "a0m24000004wWGQAA2"
                                         */
                                        farmerDetail.setRegion(bioData.getString("region"));
                                        farmerDetail.setMaritalStatus(bioData.getString("maritalstatus"));
                                        farmerDetail.setVillage(bioData.getString("village"));
                                        farmerDetail.setRegion(bioData.getString("nickname"));
                                        farmerDetail.setRegion(bioData.getString("majorcrop"));
                                        farmerDetail.setRegion(bioData.getString("region"));



                                        /*
                                        *  "region": "2. Brong-Ahafo",
                    "maritalstatus": "2. Married",
                    "village": "Kumawu",
                    "nickname": "Spomega",
                    "numberofchildren": "3",
                    "majorcrop": "Rice",
                    "age": "58",
                    "community": "Kumawu",
                    "gender": "2. Male",
                    "lastname": "Davis",
                    "numberofdependants": "5",
                    "firstname": "Joe",
                    "education": "3. Middle/JHS",
                    "farmerid": "a0m24000004xT82AAE"
                                        *
                                        * */
                                        for (String key : keys) {
                                            try {
                                                vals[cnt] = farmer.getString(key);
                                            } catch (Exception e) {
                                                vals[cnt] = "";
                                            }
                                            cnt++;
                                        }


                                        Farmer f = databaseHelper.saveFarmer(vals[0], vals[1], vals[2], vals[3],
                                                vals[4], vals[5], vals[6], vals[7], vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14],
                                                vals[15], vals[16], vals[17], vals[18],
                                                vals[19], vals[20], vals[21], vals[22], vals[23], vals[24], vals[25], vals[26], vals[27], vals[28], vals[29], vals[30], vals[31], vals[32]
                                                ,"{}","{}","{}","{}","{}","{}","{}");


                                        JSONArray meetings = farmer.getJSONArray("meeting");

                                        if (meetings != null) {

                                        int grpCnt = 1;
                                        int individaulCnt = 1;
                                        for (int k = 0; k < meetings.length(); k++) {
                                            JSONObject meet = meetings.getJSONObject(k);

                                            //    public Meeting saveMeeting(String id, St
                                            // ring type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer,
                                            String typeMeet = meet.getString("ty");
                                            String title = "";
                                            // String remark,String crop,String season){

                                            //{"ty":"individual","midx":"1","sd":"01/03/2105","sea":"1","ed":"30/03/2015"},{"ty":"group","midx":"4","sd":"01/08/2015","sea":"1","ed":"31/08/2015"},
                                            // {"ty":"group","midx":"1","sd":"01/02/2015","sea":"1","ed":"28/02/2015"}
                                            if (typeMeet.contains("ind")) {
                                                title = individaulCnt + " " + typeMeet.toUpperCase() + " Meeting";
                                                individaulCnt++;
                                            } else {

                                                title = grpCnt + " " + typeMeet.toUpperCase() + " Meeting";
                                                grpCnt++;
                                            }
                                            databaseHelper.saveMeeting(meet.getString("id"),

                                                    meet.getString("ty"),
                                                    title,
                                                    IctcCKwUtil.formatSlashDates(meet.getString("sd")),
                                                    null,
                                                    meet.getInt("at"),
                                                    meet.getInt("midx"),
                                                    f.getFarmID(),
                                                    "",
                                                    f.getMainCrop(), meet.getString("sea")
                                            );


                                        }
                                    }
//                                    farmersCnt++;

                                    }




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


                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }


}