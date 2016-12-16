package applab.client.search.activity;

import android.annotation.SuppressLint;
import android.app.*;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import applab.client.search.*;
import applab.client.search.adapters.DashboardMenuAdapter;
import applab.client.search.adapters.GridMenuAdapter;
import applab.client.search.adapters.UpcomingMeetingsAdapter;
import applab.client.search.adapters.WeatherListAdapter;
import applab.client.search.model.Meeting;
import applab.client.search.model.Payload;
import applab.client.search.model.Weather;
import applab.client.search.services.MenuItemService;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardSmartExActivity extends BaseFragmentActivity {
    private static final String TAG = DashboardSmartExActivity.class.getSimpleName();

    View mCustomView;
    ViewPager mViewPager;
    ActionBar mActionBar;
    //SectionsPagerAdapter mSectionsPagerAdapter=null;
    private TextView username;
    private EditText search_text;
    private TextView user_type;
    private ListView upcoming_meetings;
    private List<Meeting> meetinglist;
    private List<Meeting> upcomingDates;
    private DateTime today;
    private RecyclerView horizontalGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoShadow);
        setContentView(R.layout.activity_dashboard_smartex);
        super.setDetails(Db(), "SmartEx Dashboard", "Startup");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        createActionBar();

        today = new DateTime();
        username=(TextView) findViewById(R.id.username);
        user_type=(TextView) findViewById(R.id.user_type);
        username.setText(ConnectionUtil.currentUserFullName(DashboardSmartExActivity.this));
        user_type.setText(ConnectionUtil.currentUserType(DashboardSmartExActivity.this));
        //upcoming_meetings=(ListView) findViewById(R.id.upcoming_meetings);

        /*horizontalGridView = (RecyclerView)findViewById(R.id.horizontal_recycler_view);

        displayWeather(horizontalGridView);*/


       // search_text=(EditText) findViewById(R.id.search_text);
        /*try {
            ConnectionUtil.refreshWeather(getBaseContext(), "weather", "Get latest weather report");
            // DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
            //DateTime dt = formatter.parseDateTime(super.Db().getUserItem().getLastModifiedDate());
            //Duration duration = new Duration(dt, today);
           // System.out.println("Number of minutes since last sync: "+duration.getStandardMinutes());
          //if(duration.getStandardMinutes()>20){
               // ConnectionUtil.refreshFarmerInfo(DashboardSmartExActivity.this, null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");
          //  }


            if (super.Db().farmerCount() > 0) {
                if (super.Db().getMeetingSettingCount() == 0) {
                    AgentVisitUtil.setMeetingSettings(super.Db());
                }
            }
            meetinglist=super.Db().meetingTimes();
            upcomingDates=new ArrayList<Meeting>();
            for(int i=0;i<meetinglist.size();i++){
                DateTime meetingDate=new DateTime(Long.valueOf(meetinglist.get(i).getScheduledMeetingDate()));

                Meeting m=new Meeting();
                //System.out.println(Days.daysBetween(meetingDate.toLocalDate(), today.toLocalDate()).getDays());
                if(Days.daysBetween(today.toLocalDate(), meetingDate.toLocalDate()).getDays()>30){
                    m.setType(meetinglist.get(i).getType());
                    m.setTitle(meetinglist.get(i).getTitle());
                    m.setScheduledMeetingDate(meetinglist.get(i).getScheduledMeetingDate());
                    upcomingDates.add(m);
                }
            }
            UpcomingMeetingsAdapter ad=new UpcomingMeetingsAdapter(DashboardSmartExActivity.this,upcomingDates);
            upcoming_meetings.setAdapter(ad);
            new MenuItemService().processMultimediaContent();

           // createPageAdaptor();
            //createGrid();

        } catch (NullPointerException e) {
            Log.d(TAG, e.getMessage().toString());
        }

*/









        RelativeLayout clients=(RelativeLayout) findViewById(R.id.clients);
        clients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Toast.makeText(DashboardSmartExActivity.this, "Its clickable!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardSmartExActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });



        RelativeLayout meetings =(RelativeLayout) findViewById(R.id.meetings);
        meetings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardSmartExActivity.this, ScheduledMeetingsActivity.class);
                startActivity(intent);
            }
        });


        RelativeLayout technical=(RelativeLayout) findViewById(R.id.technical);
        technical.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardSmartExActivity.this, CKWSearchActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout farmersearch=(RelativeLayout) findViewById(R.id.farmerSearch);
        farmersearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardSmartExActivity.this, FarmerActivity.class);
                startActivity(intent);
            }
        });


    }

    private void createActionBar() {
        try {
            mActionBar = getSupportActionBar();
            mActionBar.setElevation(0);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(true);
            LayoutInflater mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
            mActionBar.setTitle("Dashboard");
            mTitleTextView.setText("Dashboard");
            Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
            mButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, FarmerActivity.class);
                    intent.putExtra("type", "search");
                    EditText tv = (EditText) mCustomView.findViewById(R.id.bar_search_text);
                    String q = tv.getText().toString();
                    intent.putExtra("q", q);
                    startActivity(intent);
                }
            });

        } catch(NullPointerException ex) {
            Log.d(TAG, ex.getMessage().toString());

        } catch (Exception e) {
            Log.d(TAG, e.getMessage().toString());
        }
    }

   /* private void createPageAdaptor() {
        try {
            IctcCKwUtil.setActionbarUserDetails(this, mCustomView);
            List<Weather> weathers = super.Db().getWeatherByCommunity();

            // Set up the ViewPager with the sections adapter.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), weathers, ConnectionUtil.isNetworkConnected(getApplicationContext()));
            mViewPager = (ViewPager) findViewById(R.id.weather_pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOffscreenPageLimit(weathers.size());

        } catch(NullPointerException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }*/
    private void createGrid() {
        try {
            String[] titles = {"Farmers", "Meetings", "Suppliers", "Markets","Technical Assistance", "Farmer Search"};
            int[] images = {R.mipmap.farmer_icon, R.mipmap.meeting_icon,R.mipmap.lorrygreen,  R.mipmap.markets,  R.mipmap.repair,R.mipmap.farmer_search};
            GridMenuAdapter adapter = new GridMenuAdapter(DashboardSmartExActivity.this, images, titles);
            super.setDetails(super.Db(), "Dashboard","Dashboard");

            //GridView grid_menu = (GridView) findViewById(R.id.gridView);
           // grid_menu.setAdapter(adapter);

            RelativeLayout clients=(RelativeLayout) findViewById(R.id.clients);
            clients.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(DashboardSmartExActivity.this, "Its clickable!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardSmartExActivity.this, ClientActivity.class);
                    startActivity(intent);
                }
            });


            CardView clients_cv=(CardView) findViewById(R.id.clients_cv);
            clients_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DashboardSmartExActivity.this, "Its clickable!", Toast.LENGTH_SHORT).show();
                }
            });



            RelativeLayout meetings =(RelativeLayout) findViewById(R.id.meetings);
            meetings.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, ScheduledMeetingsActivity.class);
                    startActivity(intent);
                }
            });

            /*LinearLayout suppliers=(LinearLayout) findViewById(R.id.suppliers);
            suppliers.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, SupplierActivity.class);
                    startActivity(intent);
                }
            });*/

            /*LinearLayout markets=(LinearLayout) findViewById(R.id.markets);
           markets.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, MarketActivity.class);
                    startActivity(intent);
                }
            });*/

            RelativeLayout technical=(RelativeLayout) findViewById(R.id.technical);
            technical.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, CKWSearchActivity.class);
                    startActivity(intent);
                }
            });
            RelativeLayout farmersearch=(RelativeLayout) findViewById(R.id.farmerSearch);
            farmersearch.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, FarmerActivity.class);
                    startActivity(intent);
                }
            });


            super.Db().alterSearchMenuItem();

           /* grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent;
                    switch (i) {
                    case 0:
                        intent = new Intent(DashboardSmartExActivity.this, ClientActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(DashboardSmartExActivity.this, ScheduledMeetingsActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(DashboardSmartExActivity.this, SupplierActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(DashboardSmartExActivity.this, MarketActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(DashboardSmartExActivity.this, CKWSearchActivity.class);
                        startActivity(intent);
                        break;
                        case 5:
                            intent = new Intent(DashboardSmartExActivity.this, FarmerActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });*/
        } catch(NullPointerException ex) {
            Log.d(TAG, ex.getMessage());
        } catch(Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

   /*private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        List<Weather> weathers;

        public SectionsPagerAdapter(FragmentManager fm, List<Weather> weathers , boolean connected) {

            super(fm);
            this.weathers = weathers;

            if(weathers.isEmpty()){

                if(connected){
                    ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report",this);
                }

                Weather w = new Weather();
                w.setLocation("No Weather Data Found");
                w.setDetail("no update");
                w.setIcon("01d");
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
            fragment= new WeatherSummary(getApplicationContext(), weathers.get(position));
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

    public static class WeatherSummary extends Fragment {
        View rootView;
        Weather weather ;
        Context ctx;

        public WeatherSummary() {}
        @SuppressLint("ValidFragment")
        public WeatherSummary(Context c, Weather w){ this.ctx = c; this.weather = w; }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            try {
                rootView = inflater.inflate(R.layout.activity_weather_item, null, false);
                TextView v = (TextView) rootView.findViewById(R.id.txt_weather_city);
                v.setText(weather.getLocation());
                v = (TextView) rootView.findViewById(R.id.txt_weather_temp);
                v.setText(String.valueOf(weather.getTemprature()) + " C ");
                Date i = new Date(weather.getTime() * 1000);
                v = (TextView) rootView.findViewById(R.id.txt_weather_description);
                v.setText(String.valueOf(weather.getDetail()) + "");
                ImageView iv = (ImageView) rootView.findViewById(R.id.img_weather_icon);
                String mDrawableName = "w_" + weather.getIcon();
                int resID = getResources().getIdentifier(mDrawableName, "drawable", ctx.getPackageName());
                iv.setImageResource(resID);
                v = (TextView) rootView.findViewById(R.id.txt_weather_time);
                if (weather.getTime() == 0l)
                    v.setText("-");
                else
                    v.setText("Up to " + IctcCKwUtil.formatStringDateTime(i, "d MMM hh:mm"));
            }catch(Exception e){
                e.printStackTrace();
            }
                return rootView;
        }
    }*/


    public void displayWeather(RecyclerView g){
        List<Weather> weathers = super.Db().getWeatherByCommunity();
        if(weathers.isEmpty()){

            if(ConnectionUtil.isNetworkConnected(getApplicationContext())){
                ConnectionUtil.refreshWeather(getBaseContext(),"weather","Get latest weather report",null);
            }

            Weather w = new Weather();
            w.setLocation("No Weather Data");
            w.setDetail("no update");
            w.setIcon("10d");
            w.setTemprature(0);
            w.setMinTemprature(0);
            w.setMaxTemprature(0);
            w.setTime(1471008957);
            weathers.add(w);

            w = new Weather();
            w.setLocation("No Weather Data");
            w.setDetail("no update");
            w.setIcon("01d");
            w.setTemprature(0);
            w.setMinTemprature(0);
            w.setMaxTemprature(0);
            w.setTime(1471008957);
            weathers.add(w);

            w = new Weather();
            w.setLocation("No Weather Data");
            w.setDetail("no update");
            w.setIcon("01d");
            w.setTemprature(0);
            w.setMinTemprature(0);
            w.setMaxTemprature(0);
            weathers.add(w);

            w = new Weather();
            w.setLocation("No Weather Data");
            w.setDetail("no update");
            w.setIcon("01d");
            w.setTemprature(0);
            w.setMinTemprature(0);
            w.setMaxTemprature(0);
            weathers.add(w);


        }
        WeatherListAdapter adapter=new WeatherListAdapter(DashboardSmartExActivity.this,weathers);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(DashboardSmartExActivity.this, LinearLayoutManager.HORIZONTAL, false);
        g.setLayoutManager(horizontalLayoutManagaer);
        g.setAdapter(adapter);
    }
}

