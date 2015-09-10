package applab.client.search.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import applab.client.search.activity.DashboardActivity;
import applab.client.search.storage.DatabaseHelper;
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

/**
 * Created by skwakwa on 9/4/15.
 */
public class ConnectionUtil {

    public static void refreshFarmerInfo(){

    }
    public static void refreshFarmerInfo(final Context context,final Intent intent, final String queryString, final String type,String msg ) {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";
       final  DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Toast.makeText(context, msg,
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL + "action="+type;
                    if(queryString.length()>0)
                        url+="&"+queryString;

                    JSONObject j = new JSONObject();
                    System.out.println("URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    System.out.println("After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    System.out.println("Done");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                        serverResponse += line;
                    }

//                    System.out.println("Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse,type);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg,String type) {

                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    b.putString("type", type);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String type  = msg.getData().getString("type");
                        String aResponse = msg.getData().getString("message");

                        if(type.equalsIgnoreCase("login")){

                            try {


                            }catch(Exception e){

                            }

                        }else if(type.equalsIgnoreCase("details")){
                            if ((null != aResponse)) {

                                ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                                // test.setText(aResponse);

                                try {
                                    JSONObject resp = new JSONObject(aResponse);

                                    JSONArray farmers = (JSONArray) resp.get("farmer");

                                    databaseHelper.resetFarmer();
                                    int farmersCnt = 0;
                                    for (int i = 0; i < farmers.length(); i++) {
                                        JSONObject farmer = farmers.getJSONObject(i);
//                                    System.out.println("Farmer Name  : " + farmer.getString("lname"));
//                                    cluster1List.add((String) j.get("farmer"));
//
                                        //   String []  keys =  {"fname","lname","nickname","community","village","district","ms","age","gender","ms","noc","nod","edu","cluster","id","maincrop"};
                                        String[] keys
                                                = {

                                                "fname", "lname", "nickname", "communy", "village", "district", "region", "age", "gender", "ms", "noc", "nod", "edu", "cluster", "id",
                                                "sizeplot", "labour", "date_land_ident", "loc_land", "target_area",
                                                "exp_price_ton", "variety", "target_nxt_season", "techneed1", "techneed2",
                                                "fbo",
                                                "landarea", "date_plant", "date_man_weed", "pos_contact", "mon_sell_start", "mon_fin_pro_sold",
                                                "maincrop"
                                        };
                                        String[] vals = new String[keys.length];
                                        int cnt = 0;
                                        for (String key : keys) {
                                            try {
                                                vals[cnt] = farmer.getString(key);
                                            } catch (Exception e) {
                                                vals[cnt] = "";
                                            }
                                            cnt++;
                                        }



                                        databaseHelper.saveFarmer(vals[0], vals[1], vals[2], vals[3],
                                                vals[4], vals[5], vals[6], vals[7], vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14],
                                                vals[15], vals[16], vals[17], vals[18],
                                                vals[19], vals[20], vals[21], vals[22], vals[23], vals[24], vals[25], vals[26], vals[27], vals[28], vals[29], vals[30], vals[31], vals[32]);

//                                    farmersCnt++;
                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // ALERT MESSAGE
                                Toast.makeText(
                                       context,
                                        "Data Recieved: ",

                                        Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
                                if(null != intent)
                                    context.startActivity(intent);

                            } else {

                                //ALERT MESSAGE
                                Toast.makeText(
                                        context,
                                        "Not Got Response From Server.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }

}