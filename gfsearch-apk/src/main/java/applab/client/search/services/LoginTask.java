package applab.client.search.services;

/**
 * Created by skwakwa on 8/4/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import applab.client.search.model.Payload;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.synchronization.SubmitListener;
import applab.client.search.utils.HttpHelpers;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class LoginTask extends AsyncTask<Payload, Object, Payload> {

    public static final String TAG = LoginTask.class.getSimpleName();

    private Context ctx;
    private SharedPreferences prefs;
    private SubmitListener mStateListener;

    public LoginTask(Context c) {
        this.ctx = c;
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Override
    protected Payload doInBackground(Payload... params) {

        Payload payload = params[0];

        JSONObject json = new JSONObject();
        String url = IctcCkwIntegrationSync.ICTC_SERVER_URL;
        Log.d(TAG, "logging in url...." + IctcCkwIntegrationSync.ICTC_SERVER_URL);

        HttpPost httpPost = new HttpPost(url);
        try {
            // update progress dialog
            Log.d(TAG, "logging in usrname....");

            // add post params;
            StringEntity se = new StringEntity(json.toString(), "utf8");
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);

            // make request
            HttpResponse response = HttpHelpers.postDataRequestAndGetRequest(url, null, "application/text");

            // read response
            InputStream content = response.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content), 4096);
            String responseStr = "";
            String s = "";

            while ((s = buffer.readLine()) != null) {
                responseStr += s;
            }

            Log.d(TAG, "responseStr in ...." + responseStr);
            // check status code
            switch (response.getStatusLine().getStatusCode()) {
                case 400: // unauthorised

                    System.out.println("Unauthorised");

                    payload.setResult(false);
                    break;
                case 201: // logged in
                    System.out.println("Good Data");
                    ArrayList<String> strs = new ArrayList<String>();
                    JSONObject jsonResp = new JSONObject(responseStr);
                    strs.add(responseStr);
                    payload.setData(strs);
                    payload.setResult(true);
                    break;
                default:
                    System.out.println("Noting : " + response.getStatusLine().getStatusCode());
                    Log.d(TAG, responseStr);
                    payload.setResult(false);
            }


        } catch (UnsupportedEncodingException e) {
            payload.setResult(false);
        } catch (ClientProtocolException e) {
            payload.setResult(false);
        } catch (IOException e) {
            payload.setResult(false);
        } catch (JSONException e) {
            e.printStackTrace();
            payload.setResult(false);
        } finally {

        }
        return payload;
    }

    @Override
    protected void onPostExecute(Payload response) {
        synchronized (this) {
            if (mStateListener != null) {
                System.out.println("Updat er submitComplete login task: ");
                mStateListener.submitComplete(response);
            }
        }
    }

    public void setLoginListener(SubmitListener srl) {
        synchronized (this) {
            mStateListener = srl;
        }
    }
}
