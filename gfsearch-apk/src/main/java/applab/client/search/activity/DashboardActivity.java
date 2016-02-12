package applab.client.search.activity;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import applab.client.search.*;
import applab.client.search.adapters.DashboardMenuAdapter;
import applab.client.search.adapters.WeatherPagerAdapter;
import applab.client.search.application.IctcCkwIntegration;
import applab.client.search.model.Farmer;
import applab.client.search.model.Payload;
import applab.client.search.model.UserDetails;
import applab.client.search.model.Weather;
import applab.client.search.services.MenuItemService;
import applab.client.search.services.TrackerService;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.synchronization.SynchronizationListener;
import applab.client.search.synchronization.SynchronizationManager;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.utils.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class DashboardActivity extends BaseFragmentActivity{
    private GridView grid_menu;
    private TableRow tableRow_communities;
    private TableRow tableRow_farmers;
    private TableRow tableRow_taroWorks;
    private LinearLayout tableRow_ckw;


    private ProgressDialog progressDialog = null;
    private Handler handler = null;

    DatabaseHelper helper;
    private LinearLayout clients;
    private LinearLayout meetings;
    private LinearLayout suppliers;
    private LinearLayout markets;
    private LinearLayout technical;


    SectionsPagerAdapter mSectionsPagerAdapter=null;
    ViewPager mViewPager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        grid_menu = (GridView) findViewById(R.id.gridView);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Dashboard");

        UserDetails u = helper.getUserItem();
        if(u!=null){
            System.out.println("User details : "+u.getFullName());
            IctcCKwUtil.setUserDetails(this,u);

        }else{
            System.out.println("User Null : "+u);
        }


        //temp try multimedia processing
        new MenuItemService().processMultimediaContent();
    try {
        ApplicationRegistry.setApplicationContext(this.getApplicationContext());
//        ApplicationRegistry.setMainActivity(this);


        //caching the device imie in the application registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                DeviceMetadata.getDeviceImei(this.getApplicationContext()));

        //register application version in registry
        ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION,
                getResources().getString(R.string.app_name) + "/"
                        + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        ApplicationRegistry.register(IctcCKwUtil.KEY_FULNAME,u.getFullName()
        );

        ApplicationRegistry.register(IctcCKwUtil.KEY_VERSION,getPackageManager().getPackageInfo(getPackageName(), 0).versionName
        );
        ApplicationRegistry.register(IctcCKwUtil.KEY_USER_NAME, u.getUserName());
    }catch(Exception e){

    }
        IctcCKwUtil.setActionbarUserDetails(this,mCustomView);
        ImageUtils.verifyDirectory();

        System.out.println("Initial Setup");
        Intent service = new Intent(this, TrackerService.class);
        Bundle tb = new Bundle();
        System.out.println("Middle Position");
        tb.putBoolean("backgroundData", true);
        service.putExtras(tb);
        this.startService(service);
        System.out.println("My Product");

        List<Weather> weathers = helper.getWeatherByCommunity();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),weathers,isNetworkConnected());
// Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.weather_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(weathers.size());

//        TextView st = (TextView) findViewById(R.id.farmer_cnt);
//        if (null == st) {
//            System.out.println("farmer cnt shd not be null ");
//        } else
//            st.setText(helper.farmerCount() + " Farmers");
//
//        TextView tv = (TextView) findViewById(R.id.community_count);
//        tv.setText(helper.farmerCountByCommunityGroup().size() + " Communities");


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                EditText tv = (EditText) mCustomView.findViewById(R.id.bar_search_text);
                String q = tv.getText().toString();
                System.out.println("SearchItem : " + q);
                System.out.println("Type : Search");
                intent.putExtra("q", q);
                startActivity(intent);
            }
        });
//getData();
        String[] titles = {"Clients", "Meetings", "Suppliers", "Markets","Technical Assistance"};
