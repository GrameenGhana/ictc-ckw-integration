package applab.client.search.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import applab.client.search.R;
import applab.client.search.interactivecontent.WeatherContract;
import applab.client.search.interactivecontent.WeatherPresenter;
import applab.client.search.model.ForcastDay;
import applab.client.search.utils.Temperature;
import az.openweatherapi.model.gson.common.Coord;
import az.openweatherapi.model.gson.current_day.CurrentWeather;

/**
 * Created by aangjnr on 01/05/2017.
 */

public class WeatherActivity extends BaseActivity implements WeatherContract.View{

    private static final String TAG = WeatherActivity.class.getSimpleName();

    public static final int FIRST_DAY_INDEX = 0;
    public static final int SECOND_DAY_INDEX = 1;
    public static final int THIRD_DAY_INDEX = 2;
    public static final int FOURTH_DAY_INDEX = 3;
    public static final int FIFTH_DAY_INDEX = 4;
    private static final int LOCATION_REQUEST_ID = 10;

    boolean isTodaySelected = false;

    private WeatherPresenter presenter;

    //private Typeface robotoRegularTypeFace;

    //private Typeface robotoBlackTypeFace;



    KenBurnsView ken_burns_view;
     TextView first_forecast;
     TextView second_forecast;
     TextView third_forecast;
     TextView fourth_forecast;
     TextView fifth_forecast;

     TextView first_forecast_day;
     TextView second_forecast_day;
     TextView third_forecast_day;
     TextView fourth_forecast_day;
     TextView fifth_forecast_day;

     TextView first_forecast_day_month;
     TextView second_forecast_day_month;
     TextView third_forecast_day_month;
     TextView fourth_forecast_day_month;
     TextView fifth_forecast_day_month;

     TextView current_weather;

     TextView today_button;
     TextView five_day_button;

    TextView humidity;
    TextView atm_pressure;


     ProgressBar loading_weather_progress;

     ImageView current_weather_icon;
     ImageView first_day_icon;
     ImageView second_day_icon;
     ImageView third_day_icon;
     ImageView fourth_day_icon;
     ImageView fifth_day_icon;

    Animation fade_out;
    Animation fade_in;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

       // ken_burns_view = (KenBurnsView) findViewById(R.id.kbv);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        Log.i(TAG, "Hour of the day is " + currentHour);
/*
        if(currentHour >= 17 && currentHour < 3)
            ken_burns_view.setImageResource(R.drawable.woman_farmer);

        else ken_burns_view.setImageResource(R.drawable.dusk);*/

        first_forecast = (TextView) findViewById(R.id.first_forecast);
        second_forecast = (TextView) findViewById(R.id.second_forecast);
        third_forecast = (TextView) findViewById(R.id.third_forecast);
        fourth_forecast = (TextView) findViewById(R.id.fourth_forecast);
        fifth_forecast = (TextView) findViewById(R.id.fifth_forecast);

        humidity = (TextView) findViewById(R.id.humidity);
        atm_pressure = (TextView) findViewById(R.id.atm_pressure);

        first_forecast_day = (TextView) findViewById(R.id.first_forecast_day);
        second_forecast_day = (TextView) findViewById(R.id.second_forecast_day);
        third_forecast_day = (TextView) findViewById(R.id.third_forecast_day);
        fourth_forecast_day = (TextView) findViewById(R.id.fourth_forecast_day);
        fifth_forecast_day = (TextView) findViewById(R.id.fifth_forecast_day);

        first_forecast_day_month = (TextView) findViewById(R.id.first_forecast_day_month);
        second_forecast_day_month = (TextView) findViewById(R.id.second_forecast_day_month);
        third_forecast_day_month = (TextView) findViewById(R.id.third_forecast_day_month);
        fourth_forecast_day_month = (TextView) findViewById(R.id.fourth_forecast_day_month);
        fifth_forecast_day_month = (TextView) findViewById(R.id.fifth_forecast_day_month);

