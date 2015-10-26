package applab.client.search.task;

/**
 * Created by skwakwa on 10/15/15.
 */
import applab.client.search.R;
import applab.client.search.application.IctcCkwIntegration;
import applab.client.search.model.Payload;
import applab.client.search.model.TrackerLog;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.HTTPConnectionUtil;
import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
//
//import com.bugsense.trace.BugSenseHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class SubmitTrackerMultipleTask extends AsyncTask<Payload, Object, Payload> {

    public final static String TAG = SubmitTrackerMultipleTask.class.getSimpleName();

    private Context ctx;
    private SharedPreferences prefs;

    public SubmitTrackerMultipleTask(Context ctx) {
        this.ctx = ctx;
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Override
    protected Payload doInBackground(Payload... params) {
        DatabaseHelper db = new DatabaseHelper(ctx);
        Payload payload = db.getCCHUnsentLog();
        db.close();

        @SuppressWarnings("unchecked")
        Collection<Collection<TrackerLog>> result = (Collection<Collection<TrackerLog>>) split((Collection<Object>) payload.getData(), IctcCkwIntegration.MAX_TRACKER_SUBMIT);

        HTTPConnectionUtil client = new HTTPConnectionUtil(ctx);

        String url =client.getFullURL(IctcCkwIntegration.TRACKER_SUBMIT_PATH);

//        HttpPatch httpPatch = new HttpPatch(url);
        HttpPost httpPatch = new HttpPost(url);
        for (Collection<TrackerLog> trackerBatch : result) {
            String dataToSend = createDataString(trackerBatch);

            try {

                StringEntity se = new StringEntity(dataToSend,"utf8");
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPatch.setEntity(se);

//                httpPatch.addHeader(client.getAuthHeader());

                Log.d(TAG,url);
                Log.d(TAG,dataToSend);

                // make request
                HttpResponse response = client.execute(httpPatch);

                Log.d(TAG,String.valueOf(response.getStatusLine().getStatusCode()));

                InputStream content = response.getEntity().getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content), 4096);
                String responseStr = "";
                String s = "";

                while ((s = buffer.readLine()) != null) {
                    responseStr += s;
                }

                Log.d(TAG,responseStr);


                switch (response.getStatusLine().getStatusCode()){
                    case 200: // submitted
                        DatabaseHelper dbh = new DatabaseHelper(ctx);
                        for(TrackerLog tl: trackerBatch){
                            dbh.markCCHLogSubmitted(tl.getId());
                        }
                        dbh.close();
                        payload.setResult(true);
                        // update points
                        JSONObject jsonResp = new JSONObject(responseStr);


                        try {
                            JSONObject metadata = jsonResp.getJSONObject("metadata");
//                            MetaDataUtils mu = new MetaDataUtils(ctx);
//                            mu.saveMetaData(metadata, prefs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 400: // submitted but invalid digest - returned 400 Bad Request - so record as submitted so doesn't keep trying
                        DatabaseHelper dbh2 = new DatabaseHelper(ctx);
                        for(TrackerLog tl: trackerBatch){
                            dbh2.markCCHLogSubmitted(tl.getId());
                        };
                        dbh2.close();
                        payload.setResult(true);
                        break;
                    default:
                        payload.setResult(false);
                }

            } catch (UnsupportedEncodingException e) {
                payload.setResult(false);
            } catch (ClientProtocolException e) {
                payload.setResult(false);
            } catch (IOException e) {
                payload.setResult(false);
            } catch (JSONException e) {

                payload.setResult(false);
            }
        }

        return payload;
    }

    protected void onProgressUpdate(String... obj) {
        // do nothing
    }

    @Override
    protected void onPostExecute(Payload p) {
        // reset submittask back to null after completion - so next call can run properly
        IctcCkwIntegration app = (IctcCkwIntegration) ctx.getApplicationContext();
        app.omSubmitTrackerMultipleTask = null;
    }

    private static Collection<Collection<TrackerLog>> split(Collection<Object> bigCollection, int maxBatchSize) {
        Collection<Collection<TrackerLog>> result = new ArrayList<Collection<TrackerLog>>();

        ArrayList<TrackerLog> currentBatch = null;
        for (Object obj : bigCollection) {
            TrackerLog tl = (TrackerLog) obj;
            if (currentBatch == null) {
                currentBatch = new ArrayList<TrackerLog>();
            } else if (currentBatch.size() >= maxBatchSize) {
                result.add(currentBatch);
                currentBatch = new ArrayList<TrackerLog>();
            }

            currentBatch.add(tl);
        }

        if (currentBatch != null) {
            result.add(currentBatch);
        }

        return result;
    }

    private String createDataString(Collection<TrackerLog> collection){
        String s = "{\"objects\":[";
        int counter = 0;
        for(TrackerLog tl: collection){
            counter++;
            s += tl.getContent();
            if(counter != collection.size()){
                s += ",";
            }
        }
        s += "]}";
        return s;
    }

}