//        tableRow_communities = (TableRow) findViewById(R.id.tableRow_communities);
//        tableRow_farmers = (TableRow) findViewById(R.id.tableRow_farmers);

        int[] images = {R.drawable.ic_clients, R.drawable.ic_meetings,R.drawable.ic_suppliers,  R.drawable.ic_markets,  R.drawable.ic_technical};
        DashboardMenuAdapter adapter = new DashboardMenuAdapter(DashboardActivity.this, images, titles);


        super.setDetails(helper,"Dashboard","Dashboard");
        grid_menu.setAdapter(adapter);
        clients=(LinearLayout) findViewById(R.id.clients);
        clients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
        meetings=(LinearLayout) findViewById(R.id.meetings);
        meetings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ScheduledMeetingsActivity.class);
                startActivity(intent);
            }
        });
        suppliers=(LinearLayout) findViewById(R.id.suppliers);
        suppliers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SupplierActivity.class);
                startActivity(intent);
            }
        });
        markets=(LinearLayout) findViewById(R.id.markets);
        markets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               Intent intent = new Intent(DashboardActivity.this, MarketActivity.class);
                startActivity(intent);
            }
        });
        technical=(LinearLayout) findViewById(R.id.technical);
        technical.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        try{
            System.out.println("After Farmer Alter");
//            helper.alterFarmerTable();
            helper.alterSearchMenuItem();

            System.out.println("Before Farmer Alter");

        }catch (Exception e){}


     //   helper.deleteTable(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE,"");
