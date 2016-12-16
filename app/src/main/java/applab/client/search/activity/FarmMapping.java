package applab.client.search.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import applab.client.search.R;
import applab.client.search.model.FarmGPSLocation;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.utils.IctcCKwUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by skwakwa on 8/28/15.
 */
public class FarmMapping extends BaseFragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    PolylineOptions options = new PolylineOptions();
    JSONArray mapPoints = new JSONArray();
    Farmer farmer = null;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    final GeometryFactory gf = new GeometryFactory();
    Polygon polygon = null;
    ArrayList<Coordinate> points = new ArrayList<Coordinate>();
    List<LatLng> lats = new ArrayList<LatLng>();
    List<LatLng> listed = new ArrayList<LatLng>();
    DatabaseHelper dbHelper = null;
    static final double EARTH_RADIUS = 6371009;
    int gpsCnt = 0;
    int newGps = 0;
    private double area;
    private double perimeter;
    private SweetAlertDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_farm_mapping);
//        setUpMapIfNeeded();
//
        dbHelper = new DatabaseHelper(getBaseContext());
        getSupportActionBar().setTitle("Farm Mapping");
        super.setDetails(dbHelper, "Farmer", "Farm Mapping");

        try {
            boolean shdClear = false;
            Integer clear = 0;
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                farmer = (Farmer) extras.get("farmer");
                clear = (Integer) extras.get("c");
                if (null == clear) {
                    clear = 0;
                }
                if (clear == 1)
                    shdClear = true;
                farmer = dbHelper.findFarmer(farmer.getFarmID());
//                TextView fm = (TextView) findViewById(R.id.txt_map_fm_farmer);
//                fm.setText(farmer.getFullname());
                String sh = "";
                try {
                    sh = (String) extras.get("sh");
                } catch (Exception e) {

                }
                IctcCKwUtil.setFarmerDetails(getWindow().getDecorView().getRootView(), R.id.ccs_layout, farmer.getFullname(), farmer, true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                } else {
                    showGPSDisabledAlertToUser();
                }


                if (!shdClear) {

//                if(!sh.equalsIgnoreCase("0")) {
                    showDialog("Note of Farm Measurement", "Follow these steps to measure the " +
                            farmer.getFullname()
                            + "'s farm: \n\n1. Press the map and start walking \n 2.Walk around the area to be cultivated. \n" +
                            "\n" +
                            "2. Tap the screen every 5 steps. You must select at least 3 points before you finish walking around the farm. \n" +
                            "\n" +
                            "3. Once you have gone around the farm, tap and hold the screen until you see a number. That is the area of your farm in acres. ");
                }

                setUpMapIfNeeded(shdClear);
//                }
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


    private void setUpMapIfNeeded() {
        setUpMapIfNeeded(false);
    }

    private void setUpMapIfNeeded(boolean clear) {
        System.out.println("Clear Coordinates : " + clear);
        List<FarmGPSLocation> gps = dbHelper.getFarmerCoordinates(farmer.getFarmID());
        System.out.println("GOS  : " + gps.size());
        gpsCnt = gps.size();
        for (FarmGPSLocation gpsLoc : gps) {
            options.add(new LatLng(gpsLoc.getLatitude(), gpsLoc.getLongitude()));

        }
        TextView fArea = (TextView) findViewById(R.id.txt_map_fm_area);
        fArea.setText(Html.fromHtml(": " + IctcCKwUtil.formatDouble(farmer.getLandArea()) + " acre ; Perimeter : " + IctcCKwUtil.formatDouble(farmer.getSizePlot()) + " m "));

        if (clear) {
            options = new PolylineOptions();
            resetMap();
            gpsCnt = 0;
            System.out.println("Clearing Coordinates ");
        }
        System.out.println("Options : ");
//            fArea.setText((farmer.getLandArea())+" m2  ");
        fArea = (TextView) findViewById(R.id.txt_coordinate_no);
        fArea.setText(String.valueOf(gps.size()));
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null || clear) {
            System.out.println("Clear Item Needed");
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                System.out.println("Clear 2 Item Needed");
                mMap.setMyLocationEnabled(true);
                locateMe();
                setUpMap();
                if (!clear) {
                    System.out.println("Ok  item  clearing");
                    if (options.getPoints().size() > 1) {
                        options.color(Color.parseColor("#CC0000FF"));
                        options.width(5);
                        options.visible(true);
                        mMap.addPolyline(options);
                    }
                }
            } else {
                System.out.println("Clear Successfullt obtained map");

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
        try {
            Toast.makeText(getApplicationContext(),
                    "" + mMap.getMyLocation().getLatitude() + mMap.getMyLocation().getLongitude(),
                    Toast.LENGTH_LONG).show();
            if (newGps == 0) {
                options = new PolylineOptions();
                points = new ArrayList<Coordinate>();
                listed = new ArrayList<LatLng>();
            }
            newGps++;


            points.add(new Coordinate(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

            options.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            if (options.getPoints().size() > 1) {
                options.color(Color.parseColor("#CC0000FF"));
                options.width(5);
                options.visible(true);
                mMap.addPolyline(options);
            }

//        dbHelper.saveGPSLocation(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude(),farmer.getFarmID());
            listed.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            try {
                JSONObject cordinates = new JSONObject();
                cordinates.put("lat", mMap.getMyLocation().getLatitude());
                cordinates.put("lng", mMap.getMyLocation().getLongitude());
                mapPoints.put(cordinates);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listed.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()))
                    .title("Farm Map Point " + points.size()));
        } catch (Exception e) {
        }
    }
    public void saveItems() {
        if (points.size() > 0)
            points.add(points.get(0));
        for (Coordinate point : points) {
            System.out.println(point.x + " - " + point.y);
        }
        if (points.size() > 3) {
            if (gpsCnt == 0) {
                processSaveCoordinates();
                newGps = 0;
            } else {
                try {
                    showConfirmCordinateSave();
                } catch (Exception e) {
                }
            }
        } else {

            Toast.makeText(getApplicationContext(),
                    "You Need more than  3 coordinate  before you can make Calcuate the area",
                    Toast.LENGTH_LONG).show();

        }
        setUpMapIfNeeded();

    }
    public void resetMap() {
        points = new ArrayList<Coordinate>();

    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        saveItems();

    }
    public void locateMe() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
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
        new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(msg).setCancelText("Close")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        })
        .show();
    }

    private void showGPSDisabledAlertToUser(){
         new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.WARNING_TYPE)
        .setContentText("GPS is disabled in your device. Would you like to enable it?")
                 .setConfirmText("Goto Settings Page To Enable GPS")
                 .setCancelText("Close")
                 .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                     @Override
                     public void onClick(SweetAlertDialog sDialog) {
                         Intent callGPSSettingIntent = new Intent(
                                 android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                         startActivity(callGPSSettingIntent);
                     }
                 }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
             @Override
             public void onClick(SweetAlertDialog sDialog) {
                 sDialog.dismissWithAnimation();
             }
         })
                 .show();

    }


    private void showConfirmCordinateSave(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Farmer Already Has coordinates")
                .setCancelable(false)
                .setPositiveButton("Coordinates Already Exist\nClicking on the OK button would overwrite them",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                        processSaveCoordinates();
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

    public void processSaveCoordinates(){
        polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
                .toArray(new Coordinate[points.size()])), gf), null);
        //double area = (polygon.getArea() * (Math.PI / 180) * 6378137) * 100000;
        area = computeSignedArea(listed,EARTH_RADIUS);
        perimeter = computeLength(listed);

        area = IctcCKwUtil.meterSqdToAcre(area);
        Toast.makeText(getApplicationContext(),
                "" + polygon.getArea(),
                Toast.LENGTH_LONG).show();

        DecimalFormat df = new DecimalFormat("#.000000");

        TextView fm = (TextView) findViewById(R.id.txt_map_fm_area);
        fm.setText(Html.fromHtml(IctcCKwUtil.formatDouble(area) + " acre; Perimeter : "+IctcCKwUtil.formatDouble(perimeter)+" m"));

        fm = (TextView) findViewById(R.id.txt_coordinate_no);

        Toast.makeText(getApplicationContext(),
                "" + df.format(area),
                Toast.LENGTH_LONG).show();

        dbHelper.deleteFarmerGPS(farmer.getFarmID());
        System.out.println("Saving Points");
        JSONArray jsonCoordinate = new JSONArray();
        for (Coordinate coordinate : points) {
            long  l =  dbHelper.saveGPSLocation(coordinate.x, coordinate.y, farmer.getFarmID());
            System.out.println("Saved GPS: "+l);
            JSONObject obj = new JSONObject();
            try {
                obj.put("x",String.valueOf(coordinate.x));
                obj.put("y",String.valueOf(coordinate.y));
                jsonCoordinate.put(obj);
            }catch (Exception e){

            }
            System.out.println("Done saving  Pnts: "+l);
        }
        JSONObject objs = new JSONObject();
        try {
            objs.put("user_id",farmer.getFarmID());
            objs.put("page","Farm Map Input");
            objs.put("area",String.valueOf(area));
            objs.put("perimeter",String.valueOf(perimeter));
            objs.put("section",farmer.getFullname());
            objs.put("coordinates",jsonCoordinate);
            objs.put("imei",IctcCKwUtil.getImei(getBaseContext()));
            objs.put("version",IctcCKwUtil.getAppVersion());
            objs.put("battery",IctcCKwUtil.getBatteryLevel(getBaseContext()));
            dbHelper.insertCCHLog("Farmer",objs.toString(),super.baseLogActivity.getStartTime(), System.currentTimeMillis());
           // sendCoordinates(String.valueOf(perimeter),String.valueOf(area));
        }catch(Exception e ){

        }

        dbHelper.updateFarmer(farmer.getFarmID(), area, perimeter);

        newGps=0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.farm_map_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.action_calculate_area) {
                saveItems();
            } else if (item.getItemId() == R.id.action_clear_coordinates) {
                Intent n  = new Intent(this,FarmMapping.class);
                n.putExtra("farmer",farmer);
                n.putExtra("c",1);
                startActivity(n);

                setUpMapIfNeeded(true);
            }else if (item.getItemId() == R.id.action_show_mapping) {
                setUpMapIfNeeded();
            }else if (item.getItemId() == R.id.action_send_coordinates) {
                sendCoordinates(farmer.getSizePlot(),  farmer.getLandArea());
            }
       } catch (Exception ex) {
            Log.e(CKWSearchActivity.class.getName(), "", ex);
        }

        return true;
    }

    public void sendCoordinates(final String perimeter, final String area){
        try {
            JSONObject j = new JSONObject();
            j.put("requestType", "measure");
            j.put("farmerId", farmer.getFarmID());
            j.put("area", area);
            j.put("perimeter", String.valueOf(perimeter));
            RequestParams params = new RequestParams();
            params.put("data", j.toString());
            params.put("method", "measurement");
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(IctcCkwIntegrationSync.ICTC_SALESFORCE_SEND_MEASUREMENT_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    pDialog = new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.setTitleText("Sending... \n" + "Area: " + area + "\n" + "Perimeter: " + perimeter);
                    pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    pDialog.show();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    System.out.println(response.toString());
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .setContentText("Sent successfully!")
                            .setCancelText("Close")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse,Throwable e) {
                    System.out.println(errorResponse.toString());
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Something went wrong")
                            .setCancelText("Close")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });


        } catch (Exception e) {
            pDialog.dismissWithAnimation();
            new SweetAlertDialog(FarmMapping.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Have you calculated the area?")
                    .setCancelText("Close")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }

}


