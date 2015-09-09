package applab.client.search.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
public class FarmMapping extends Activity {
    final GeometryFactory gf = new GeometryFactory();

    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
//    final    GpsManager gpsInstance =  GpsManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_farm_mapping);
    }


    public void getMapping(View view) {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_coordinates);

        double lat = 0;
        double lon = 0;
        try {
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (enabled) {

                System.out.println("System Enabled");
                Location location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                lat = location.getLatitude();
                lon = location.getLongitude();
                System.out.println("lon " + lon + " : lat " + lat);
                // Add textview 1
                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setText("Coordinates  : " + lon + " , " + lat);
//        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                linearLayout.addView(textView1);
                System.out.println("Sont ");
                Button btn = (Button) findViewById(R.id.btn_cal_area);

                if (points.size() > 3)
                    btn.setVisibility(Button.VISIBLE);
                else
                    btn.setVisibility(Button.INVISIBLE);

                points.add(new Coordinate(lon, lat));
            }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Unable to take coordinates " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * @param view
     */
    public void getArea(View view) {
        try {
            final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
                    .toArray(new Coordinate[points.size()])), gf), null);
            double area = polygon.getArea();
            double perimeter = polygon.getLength();
            Toast.makeText(getBaseContext(), "Area  : " + area + " Perimieter : " + perimeter, Toast.LENGTH_LONG).show();

        } catch (Exception e) {

        }
    }

}