//        tableRow_communities.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, CommunityActivity.class);
//                startActivity(intent);
//            }
//        });
//        tableRow_farmers.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
//                intent.putExtra("type", "farmer");
//                startActivity(intent);
//            }
//        });

        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(DashboardActivity.this, ClientActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(DashboardActivity.this, ScheduledMeetingsActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(DashboardActivity.this, SupplierActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(DashboardActivity.this, MarketActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

//            if (drawerToggle.onOptionsItemSelected(item)) {
//                return true;
//            }

//            System.out.println("Itemsing android.R.id.home  : "+android.R.id.action_home);

            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent().setClass(this, SettingsActivity.class);
                this.startActivityForResult(intent, 0);
            } /*else if (item.getItemId() == R.id.action_nav_back) {
                listViewBackNavigation();
            }
              else if (item.getItemId() == R.id.action_synchronise) {
                startSynchronization();
            */
        //    }
        else if (item.getItemId() == R.id.action_about) {
//                Intent intent = new Intent().setClass(this, AboutActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
                Intent intent = new Intent().setClass(this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                this.startActivity(intent);
            }

            else if (item.getItemId() == R.id.action_refresh_farmer) {
//                Intent intent = new Intent().setClass(this, AboutActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                this.startActivity(intent);
                //(final Context context,final DatabaseHelper databaseHelper,final Intent intent, final String queryString, final String type,String msg )


                Toast.makeText(getBaseContext(),"Synchronising Data Please wait",Toast.LENGTH_LONG).show();
                System.out.println("Payload Refresh farmer Data");
//                IctcCkwIntegration app = null;
//                try {
//                    app=(IctcCkwIntegration) this.getApplication();
//
//                }catch(Exception e)h{
//                    System.out.println("Exceptione e: "+e.getLocalizedMessage());
//                }

                System.out.println("Payload ppapp ");
                DatabaseHelper dbh = new DatabaseHelper(getBaseContext());
                System.out.println("Payload dbh ");
                Payload mqp = dbh.getCCHUnsentLog();
                System.out.println("Payload unset ");
                IctcTrackerLogTask omUpdateCCHLogTask = new IctcTrackerLogTask(this);
                System.out.println("Payload stask ");
                omUpdateCCHLogTask.execute(mqp);
                System.out.println("Payload execute ");
                ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report");


                ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");
                startSynchronization();
//
            }
//            else if (item.getItemId() == android.R.id.home) {
//                //resetDisplayMenus();
//                selectItem(0);
//            }
            else if (item.getItemId() == R.id.action_logout) {helper.resetFarmer();
                Intent intent = new Intent().setClass(this, StartUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                this.startActivity(intent);

            }
            else if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent().setClass(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                this.startActivity(intent);

            } /*else if (item.getItemId() ==R.id.action_meeting_items) {
                Intent intent = new Intent().setClass(this, ScheduledMeetingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                this.startActivity(intent);

            }*/
        } catch (Exception ex) {
            Log.e(MainActivity.class.getName(), "", ex);
        }

        return true;
    }


    private void startSynchronization() {
        SynchronizationManager.getInstance().registerListener(new SynchronizationListener() {
            @Override
            public void synchronizationStart() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.show();
                    }
                });
            }

            @Override
            public void synchronizationUpdate(final Integer step, final Integer max, final String message, Boolean reset) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage(message);
                        progressDialog.setMax(max);
                        progressDialog.setProgress(step);
                        progressDialog.setIndeterminate(false);
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    }
                });
            }

            @Override
            public void synchronizationUpdate(final String message, Boolean indeterminate) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage(message);
                        progressDialog.setIndeterminate(true);
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    }
                });
            }

            @Override
            public void synchronizationComplete() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();


                        new MenuItemService().processMultimediaContent();
                        //we refresh the UI
                    }

                });

                SynchronizationManager.getInstance().unRegisterListener(this);
            }

            @Override
            public void onSynchronizationError(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        AlertDialog alertDialog =
                                new AlertDialog.Builder(DashboardActivity.this).create();
                        alertDialog.setMessage(throwable.getMessage());
                        alertDialog.setIcon(android.R.drawable.stat_sys_warning);

                        alertDialog.setTitle(R.string.error_title);
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                    }
                });

                SynchronizationManager.getInstance().unRegisterListener(this);
            }
        });

        SynchronizationManager.getInstance().start();
    }

    public void getSData() {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";

        Toast.makeText(getBaseContext(),
                "Please wait, connecting to server Dashboard.",
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL;

                    JSONObject j = new JSONObject();
                    System.out.println("URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    System.out.println("After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    System.out.println("Sent Response");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                        serverResponse += line;
                    }

//                    System.out.println("Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg) {

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
                                List<Farmer> myFarmers = new ArrayList<Farmer>();

                                int farmersCnt = 0;
                                for (int i = 0; i < farmers.length(); i++) {
                                    JSONObject farmer = farmers.getJSONObject(i);
                                    System.out.println("Farmer Name  : " + farmer.getString("lname"));
//                                    myFarmers.add(new Farmer(farmer.getString("fname"),farmer.getString("lName"),farmer.getString("nickname"),farmer.getString("community"),farmer.getString("village"),farmer.getString("district"),farmer.getString("region"),farmer.getString("age"),farmer.getString("gender"),farmer.getString("ms"),farmer.getString("noc"),farmer.getString("nod"),farmer.getString("edu"),farmer.getString("cluster"),farmer.getString("id")));

//
//     cluster1List.add((String) j.get("farmer"));
                                    farmersCnt++;
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
                                    "Cluster Data Recieved: ",
                                    Toast.LENGTH_SHORT).show();
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

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        List<Weather> weathers;
        boolean internet=false;
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm,List<Weather> weathers , boolean netwk) {
            super(fm);

            internet = netwk;

            this.weathers = weathers;
            if(weathers.isEmpty()){

                if(internet){
                    ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report",this);
                }
                Weather w = new Weather();
                w.setLocation("No Weather Data Found");
                w.setDetail("no update");
                w.setIcon("50n");
                w.setTemprature(0);
                w.setMinTemprature(0);
                w.setMaxTemprature(0);

                weathers.add(w);
                 w = new Weather();
                w.setLocation("No Weather Data Found -");
                w.setDetail("no update");
                w.setIcon("01d");
                w.setTemprature(0);
                w.setMinTemprature(0);
                w.setMaxTemprature(0);

                weathers.add(w);
            }
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

                fragment= new WeatherSummary(weathers.get(position));

            return fragment;
        }
        @Override
        public int getCount() {
            return weathers.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    public class WeatherSummary extends Fragment {
        View rootView;
//        private SharedPreferences loginPref;

        Weather weather ;
        String month_text;
        public WeatherSummary(){
        }

        public WeatherSummary(Weather w){
            this.weather = w;
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView=inflater.inflate(R.layout.activity_weather_item ,null,false);


            TextView v =(TextView) rootView.findViewById(R.id.txt_weather_city);
            v.setText(weather.getLocation());

            v =(TextView) rootView.findViewById(R.id.txt_weather_temp);
            v.setText(String.valueOf(weather.getTemprature())+" C ");
            Date i = new Date(weather.getTime()*1000);
            System.out.println("Date r : "+i);
            System.out.println("Date o : "+weather.getTime());


            v =(TextView) rootView.findViewById(R.id.txt_weather_description);
            v.setText(String.valueOf(weather.getDetail())+"");

            ImageView iv =(ImageView) rootView.findViewById(R.id.img_weather_icon);
            String mDrawableName = "w_"+weather.getIcon();
            int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
            iv.setImageResource(resID);

            v =(TextView) rootView.findViewById(R.id.txt_weather_time);

            if(weather.getTime()==0l)
                v.setText("-");
            else
                v.setText("Up to "+IctcCKwUtil.formatStringDateTime(i,"d MMM hh:mm"));


            return rootView;
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}