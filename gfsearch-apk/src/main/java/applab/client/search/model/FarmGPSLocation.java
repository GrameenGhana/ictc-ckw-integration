package applab.client.search.model;

/**
 * Created by skwakwa on 9/14/15.
 */
public class FarmGPSLocation {

    private int id;
    private double latitude;
    private double longitude;
    private String farmerId;

    public  FarmGPSLocation(int id,double latitude,double longitude,String farmerId){

        this.setId(id);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setFarmerId(farmerId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }
}
