package applab.client.search.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import applab.client.search.model.Farmer;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 3/31/2016.
 */
public class Unwanted extends Activity {

    public void getSData() {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";

        Toast.makeText(getBaseContext(),
                "Please wait, connecting to server Dashboard.",
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL;

                    JSONObject j = new JSONObject();
                    System.out.println("URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    System.out.println("After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    System.out.println("Sent Response");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                        serverResponse += line;
                    }

//                    System.out.println("Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg) {

                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String aResponse = msg.getData().getString("message");

                        if ((null != aResponse)) {

                            ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                            // test.setText(aResponse);

                            try {
                                JSONObject resp = new JSONObject(aResponse);

                                JSONArray farmers = (JSONArray) resp.get("farmer");
                                List<Farmer> myFarmers = new ArrayList<Farmer>();

                                int farmersCnt = 0;
                                for (int i = 0; i < farmers.length(); i++) {
                                    JSONObject farmer = farmers.getJSONObject(i);
                                    System.out.println("Farmer Name  : " + farmer.getString("lname"));
//                                    myFarmers.add(new Farmer(farmer.getString("fname"),farmer.getString("lName"),farmer.getString("nickname"),farmer.getString("community"),farmer.getString("village"),farmer.getString("district"),farmer.getString("region"),farmer.getString("age"),farmer.getString("gender"),farmer.getString("ms"),farmer.getString("noc"),farmer.getString("nod"),farmer.getString("edu"),farmer.getString("cluster"),farmer.getString("id")));

//
//     cluster1List.add((String) j.get("farmer"));
                                    farmersCnt++;
                                }


                                //sending data into cluster 1
                                /**  Intent cluster1Intent = new Intent(MainActivity.this, Cluster1.class);
                                 Bundle b = new Bundle();
                                 b.putStringArrayList("cluster1", (ArrayList<String>) cluster1List);
                                 cluster1Intent.putExtra("clusterbundle", b);
                                 startActivity(cluster1Intent);

                                 //sending data into cluster 2
                                 Intent cluster2Intent = new Intent(MainActivity.this, Cluster2.class);
                                 Bundle b2 = new Bundle();
                                 b2.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster2Intent.putExtra("clusterbundle",b2);
                                 startActivity(cluster2Intent);


                                 //sending data into cluster 3
                                 Intent cluster3Intent = new Intent(MainActivity.this, Cluster3.class);
                                 Bundle b3 = new Bundle();
                                 b3.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster3Intent.putExtra("clusterbundle",b3);
                                 startActivity(cluster3Intent);
                                 **/


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Cluster Data Recieved: ",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            //ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Not Got Response From Server.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }

}
