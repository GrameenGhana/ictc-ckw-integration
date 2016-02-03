package applab.client.search.model;

import applab.client.search.utils.IctcCKwUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by skwakwa on 12/3/15.
 */
public class Weather {

    private String location;
    private String locationId;
    private String longitude;
    private String latitude;

    private String icon;
    private double temprature;
    private double minTemprature;
    private double maxTemprature;
    private String detail;
    private long time;
    private String timing;

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the temprature
     */
    public double getTemprature() {
        return temprature;
    }

    /**
     * @param temprature the temprature to set
     */
    public void setTemprature(double temprature) {
        this.temprature = temprature;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;

        try {
            Date dt = new Date();
            dt.setTime(time);
            timing = IctcCKwUtil.formatStringDateTime(dt,"D M, y H:i");

        }catch (Exception e){


        }
    }

    /**
     * @return the minTemprature
     */
    public double getMinTemprature() {
        return minTemprature;
    }

    /**
     * @param minTemprature the minTemprature to set
     */
    public void setMinTemprature(double minTemprature) {
        this.minTemprature = minTemprature;
    }

    /**
     * @return the maxTemprature
     */
    public double getMaxTemprature() {
        return maxTemprature;
    }

    /**
     * @param maxTemprature the maxTemprature to set
     */
    public void setMaxTemprature(double maxTemprature) {
        this.maxTemprature = maxTemprature;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }


    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
