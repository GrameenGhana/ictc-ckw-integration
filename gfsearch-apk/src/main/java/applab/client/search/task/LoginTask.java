package applab.client.search.task;

import android.os.AsyncTask;
import android.util.Log;

import applab.client.search.model.Payload;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class LoginTask extends AsyncTask<Payload, Object, Payload> {

	public static final String TAG = LoginTask.class.getSimpleName();

	private APIRequestListener mStateListener;
	
	public LoginTask() { }

	public void setListener(APIRequestListener	 srl) {
		synchronized (this) {
			mStateListener = srl;
		}
	}

	@Override
	protected Payload doInBackground(Payload... params) {

		Payload payload = params[0];
		String username = (String) payload.getData().get(0);
		String password = (String) payload.getData().get(1);

		try {
			String serverResponse = "";
			String uri = IctcCkwIntegrationSync.AGSMO_LOGIN_API + "username="+username+"&password="+password;

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(uri);
			HttpResponse resp = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			String line;
			while ((line = rd.readLine()) != null) {
				serverResponse += line;
			}

			Log.d(TAG, serverResponse);

			if(serverResponse.isEmpty()){
				payload.setResult(false);
				payload.setResultResponse("Invalid username or password");
			} else{
                JSONObject obj = new JSONObject(serverResponse);
                if(obj.getBoolean("success")) {
                    payload.setResult(true);
                    payload.getResponseData().add(obj.getJSONObject("data"));
                    payload.setResultResponse("Login successful");
                } else {
                    payload.setResult(false);
                    payload.setResultResponse(obj.getString("message"));
                }
			}

        } catch (IOException e) {
            payload.setResult(false);
            payload.setResultResponse("Error connecting to server");

		} catch (JSONException e) {
			e.printStackTrace();
			payload.setResult(false);
			payload.setResultResponse("Error processing results from server");
		}

		return payload;
	}

	@Override
	protected void onPostExecute(Payload response) {
		synchronized (this) {
            if (mStateListener != null) {
               mStateListener.apiRequestComplete(response);
            }
        }
	}
}
