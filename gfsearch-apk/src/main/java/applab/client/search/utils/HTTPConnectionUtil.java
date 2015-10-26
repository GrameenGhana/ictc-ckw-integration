package applab.client.search.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Base64;
import applab.client.search.R;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by skwakwa on 10/15/15.
 */
public class HTTPConnectionUtil  extends DefaultHttpClient {

    private HttpParams httpParameters;
    private SharedPreferences prefs;
    private Context ctx;
//104.236.220.225
    private static final String CCH_SERVER = "http://104.236.220.225:8080/ICTC/";
//private static final String CCH_SERVER = "http://192.168.10.240:8080/ictc-webapp/";
    private static final String CCH_API_USER= "tracker";
    private static final String CCH_API_KEY = "dog";

    public HTTPConnectionUtil(Context ctx){
        this.prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        this.ctx = ctx;
        this.httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(
                httpParameters,
                Integer.parseInt(prefs.getString(ctx.getString(R.string.prefs_server_timeout_connection),
                        ctx.getString(R.string.prefServerTimeoutConnectionDefault))));
        HttpConnectionParams.setSoTimeout(
                httpParameters,
                Integer.parseInt(prefs.getString(ctx.getString(R.string.prefs_server_timeout_response),
                        ctx.getString(R.string.prefServerTimeoutResponseDefault))));

        // add user agent
        String v = "0";
        try {
            v = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        super.setParams(httpParameters);
        super.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "ICTC Ghana Android "+ v);
    }

    public String getAuthHeader(){
        byte[] b = (CCH_API_USER+":"+CCH_API_KEY).getBytes();
        return "Basic " + Base64.encode(b, Base64.NO_WRAP);
    }

    public String getFullURL(String apiPath){
        return CCH_SERVER + apiPath;
    }

    public List<NameValuePair> postData(String data)
    {
        List<NameValuePair> pairs = new LinkedList<NameValuePair>();
        System.out.println("Payload data : "+data);
        pairs.add(new BasicNameValuePair("data", data));
        return pairs;
    }

    public String createUrlWithCredentials(String baseUrl){
        List<NameValuePair> pairs = new LinkedList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", CCH_API_USER));
        pairs.add(new BasicNameValuePair("api_key", CCH_API_KEY));
        pairs.add(new BasicNameValuePair("format", "json"));
        String paramString = URLEncodedUtils.format(pairs, "utf-8");
        if(!baseUrl.endsWith("?"))
            baseUrl += "?";
        baseUrl += paramString;
        return baseUrl;
    }

}
