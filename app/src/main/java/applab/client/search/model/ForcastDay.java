package applab.client.search.model;

/**
 * Created by aangjnr on 01/05/2017.
 */

public class ForcastDay {

    private String day;
    private String dayString;
    private String hourOfForecast;
    private int temperature;
    private String icon;
    private int humidity;
    private double atm;

    public ForcastDay(String day, String dayString, int hourOfForecast, int temperature, int hum, double atm, String icon) {
        this.day = day;
        this.dayString = dayString;
        this.temperature = temperature;
        this.hourOfForecast = hourOfForecast + ":00";
        this.humidity =   hum ;
        this.atm =  atm ;
        this.icon = icon;
    }

    public String getDay() {
        return day;
    }

    public String getDayMonth() {
        return dayString;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getHourOfForecast() {
        return hourOfForecast;
    }

    public String getIcon() {
        return icon;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getAtm() {
        return atm;
    }
}


