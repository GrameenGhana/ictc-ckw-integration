package applab.client.search.task;


import applab.client.search.application.IctcCkwIntegration;
import applab.client.search.model.Payload;
import applab.client.search.model.TrackerLog;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.utils.HTTPConnectionUtil;
import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.entity.UrlEncodedFormEntity;

        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.StringEntity;
        import org.apache.http.message.BasicHeader;
        import org.apache.http.protocol.HTTP;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.preference.PreferenceManager;
        import android.util.Log;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class IctcTrackerLogTask extends AsyncTask<Payload, Object, Payload> {

    public final static String TAG = IctcTrackerLogTask.class.getSimpleName();

    private Context ctx;

    public IctcTrackerLogTask(Context ctx) {
        System.out.println("Payload IctcTrackerLogTask");
        this.ctx = ctx;
    }

    @Override
    protected Payload doInBackground(Payload... params) {
        System.out.println("Payload Background");
        Payload payload = params[0];

        @SuppressWarnings("unchecked")
        Collection<Collection<TrackerLog>> result = (Collection<Collection<TrackerLog>>) split((Collection<Object>) payload.getData(), IctcCkwIntegration.MAX_TRACKER_SUBMIT);
        System.out.println("Payload Background I");
        HTTPConnectionUtil client = new HTTPConnectionUtil(ctx);
        System.out.println("Payload Background II");
        String url = client.getFullURL(IctcCkwIntegration.TRACKER_SUBMIT_PATH);
        System.out.println("Payload Url : "+url);
        System.out.println("Payload Background III");
        //HttpPatch httpPatch = new HttpPatch(url);
        HttpPost httpPatch = new HttpPost(url);
        System.out.println("Payload Background IIII");
        for (Collection<TrackerLog> trackerBatch : result) {
            String dataToSend = createDataString(trackerBatch);

            try {
                System.out.println("Payload Background IV");
                List<NameValuePair> nameValuePairs = client.postData(dataToSend);
                httpPatch.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                httpPatch.addHeader("Authorization", client.getAuthHeader());

                //Log.v(TAG,url);
                //Log.v(TAG,dataToSend);

                System.out.println("Payload TO Execute");
                // make request
                HttpResponse response = client.execute(httpPatch);
                System.out.println("Payload TO Execute II");
                //Log.d(TAG, String.valueOf(response.getStatusLine().getStatusCode()));

                InputStream content = response.getEntity().getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content), 4096);
                String responseStr = "";
                String s = "";

                System.out.println("Responce : "+responseStr);
                while ((s = buffer.readLine()) != null) {
                    responseStr += s;
                }

                Log.d(TAG,responseStr);
                System.out.println("Payload Background V :"+responseStr);
                switch (response.getStatusLine().getStatusCode()){
                    case 200: // submitted
                        DatabaseHelper dbh = new DatabaseHelper(ctx);
                        try {
                            JSONObject obj = new JSONObject(responseStr);
                            String ids = obj.getString("ids");

//                            for(TrackerLog tl: trackerBatch){
                                dbh.markCCHLogsAsSubmitted(ids);
//                            }
                            dbh.close();
                        }catch(Exception e){

                        }

                        payload.setResult(true);
                        break;

                    case 400: // submitted but invalid request - returned 400 Bad Request - so record as submitted so doesn't keep trying
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
            }
        }

        return payload;
    }

    protected void onProgressUpdate(String... obj) {
        // do nothing
    }

    @Override
    protected void onPostExecute(Payload p) {
        // reset submit task back to null after completion - so next call can run properly
//        IctcCkwIntegration app = (IctcCkwIntegration) ctx.getApplicationContext();
//        app.omUpdateCCHLogTask = null;
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
        String s = "{\"logs\":[";
        int counter = 0;
        for(TrackerLog tl: collection){
            counter++;
            s += tl.getContent();
            if(counter != collection.size()){ s += ","; }
        }
        s += "]}";

        System.out.println("Payload : "+s);
        return s;
    }

}
