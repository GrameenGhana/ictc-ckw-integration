package applab.client.search.activity;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import applab.client.search.*;
import applab.client.search.adapters.DashboardMenuAdapter;
import applab.client.search.model.Weather;
import applab.client.search.services.MenuItemService;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.utils.*;
import java.util.Date;
import java.util.List;

public class DashboardSmartExActivity extends BaseFragmentActivity {
    private static final String TAG = DashboardSmartExActivity.class.getSimpleName();

    View mCustomView;
    ViewPager mViewPager;
    ActionBar mActionBar;
    SectionsPagerAdapter mSectionsPagerAdapter=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_smartex);
        super.setDetails(Db(), "SmartEx Dashboard","Startup");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            ConnectionUtil.refreshWeather(getBaseContext(), "weather", "Get latest weather report");
            ConnectionUtil.refreshFarmerInfo(getBaseContext(), null, "", IctcCkwIntegrationSync.GET_FARMER_DETAILS, "Refreshing farmer Data");

            if (super.Db().farmerCount() > 0) {
                if (super.Db().getMeetingSettingCount() == 0) {
                    AgentVisitUtil.setMeetingSettings(super.Db());
                }
            }

            new MenuItemService().processMultimediaContent();
            createActionBar();
            createPageAdaptor();
            createGrid();

        } catch (NullPointerException e) {
            Log.d(TAG, e.getMessage());

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void createActionBar() {
        try {
            mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(true);
            LayoutInflater mInflater = LayoutInflater.from(this);

            mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
            mActionBar.setTitle("Dashboard");
            mTitleTextView.setText("Dashboard");

            //mActionBar.setCustomView(mCustomView);
            //mActionBar.setDisplayShowCustomEnabled(true);
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
            Log.d(TAG, ex.getMessage());

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void createPageAdaptor() {
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
    }

    private void createGrid() {
        try {
            String[] titles = {"Clients", "Meetings", "Suppliers", "Markets","Technical Assistance"};
            int[] images = {R.drawable.ic_clients, R.drawable.ic_meetings,R.drawable.ic_suppliers,  R.drawable.ic_markets,  R.drawable.ic_technical};
            DashboardMenuAdapter adapter = new DashboardMenuAdapter(DashboardSmartExActivity.this, images, titles);
            super.setDetails(super.Db(), "Dashboard","Dashboard");

            GridView grid_menu = (GridView) findViewById(R.id.gridView);
            grid_menu.setAdapter(adapter);

            LinearLayout clients=(LinearLayout) findViewById(R.id.clients);
            clients.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, ClientActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout meetings =(LinearLayout) findViewById(R.id.meetings);
            meetings.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, ScheduledMeetingsActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout suppliers=(LinearLayout) findViewById(R.id.suppliers);
            suppliers.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, SupplierActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout markets=(LinearLayout) findViewById(R.id.markets);
            markets.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, MarketActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout technical=(LinearLayout) findViewById(R.id.technical);
            technical.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardSmartExActivity.this, CKWSearchActivity.class);
                    startActivity(intent);
                }
            });


            super.Db().alterSearchMenuItem();

            grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    }
                }
            });
        } catch(NullPointerException ex) {
            Log.d(TAG, ex.getMessage());
        } catch(Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

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
            rootView=inflater.inflate(R.layout.activity_weather_item ,null,false);

            TextView v =(TextView) rootView.findViewById(R.id.txt_weather_city);
            v.setText(weather.getLocation());

            v =(TextView) rootView.findViewById(R.id.txt_weather_temp);
            v.setText(String.valueOf(weather.getTemprature())+" C ");
            Date i = new Date(weather.getTime()*1000);

            v =(TextView) rootView.findViewById(R.id.txt_weather_description);
            v.setText(String.valueOf(weather.getDetail())+"");

            ImageView iv =(ImageView) rootView.findViewById(R.id.img_weather_icon);
            String mDrawableName = "w_"+weather.getIcon();
            int resID = getResources().getIdentifier(mDrawableName , "drawable", ctx.getPackageName());
            iv.setImageResource(resID);

            v =(TextView) rootView.findViewById(R.id.txt_weather_time);

            if(weather.getTime()==0l)
                v.setText("-");
            else
                v.setText("Up to "+IctcCKwUtil.formatStringDateTime(i,"d MMM hh:mm"));

            return rootView;
        }
    }
}