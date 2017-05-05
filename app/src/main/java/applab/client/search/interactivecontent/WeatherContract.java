package applab.client.search.interactivecontent;

import java.util.ArrayList;

import applab.client.search.model.ForcastDay;
import applab.client.search.utils.Temperature;
import az.openweatherapi.model.gson.common.Coord;
import az.openweatherapi.model.gson.current_day.CurrentWeather;

/**
 * Created by aangjnr on 01/05/2017.
 */

public interface WeatherContract {

    interface View {
        void updateFiveDayForecast(ArrayList<ForcastDay> weatherForecastElement);

        void updateCurrentDayExtendedForecast(ArrayList<ForcastDay> currentWeather);

        void updateTodayForecast(CurrentWeather currentWeather);
    }

    interface Presenter {
        void getFiveDayForecast(final Coord coordinate);

        void getCurrentDayExtendedForecast();

        Temperature getColorForTemp(int temp);
    }
}