        current_weather = (TextView) findViewById(R.id.current_weather);

        today_button = (TextView) findViewById(R.id.today_button);
        five_day_button = (TextView) findViewById(R.id.five_day_button);


        loading_weather_progress = (ProgressBar) findViewById(R.id.loading_weather_progress);

        current_weather_icon = (ImageView) findViewById(R.id.current_weather_icon);
        first_day_icon = (ImageView) findViewById(R.id.first_day_icon);
        second_day_icon = (ImageView) findViewById(R.id.second_day_icon);
        third_day_icon = (ImageView) findViewById(R.id.third_day_icon);
        fourth_day_icon = (ImageView) findViewById(R.id.fourth_day_icon);
        fifth_day_icon = (ImageView) findViewById(R.id.fifth_day_icon);


        scaleText(five_day_button);

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_text);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out_text);



        presenter = new WeatherPresenter(this, Locale.getDefault());
        //robotoRegularTypeFace = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        //robotoBlackTypeFace = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
        setupUiTypeFace();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        checkLocationPermissions();

        else retrieveLatestKnownLocationAndCheckFiveDayWeather();




        today_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isTodaySelected) {
                    scaleText(today_button);
                    resetScale(five_day_button);
                    setupTodaySelectedUi();
                    presenter.getCurrentDayExtendedForecast();
                    isTodaySelected = true;
                }
            }
        });


        five_day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isTodaySelected = false;
                scaleText(five_day_button);
                resetScale(today_button);
                setupFiveDaySelectedUi();
                retrieveLatestKnownLocationAndCheckFiveDayWeather();

            }
        });
    }



    private void checkLocationPermissions() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            retrieveLatestKnownLocationAndCheckFiveDayWeather();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_ID);
        }
    }


    private void retrieveLatestKnownLocationAndCheckFiveDayWeather() {

        Double lat = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("latitude", "0.0"));
        Double lon = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("longitude", "0.0"));

        if(lat != 0.0 && lon != 0.0) {

            Coord coordinate = new Coord();
            coordinate.setLat(lat);
            coordinate.setLon(lon);

            presenter.getFiveDayForecast(coordinate);
        }else{

            Toast.makeText(this, "Could not get accurate location", Toast.LENGTH_SHORT).show();


        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_ID: {
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    retrieveLatestKnownLocationAndCheckFiveDayWeather();
                } else {
                    Toast.makeText(this, "Cannot retrieve current location\nwithout permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupFiveDaySelectedUi();
    }



    private void setupTodaySelectedUi() {

       // today_button.setTypeface(robotoBlackTypeFace);
        //five_day_button.setTypeface(robotoRegularTypeFace);

        first_forecast_day_month.startAnimation(fade_out);
        first_forecast_day_month.setVisibility(View.INVISIBLE);

        second_forecast_day_month.startAnimation(fade_out);
        second_forecast_day_month.setVisibility(View.INVISIBLE);

        third_forecast_day_month.startAnimation(fade_out);
        third_forecast_day_month.setVisibility(View.INVISIBLE);

        fourth_forecast_day_month.startAnimation(fade_out);
        fourth_forecast_day_month.setVisibility(View.INVISIBLE);

        fifth_forecast_day_month.startAnimation(fade_out);
        fifth_forecast_day_month.setVisibility(View.INVISIBLE);


    }



    private void setupFiveDaySelectedUi() {

        //today_button.setTypeface(robotoRegularTypeFace);
        //five_day_button.setTypeface(robotoBlackTypeFace);



        first_forecast_day_month.setText("");
        second_forecast_day_month.setText("");
        third_forecast_day_month.setText("");
        fourth_forecast_day_month.setText("");
        fifth_forecast_day_month.setText("");



        first_forecast_day_month.startAnimation(fade_in);
        first_forecast_day_month.setVisibility(View.VISIBLE);

        second_forecast_day_month.startAnimation(fade_in);
        second_forecast_day_month.setVisibility(View.VISIBLE);

        third_forecast_day_month.startAnimation(fade_in);
        third_forecast_day_month.setVisibility(View.VISIBLE);

        fourth_forecast_day_month.startAnimation(fade_in);
        fourth_forecast_day_month.setVisibility(View.VISIBLE);

        fifth_forecast_day_month.startAnimation(fade_in);
        fifth_forecast_day_month.setVisibility(View.VISIBLE);
    }

    private void setupUiTypeFace() {
        /*first_forecast_day.setTypeface(robotoBlackTypeFace);
        second_forecast_day.setTypeface(robotoBlackTypeFace);
        third_forecast_day.setTypeface(robotoBlackTypeFace);
        fourth_forecast_day.setTypeface(robotoBlackTypeFace);
        fifth_forecast_day.setTypeface(robotoBlackTypeFace);

        current_weather.setTypeface(robotoBlackTypeFace);
        first_forecast.setTypeface(robotoBlackTypeFace);
        second_forecast.setTypeface(robotoBlackTypeFace);
        third_forecast.setTypeface(robotoBlackTypeFace);
        fourth_forecast.setTypeface(robotoBlackTypeFace);
        fifth_forecast.setTypeface(robotoBlackTypeFace);*/
    }

    @Override
    public void updateFiveDayForecast(ArrayList<ForcastDay> forecastDays) {
        enableModeButtons();

        updateForecastInUi(forecastDays);
    }

    private void updateForecastInUi(ArrayList<ForcastDay> forecastDays) {
        String tempWithDegrees = String.format(getString(R.string.degrees_placeholder), forecastDays.get(FIRST_DAY_INDEX).getTemperature());
        first_forecast_day_month.setText(forecastDays.get(FIRST_DAY_INDEX).getDayMonth());

        first_forecast.setText(tempWithDegrees);
        first_forecast.setTextColor(getColor(presenter.getColorForTemp(forecastDays.get(FIRST_DAY_INDEX).getTemperature())));
        setupWeatherIcon(first_day_icon, forecastDays.get(FIRST_DAY_INDEX).getIcon());

        current_weather_icon.startAnimation(fade_out);
        current_weather_icon.setVisibility(View.INVISIBLE);
        setupWeatherIcon(current_weather_icon, forecastDays.get(FIRST_DAY_INDEX).getIcon());
        current_weather_icon.startAnimation(fade_in);
        current_weather_icon.setVisibility(View.VISIBLE);


        tempWithDegrees = String.format(getString(R.string.degrees_placeholder), forecastDays.get(SECOND_DAY_INDEX).getTemperature());
        second_forecast.setText(tempWithDegrees);
        second_forecast.setTextColor(getColor(presenter.getColorForTemp(forecastDays.get(SECOND_DAY_INDEX).getTemperature())));
        second_forecast_day.setText(forecastDays.get(SECOND_DAY_INDEX).getDay());
        second_forecast_day_month.setText(forecastDays.get(SECOND_DAY_INDEX).getDayMonth());
        setupWeatherIcon(second_day_icon, forecastDays.get(SECOND_DAY_INDEX).getIcon());

        tempWithDegrees = String.format(getString(R.string.degrees_placeholder), forecastDays.get(THIRD_DAY_INDEX).getTemperature());
        third_forecast.setText(tempWithDegrees);
        third_forecast.setTextColor(getColor(presenter.getColorForTemp(forecastDays.get(THIRD_DAY_INDEX).getTemperature())));
        third_forecast_day.setText(forecastDays.get(THIRD_DAY_INDEX).getDay());
        third_forecast_day_month.setText(forecastDays.get(THIRD_DAY_INDEX).getDayMonth());
        setupWeatherIcon(third_day_icon, forecastDays.get(THIRD_DAY_INDEX).getIcon());

        tempWithDegrees = String.format(getString(R.string.degrees_placeholder), forecastDays.get(FOURTH_DAY_INDEX).getTemperature());
        fourth_forecast.setText(tempWithDegrees);
        fourth_forecast.setTextColor(getColor(presenter.getColorForTemp(forecastDays.get(FOURTH_DAY_INDEX).getTemperature())));
        fourth_forecast_day.setText(forecastDays.get(FOURTH_DAY_INDEX).getDay());
        fourth_forecast_day_month.setText(forecastDays.get(FOURTH_DAY_INDEX).getDayMonth());
        setupWeatherIcon(fourth_day_icon, forecastDays.get(FOURTH_DAY_INDEX).getIcon());

        tempWithDegrees = String.format(getString(R.string.degrees_placeholder), forecastDays.get(FIFTH_DAY_INDEX).getTemperature());
        fifth_forecast.setText(tempWithDegrees);
        fifth_forecast.setTextColor(getColor(presenter.getColorForTemp(forecastDays.get(FIFTH_DAY_INDEX).getTemperature())));
        fifth_forecast_day.setText(forecastDays.get(FIFTH_DAY_INDEX).getDay());
        fifth_forecast_day_month.setText(forecastDays.get(FIFTH_DAY_INDEX).getDayMonth());
        setupWeatherIcon(fifth_day_icon, forecastDays.get(FIFTH_DAY_INDEX).getIcon());


        int atm =  (int) Math.round((forecastDays.get(FIRST_DAY_INDEX).getAtm() + forecastDays.get(SECOND_DAY_INDEX).getAtm() + forecastDays.get(THIRD_DAY_INDEX).getAtm() +
                forecastDays.get(FOURTH_DAY_INDEX).getAtm() + forecastDays.get(FIFTH_DAY_INDEX).getAtm())/5);


        atm_pressure.setText("Atmospheric Pressure = " + atm + "pA");


        int hum = Math.round((forecastDays.get(FIRST_DAY_INDEX).getHumidity() + forecastDays.get(SECOND_DAY_INDEX).getHumidity() + forecastDays.get(THIRD_DAY_INDEX).getHumidity() +
                forecastDays.get(FOURTH_DAY_INDEX).getHumidity() + forecastDays.get(FIFTH_DAY_INDEX).getHumidity())/5);

        humidity.setText("Humidity = " + hum + "%");



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

    private void enableModeButtons() {
        five_day_button.setEnabled(true);
        today_button.setEnabled(true);
    }

    @Override
    public void updateCurrentDayExtendedForecast(ArrayList<ForcastDay> currentWeather) {
        String degrees = String.format(getString(R.string.degrees_placeholder), currentWeather.get(FIRST_DAY_INDEX).getTemperature());

        current_weather.startAnimation(fade_out);
        current_weather.setVisibility(View.INVISIBLE);
        current_weather.setText(degrees);
        current_weather.startAnimation(fade_in);
        current_weather.setVisibility(View.VISIBLE);

        // current_weather.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(FIRST_DAY_INDEX).getTemperature())));

        first_forecast.setText(degrees);
        first_forecast.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(FIRST_DAY_INDEX).getTemperature())));
        setupWeatherIcon(first_day_icon, currentWeather.get(FIRST_DAY_INDEX).getIcon());

        current_weather_icon.startAnimation(fade_out);
        current_weather_icon.setVisibility(View.INVISIBLE);
        setupWeatherIcon(current_weather_icon, currentWeather.get(FIRST_DAY_INDEX).getIcon());
        current_weather_icon.setVisibility(View.VISIBLE);
        current_weather_icon.startAnimation(fade_in);


        degrees = String.format(getString(R.string.degrees_placeholder), currentWeather.get(SECOND_DAY_INDEX).getTemperature());
        second_forecast.setText(degrees);
        second_forecast.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(SECOND_DAY_INDEX).getTemperature())));
        second_forecast_day.setText(currentWeather.get(SECOND_DAY_INDEX).getHourOfForecast());
        setupWeatherIcon(second_day_icon, currentWeather.get(SECOND_DAY_INDEX).getIcon());

        degrees = String.format(getString(R.string.degrees_placeholder), currentWeather.get(THIRD_DAY_INDEX).getTemperature());
        third_forecast.setText(degrees);
        third_forecast.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(THIRD_DAY_INDEX).getTemperature())));
        third_forecast_day.setText(currentWeather.get(THIRD_DAY_INDEX).getHourOfForecast());
        setupWeatherIcon(third_day_icon, currentWeather.get(THIRD_DAY_INDEX).getIcon());

        degrees = String.format(getString(R.string.degrees_placeholder), currentWeather.get(FOURTH_DAY_INDEX).getTemperature());
        fourth_forecast.setText(degrees);
        fourth_forecast.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(FOURTH_DAY_INDEX).getTemperature())));
        fourth_forecast_day.setText(currentWeather.get(FOURTH_DAY_INDEX).getHourOfForecast());
        setupWeatherIcon(fourth_day_icon, currentWeather.get(FOURTH_DAY_INDEX).getIcon());

        degrees = String.format(getString(R.string.degrees_placeholder), currentWeather.get(FIFTH_DAY_INDEX).getTemperature());
        fifth_forecast.setText(degrees);
        fifth_forecast.setTextColor(getColor(presenter.getColorForTemp(currentWeather.get(FIFTH_DAY_INDEX).getTemperature())));
        fifth_forecast_day.setText(currentWeather.get(FIFTH_DAY_INDEX).getHourOfForecast());
        setupWeatherIcon(fifth_day_icon, currentWeather.get(FIFTH_DAY_INDEX).getIcon());


        humidity.setText("Humidity = " +  currentWeather.get(FIRST_DAY_INDEX).getHumidity());
        atm_pressure.setText("Atmospheric Pressure = " + (int) currentWeather.get(FIRST_DAY_INDEX).getAtm());


    }

    @Override
    public void updateTodayForecast(CurrentWeather currentWeather) {
        loading_weather_progress.setVisibility(View.INVISIBLE);

        int roundedTemp = (int) Math.round(currentWeather.getMain().getTemp());
        String tempWithDegrees = String.format(getString(R.string.degrees_placeholder), roundedTemp);

        current_weather.startAnimation(fade_out);
        current_weather.setVisibility(View.INVISIBLE);
        current_weather.setText(tempWithDegrees);
        current_weather.setVisibility(View.VISIBLE);
        current_weather.startAnimation(fade_in);

        // current_weather.setTextColor(getColor(presenter.getColorForTemp(roundedTemp)));
    }

    private int getColor(Temperature colorTemp) {
        int color = 0;

        switch (colorTemp) {
            case SUPER_HOT:
                color = ContextCompat.getColor(this, R.color.super_hot);
                break;
            case MEDIUM_HOT:
                color = ContextCompat.getColor(this, R.color.medium_hot);
                break;
            case HOT:
                color = ContextCompat.getColor(this, R.color.hot);
                break;
            case OK:
                color = ContextCompat.getColor(this, R.color.ok);
                break;
            case OK_CHILL:
                color = ContextCompat.getColor(this, R.color.ok_chill);
                break;
            case COLD:
                color = ContextCompat.getColor(this, R.color.cold);
                break;
        }

        return color;
    }


    void scaleText (final TextView view){

        view.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setTextColor(ContextCompat.getColor(WeatherActivity.this, R.color.white));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).setInterpolator(new BounceInterpolator())
                .start();




    }

    void resetScale (final TextView view){

        view.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(300)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setTextColor(ContextCompat.getColor(WeatherActivity.this, R.color.text_color_grey));

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();




    }
}
