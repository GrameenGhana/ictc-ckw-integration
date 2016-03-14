package applab.client.search.task;

/**
 * Created by skwakwa on 10/15/15.
 */
import android.content.Context;
import android.os.AsyncTask;
import applab.client.search.R;
import applab.client.search.model.Payload;
import applab.client.search.utils.HTTPConnectionUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VideoDownloadRequestTask extends AsyncTask<Payload, Object, Payload>{

    public static final String TAG = VideoDownloadRequestTask.class.getSimpleName();
    protected Context ctx;
    private APIRequestListener requestListener;

    public VideoDownloadRequestTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Payload doInBackground(Payload... params){

        Payload payload = params[0];
        String responseStr = "";

       HTTPConnectionUtil client = new HTTPConnectionUtil(ctx);
        String url = client.getFullURL(payload.getUrl());
        HttpGet httpGet = new HttpGet(url);
//        httpGet.addHeader(client.getAuthHeader());

        try {

            // make request
            HttpResponse response = client.execute(httpGet);

            // read response
            InputStream content = response.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content), 1024);
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseStr += s;
            }


            switch (response.getStatusLine().getStatusCode()){
                // TODO check the unauthorised response code...
                case 400: // unauthorised
                    payload.setResult(false);
                    payload.setResultResponse(ctx.getString(R.string.error_login));
                    break;
                case 200:
                    payload.setResult(true);
                    payload.setResultResponse(responseStr);
                    break;
                default:
                    payload.setResult(false);
                    payload.setResultResponse(ctx.getString(R.string.error_connection));
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            payload.setResult(false);
            payload.setResultResponse(ctx.getString(R.string.error_connection));
        } catch (IOException e) {
            e.printStackTrace();
            payload.setResult(false);
            payload.setResultResponse(ctx.getString(R.string.error_connection));
        }
        return payload;
    }

    @Override
    protected void onPostExecute(Payload response) {
        synchronized (this) {
            if (requestListener != null) {
                requestListener.apiRequestComplete(response);
            }
        }
    }

    public void setAPIRequestListener(APIRequestListener srl) {
        synchronized (this) {
            requestListener = srl;
        }
    }

}