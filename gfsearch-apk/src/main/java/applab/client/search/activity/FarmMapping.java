package applab.client.search.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import applab.client.search.R;
import applab.client.search.location.GpsManager;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import java.util.ArrayList;

/**
 * Created by skwakwa on 8/28/15.
 */
public class FarmMapping extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

private GoogleMap mMap; // Might be null if Google Play services APK is not available.
final GeometryFactory gf = new GeometryFactory();
        Polygon polygon = null;
        ArrayList<Coordinate> points = new ArrayList<Coordinate>();
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        points.add(new Coordinate(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));




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
        ""+area,
        Toast.LENGTH_LONG).show();
        }


        }
// Activity {
//    final GeometryFactory gf = new GeometryFactory();
//
//    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
////    final    GpsManager gpsInstance =  GpsManager.getInstance();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.acttivity_farm_mapping);
//    }
//
//
//    public void getMapping(View view) {
//
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_coordinates);
//
//        double lat = 0;
//        double lon = 0;
//        try {
//            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//            boolean enabled = service
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (enabled) {
//
//                System.out.println("System Enabled");
//                Location location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////                Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//                lat = location.getLatitude();
//                lon = location.getLongitude();
//                System.out.println("lon " + lon + " : lat " + lat);
//                // Add textview 1
//                TextView textView1 = new TextView(this);
//                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT));
//                textView1.setText("Coordinates  : " + lon + " , " + lat);
////        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
//                textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
//                linearLayout.addView(textView1);
//                System.out.println("Sont ");
//                Button btn = (Button) findViewById(R.id.btn_cal_area);
//
//                if (points.size() > 3)
//                    btn.setVisibility(Button.VISIBLE);
//                else
//                    btn.setVisibility(Button.INVISIBLE);
//
//                points.add(new Coordinate(lon, lat));
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(getBaseContext(), "Unable to take coordinates " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    /**
//     * @param view
//     */
//    public void getArea(View view) {
//        try {
//            final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
//                    .toArray(new Coordinate[points.size()])), gf), null);
//            double area = polygon.getArea();
//            double perimeter = polygon.getLength();
//            Toast.makeText(getBaseContext(), "Area  : " + area + " Perimieter : " + perimeter, Toast.LENGTH_LONG).show();
//
//        } catch (Exception e) {
//
//        }
//    }
//
//}