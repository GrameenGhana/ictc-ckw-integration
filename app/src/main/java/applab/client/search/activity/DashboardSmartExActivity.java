package applab.client.search.activity;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

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
import applab.client.search.interactivecontent.WeatherContract;
import applab.client.search.interactivecontent.WeatherPresenter;
import applab.client.search.model.ForcastDay;
import applab.client.search.model.Meeting;
import applab.client.search.model.Payload;
import applab.client.search.model.Weather;
import applab.client.search.services.MenuItemService;
import applab.client.search.settings.SettingsActivity;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.task.IctcTrackerLogTask;
import applab.client.search.utils.*;
import az.openweatherapi.OWService;
import az.openweatherapi.listener.OWRequestListener;
import az.openweatherapi.model.OWResponse;
import az.openweatherapi.model.gson.common.Coord;
import az.openweatherapi.model.gson.current_day.CurrentWeather;
import az.openweatherapi.model.gson.five_day.ExtendedWeather;
import az.openweatherapi.utils.OWSupportedUnits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardSmartExActivity extends BaseFragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, WeatherContract.View {
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
    String IS_FARMER_INFO_REFRESHED = "isFarmerInfoRefreshed";
    SharedPreferences sharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    double mLongitude = 0.0;
    double mLatitude = 0.0;

    TextView currentWeatherText;
    ImageView currentWeatherIcon;
    WeatherPresenter presenter;
    LinearLayout weather;
    TextView city_name;
    SharedPreferences prefs;
    String _loc = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoShadow);
        setContentView(R.layout.activity_dashboard_smartex);
        super.setDetails(Db(), "SmartEx Dashboard", "Startup");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        createActionBar();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        presenter = new WeatherPresenter(this, Locale.getDefault());



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        today = new DateTime();
        username=(TextView) findViewById(R.id.username);
        user_type=(TextView) findViewById(R.id.user_type);

        username.setText(ConnectionUtil.currentUserFullName(DashboardSmartExActivity.this));
        user_type.setText(ConnectionUtil.currentUserType(DashboardSmartExActivity.this));


        currentWeatherText = (TextView) findViewById(R.id.current_weather_text);
        currentWeatherIcon = (ImageView) findViewById(R.id.current_weather_icon);
        city_name = (TextView) findViewById(R.id.city_name);

        currentWeatherText.setText(prefs.getString("lastTempValue", "28"));
        setupWeatherIcon(currentWeatherIcon, prefs.getString("lastWeatherIcon", "sunny"));

        city_name.setText(prefs.getString("lastCityValue", "Accra, Ghana"));



        weather = (LinearLayout) findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardSmartExActivity.this, WeatherActivity.class));
            }
        });





        RelativeLayout clients=(RelativeLayout) findViewById(R.id.clients);
        clients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Toast.makeText(DashboardSmartExActivity.this, "Its clickable!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(DashboardSmartExActivity.this, ClientActivity.class);\


                Intent intent = new Intent(DashboardSmartExActivity.this, FarmerRecordsOptionsActivity.class);
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

            ex.printStackTrace();
        }
    }



    protected void onStart() {
        mGoogleApiClient.connect();
        Log.i(TAG, "API client connected");
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        Log.i(TAG, "API client disconnected");
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            mLongitude = mLastLocation.getLongitude();
            mLatitude = mLastLocation.getLatitude();





            if(IctcCKwUtil.haveNetworkConnection(this)) {
                if (mLatitude != 0.0 && mLongitude != 0.0) {


                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> listAddresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                        if(null!=listAddresses&&listAddresses.size()>0){
                            String _Location = listAddresses.get(0).getAddressLine(0);

                            _loc = _Location;
                            Log.i(TAG, "Geocoder location is " + _Location);
                            prefs.edit().putString("lastCityValue", _Location).apply();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    Log.i(TAG, "Longitude = " + mLongitude + " and Latitude = " + mLatitude);
                    PreferenceManager.getDefaultSharedPreferences(this).edit().putString("latitude", String.valueOf(mLatitude))
                            .putString("longitude", String.valueOf(mLongitude)).apply();



                    WeatherPresenter presenter = new WeatherPresenter(DashboardSmartExActivity.this, Locale.getDefault());
                    Coord coordinate = new Coord();
                    coordinate.setLat(mLatitude);
                    coordinate.setLon(mLatitude);
                    presenter.getFiveDayForecast(coordinate);
 
                }


            }else {
                Toast.makeText(this, "Please check your internet connection to update weather services.", Toast.LENGTH_LONG).show();
            }


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    @Override
    public void updateFiveDayForecast(ArrayList<ForcastDay> weatherForecastElement) {

    }

    @Override
    public void updateCurrentDayExtendedForecast(ArrayList<ForcastDay> currentWeather) {

        Log.i(TAG, "updateCurrentDayExtendedForecast\n");


    }

    @Override
    public void updateTodayForecast(CurrentWeather currentWeather) {

        String city = currentWeather.getName();
        Double temp = currentWeather.getMain().getTemp();
        String icon = currentWeather.getWeather().get(0).getIcon();
        String desc = currentWeather.getWeather().get(0).getDescription();



        Log.i(TAG, "updateTodayForecast" + " Country and city name " + currentWeather.getSys().getCountry() + ", " + city
        + " Temp = " + temp + "\n" + "Description = " + desc + "\n" + "Icon = " + icon);






        int roundedTemp = (int) Math.round(temp);
        String tempWithDegrees = String.format(getString(R.string.degrees_placeholder), roundedTemp);
         Log.i(TAG, "Degrees is " + tempWithDegrees);

        currentWeatherText.setText(tempWithDegrees);
        prefs.edit().putString("lastTempValue", tempWithDegrees).putString("lastWeatherIcon", presenter.parseWeatherDescription(desc)).apply();
        setupWeatherIcon(currentWeatherIcon, presenter.parseWeatherDescription(desc));
        city_name.setText(_loc);



        Log.i(TAG, "Weather Updated");



    }

    private void setupWeatherIcon(ImageView imageView, String icon) {
        switch (icon) {
            case WeatherPresenter.SUNNY:
                Picasso.with(this).load(R.drawable.sunny).into(imageView);
                break;
            case WeatherPresenter.CLOUDY:
                Picasso.with(this).load(R.drawable.cloudy).into(imageView);
                break;
            case WeatherPresenter.PARTIALLY_CLOUDY:
                Picasso.with(this).load(R.drawable.partially_cloudy).into(imageView);
                break;
            case WeatherPresenter.RAINY:
                Picasso.with(this).load(R.drawable.rainy).into(imageView);
                break;
        }
    }
}

