package applab.client.search.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.model.FarmGPSLocation;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.ConnectionUtil;
import applab.client.search.utils.GoogleMapUtils;
import applab.client.search.utils.IctcCKwUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by skwakwa on 8/28/15.
 */
public class FarmMapping extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    PolylineOptions options = new PolylineOptions();
    JSONArray mapPoints = new JSONArray();
    Farmer farmer =null;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    final GeometryFactory gf = new GeometryFactory();
    Polygon polygon = null;
    ArrayList<Coordinate> points = new ArrayList<Coordinate>();
    List<LatLng> lats = new ArrayList<LatLng>();
    List<LatLng> listed = new ArrayList<LatLng>();
    DatabaseHelper dbHelper=null;
    static final double EARTH_RADIUS = 6371009;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_farm_mapping);


        setUpMapIfNeeded();

        dbHelper = new DatabaseHelper(getBaseContext());

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");
                farmer = dbHelper.findFarmer(farmer.getFarmID());
                TextView fm = (TextView) findViewById(R.id.txt_map_fm_farmer);
                fm.setText(farmer.getFullname());

                fm = (TextView) findViewById(R.id.txt_map_fm_crop);
                fm.setText(farmer.getMainCrop());

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                }else{
                    showGPSDisabledAlertToUser();
                }

                fm = (TextView) findViewById(R.id.txt_map_fm_loc);
                fm.setText(farmer.getCommunity());
                showDialog("Note of Farm Measurement", "Follow these steps to measure the "+
                        farmer.getFullname()
                        +"'s farm: \n\n1. Press the map and start walking \n 2.Walk around the area to be cultivated. \n" +
                        "\n" +
                        "2. Tap the screen every 5 steps. You must select at least 3 points before you finish walking around the farm. \n" +
                        "\n" +
                        "3. Once you have gone around the farm, tap and hold the screen until you see a number. That is the area of your farm in square metres. ");

                List<FarmGPSLocation> gps = dbHelper.getFarmerCoordinates(farmer.getFarmID());


                System.out.println("GOS  : "+gps.size());

                TextView fArea = (TextView) findViewById(R.id.txt_map_fm_area);
//            fArea.setText((farmer.getLandArea())+" m2  ");
                fArea.setText(Html.fromHtml(": "+IctcCKwUtil.formatDouble(farmer.getLandArea()) + " m<sup>2</sup> Perimeter : " + IctcCKwUtil.formatDouble(farmer.getSizePlot()) + " m "));
                fArea = (TextView) findViewById(R.id.txt_coordinate_no);
                fArea.setText(String.valueOf(gps.size()));
                for (FarmGPSLocation gpsLoc : gps) {


//                    mMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(gpsLoc.getLatitude(), gpsLoc.getLongitude()))
//                            .title("Farm Map Point " + farmer.getLandArea()));
                    options.add(new LatLng(gpsLoc.getLatitude(), gpsLoc.getLongitude()));

                }





                setUpMapIfNeeded();

