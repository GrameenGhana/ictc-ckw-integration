package applab.client.search.activity;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.utils.ConnectionUtil;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by skwakwa on 8/28/15.
 */
public class FarmMapping extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

private GoogleMap mMap; // Might be null if Google Play services APK is not available.
 PolylineOptions options = new PolylineOptions();

    JSONArray mapPoints = new JSONArray();

final GeometryFactory gf = new GeometryFactory();
        Polygon polygon = null;
        ArrayList<Coordinate> points = new ArrayList<Coordinate>();
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_farm_mapping);

        setUpMapIfNeeded();
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

        Toast.makeText(getApplicationContext(),
        ""+mMap.getMyLocation().getLatitude() + mMap.getMyLocation().getLongitude(),
        Toast.LENGTH_LONG).show();

    mMap.addMarker(new MarkerOptions()
            .position(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())));

       if(options.getPoints().size()>1)
       {
           options.color( Color.parseColor("#CC0000FF") );
           options.width( 5 );
           options.visible( true );
           mMap.addPolyline(options);
       }
    options.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
    points.add(new Coordinate(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));


    try {
        JSONObject cordinates = new JSONObject();
        cordinates.put("lat",mMap.getMyLocation().getLatitude());
        cordinates.put("lng",mMap.getMyLocation().getLongitude());
         mapPoints.put(cordinates);
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

@Override
public void onMapLongClick(LatLng latLng) {
        if(points.size()>0)
        points.add(points.get(0));
        for(Coordinate point : points){
        System.out.println(point.x+" - "+point.y);
        }
        polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
        .toArray(new Coordinate[points.size()])), gf), null);
        double area = polygon.getArea() *  (Math.PI/180) * 6378137;

        Toast.makeText(getApplicationContext(),
        ""+polygon.getArea(),
        Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(),
        "Area is "+area,
        Toast.LENGTH_LONG).show();

        JSONObject l = new JSONObject();
    try {
        l.put("points",mapPoints);
        l.put("area",area);
        ConnectionUtil.refreshFarmerInfo(getBaseContext(),null,"l="+l.toString(),"fmap","Syncing Farm Mapping");
    } catch (JSONException e) {
        e.printStackTrace();
    }

}


    public void locateMe()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 15));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

}