//                "1. Walk around the area to be cultivated. \n" +
//                        "\n" +
//                        "2. Tap the screen every 5 steps. You must select at least 3 points before you finish walking around the farm. \n" +
//                        "\n" +
//                        "3. Once you have gone around the farm, tap and hold the screen until you see a number. That is the area of your farm in square metres. ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                locateMe();
                setUpMap();
                if(options.getPoints().size()>1)
                {
                    options.color( Color.parseColor("#CC0000FF") );
                    options.width( 5 );
                    options.visible( true );
                    mMap.addPolyline(options);
                }
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {

        try{

            Toast.makeText(getApplicationContext(),
                    "" + mMap.getMyLocation().getLatitude() + mMap.getMyLocation().getLongitude(),
                    Toast.LENGTH_LONG).show();

            points.add(new Coordinate(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

            options.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            if(options.getPoints().size()>1)
            {
                options.color( Color.parseColor("#CC0000FF") );
                options.width( 5 );
                options.visible( true );
                mMap.addPolyline(options);
            }



//        dbHelper.saveGPSLocation(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude(),farmer.getFarmID());
            listed.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            try {
                JSONObject cordinates = new JSONObject();
                cordinates.put("lat",mMap.getMyLocation().getLatitude());
                cordinates.put("lng",mMap.getMyLocation().getLongitude());
                mapPoints.put(cordinates);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listed.add(new LatLng(mMap.getMyLocation().getLatitude(),  mMap.getMyLocation().getLongitude()));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mMap.getMyLocation().getLatitude(),  mMap.getMyLocation().getLongitude()))
                    .title("Farm Map Point "+points.size()));
        }
        catch (Exception e){


        }



    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if(points.size()>0)
            points.add(points.get(0));
        for(Coordinate point : points){
            System.out.println(point.x+" - "+point.y);
        }


        if(points.size()>3) {
            polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
                    .toArray(new Coordinate[points.size()])), gf), null);
            //double area = (polygon.getArea() * (Math.PI / 180) * 6378137) * 100000;
            double area = computeSignedArea(listed,EARTH_RADIUS);
            double perimeter = computeLength(listed);
            Toast.makeText(getApplicationContext(),
                    "" + polygon.getArea(),
                    Toast.LENGTH_LONG).show();
            DecimalFormat df = new DecimalFormat("#.000000");

            TextView fm = (TextView) findViewById(R.id.txt_map_fm_area);
            fm.setText(Html.fromHtml(IctcCKwUtil.formatDouble(area) + " m<sup>2</sup> Perimeter : "+IctcCKwUtil.formatDouble(perimeter)+" m"));

            fm = (TextView) findViewById(R.id.txt_coordinate_no);

            Toast.makeText(getApplicationContext(),
                    "" + df.format(area),
                    Toast.LENGTH_LONG).show();

            dbHelper.deleteFarmerGPS(farmer.getFarmID());
            System.out.println("Saving Points");
            for (Coordinate coordinate : points) {
               long  l =  dbHelper.saveGPSLocation(coordinate.x, coordinate.y, farmer.getFarmID());
                System.out.println("Done saving  Pnts: "+l);
            }
            dbHelper.updateFarmer(farmer.getFarmID(), area,perimeter);



            JSONObject l = new JSONObject();
            try {
                l.put("points",mapPoints);
                l.put("area",area);
                l.put("perimeter",perimeter);
                ConnectionUtil.refreshFarmerInfo(getBaseContext(),null, "fid="+farmer+"&l="+URLEncoder.encode(l.toString()),"fmap","Syncing Farm Mapping");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

            Toast.makeText(getApplicationContext(),
                    "You Need more than  3 coordinate  before you can make Calcuate the area" ,
                    Toast.LENGTH_LONG).show();

        }
        setUpMapIfNeeded();

    }


    public void locateMe()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(20)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }


    /**
     * Returns the signed area of a closed path on a sphere of given radius.
     * The computed area uses the same units as the radius squared.
     *
     */
    static double computeSignedArea(List<LatLng> path, double radius) {
        int size = path.size();
        if (size < 3) { return 0; }
        double total = 0;
        LatLng prev = path.get(size - 1);
        double prevTanLat = tan((PI / 2 - toRadians(prev.latitude)) / 2);
        double prevLng = toRadians(prev.longitude);
        // For each edge, accumulate the signed area of the triangle formed by the North Pole
        // and that edge ("polar triangle").
        for (LatLng point : path) {
            double tanLat = tan((PI / 2 - toRadians(point.latitude)) / 2);
            double lng = toRadians(point.longitude);
            total += polarTriangleArea(tanLat, lng, prevTanLat, prevLng);
            prevTanLat = tanLat;
            prevLng = lng;
        }
        return total * (radius * radius);
    }

    /**
     * Returns the length of the given path, in meters, on Earth.
     */
    public static double computeLength(List<LatLng> path) {
        if (path.size() < 2) {
            return 0;
        }
        double length = 0;
        LatLng prev = path.get(0);
        double prevLat = toRadians(prev.latitude);
        double prevLng = toRadians(prev.longitude);
        for (LatLng point : path) {
            double lat = toRadians(point.latitude);
            double lng = toRadians(point.longitude);
            length += distanceRadians(prevLat, prevLng, lat, lng);
            prevLat = lat;
            prevLng = lng;
        }
        return length * EARTH_RADIUS;
    }


    /**
     * Returns distance on the unit sphere; the arguments are in radians.
     */
    private static double distanceRadians(double lat1, double lng1, double lat2, double lng2) {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }

    private static double polarTriangleArea(double tan1, double lng1, double tan2, double lng2) {
        double deltaLng = lng1 - lng2;
        double t = tan1 * tan2;
        return 2 * atan2(t * sin(deltaLng), 1 + t * cos(deltaLng));
    }

    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }


    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }

    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }

    public void showDialog(final String title,final String  msg) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(FarmMapping.this);

        builder.setTitle(title);

        builder.setMessage(msg);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

//        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                dialog.dismiss();
//            }
//        });

        builder.show();
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}


