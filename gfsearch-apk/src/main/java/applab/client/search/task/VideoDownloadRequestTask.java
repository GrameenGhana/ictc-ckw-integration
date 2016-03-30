package applab.client.search.task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import applab.client.search.ApplicationRegistry;
import applab.client.search.model.Payload;
import applab.client.search.model.wrapper.VideoData;
import applab.client.search.model.wrapper.VideoRequestData;
import applab.client.search.model.wrapper.VideosRequestWrapper;
import applab.client.search.model.wrapper.VideosResponseWrapper;
import applab.client.search.settings.SettingsConstants;
import applab.client.search.settings.SettingsManager;
import applab.client.search.synchronization.SynchronizationManager;
import applab.client.search.utils.*;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class VideoDownloadRequestTask extends AsyncTask<Payload, Object, Payload>{

    public static final String TAG = VideoDownloadRequestTask.class.getSimpleName();

    protected Context ctx;
    private ProgressDialog pDialog;

    private SynchronizationManager syncManager = null;
    private VideoDownloadRequestListener requestListener = null;

    public VideoDownloadRequestTask(Context ctx) {
        this.ctx = ctx;
    }

    public VideoDownloadRequestTask(Context ctx, SynchronizationManager sm) {
            this.ctx = ctx;
            this.syncManager = sm;
    }

    public void setRequestListener(VideoDownloadRequestListener listener) {
        synchronized (this) {
            requestListener = listener;
        }
    }

    protected Dialog showDialog() {
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Downloading video file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
        return pDialog;
    }

    protected void closeDialog() {

        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    protected void updateNotification(int counter, int count, boolean error) {
        if (syncManager==null) {
            if (error) {
                publishProgress("Error downloading videos");
            } else {
                publishProgress("" + ((counter * 100) / count));
            }
        } else  {
            if (error) {
                //syncManager.notifySynchronizationListeners("onSynchronizationError", new Throwable("Error downloading videos"));
            } else {
                syncManager.notifySynchronizationListeners("synchronizationUpdate", counter, count, "Downloading videos", true);
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (syncManager==null) {
            showDialog();
        }
    }

    @Override
    protected Payload doInBackground(Payload... payloadParams) {

        Payload payload = payloadParams[0];
        ArrayList<Object> items = (ArrayList<Object>) payload.getData();
        VideoRequestData vrd = null;
        for(Object o : items) { vrd = (VideoRequestData) o; }

        if (vrd != null) {
            List<String> videoIds = vrd.getVideoIds();
            String videosVersion = vrd.getVideosVersion();

            System.out.println("TMEDIA Download Videos cnt : " + videoIds.size() + " Version: "+videosVersion);

            if (videoIds.size() > 0) {
                int count = videoIds.size(), counter = 0;

                if (MediaUtils.storageReady() && MediaUtils.createRootFolder()) {

                    for (final String videoId : videoIds) {

                        if (videoId != null || videoId.trim().length() > 0) {

                            counter++;
                            updateNotification(counter, count, false);

                            // Only download video if video does not already exist!
                            if (!MediaUtils.fileExists(videoId, false)) {

                                Log.d("TMEDIA: Video Download", "Getting " + videoId);

                                String url = SettingsManager.getInstance().getValue(SettingsConstants.KEY_SERVER);

                                VideosRequestWrapper request = new VideosRequestWrapper();
                                request.setRequest(SettingsConstants.REQUEST_DOWNLOAD_VIDEOS);
                                request.setImei(DeviceMetadata.getDeviceImei(ApplicationRegistry.getApplicationContext()));
                                List<String> ids = new ArrayList<String>();
                                ids.add(videoId);
                                request.setVideoIds(ids);

                                try {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(request);


                                    int networkTimeout = 10 * 60 * 1000;
                                    List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                                    params.add(new BasicNameValuePair(SettingsConstants.REQUEST_METHODNAME,
                                            SettingsConstants.REQUEST_DOWNLOAD_VIDEOS));
                                    params.add(new BasicNameValuePair(SettingsConstants.REQUEST_DATA, jsonRequest));
                                    InputStream inputStream = HttpHelpers.postJsonRequestAndGetStream(url, networkTimeout, params);

                                    String jsonResponse = new java.util.Scanner(inputStream).useDelimiter("\\A").next();
                                    //Log.d("TMEDIA: Video Download", "Response " + jsonResponse);

                                    if (jsonResponse.length() > 4972) {
                                        //VideosResponseWrapper res = gson.fromJson(jsonResponse, VideosResponseWrapper.class);
                                        FileOutputStream out = null;

                                        //if (res != null && res.getResultCode().equals("0")) {
                                        //    for (VideoData video : res.getVideoResults()) {
                                               // byte[] arr = Base64.decode((video.getVideoData()), Base64.DEFAULT);
                                        byte[] arr = Base64.decode(jsonResponse, Base64.DEFAULT);
                                                out = new FileOutputStream(new File(MediaUtils.MEDIA_ROOT, videoId + ".mp4"));
                                                out.write(arr);
                                        //    }
                                            if (out != null) {
                                                out.close();
                                            }
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            payload.setResult(true);
                                            payload.setResultResponse(videoId);
                                        //} else {
                                        //    payload.setResultResponse(res.getResultMassage());
                                       // }
                                    } else {
                                        Log.e(TAG, "TMEDIA: Could not find file "+videoId);
                                    }
                                } catch (Exception ex) {
                                    payload.setResult(false);
                                    payload.setResultResponse(ex.getMessage());
                                    Log.e(TAG, "TMEDIA: Video parsing Error", ex);
                                    updateNotification(0,0,true);
                                }
                            }
                        }
                    }

                    if (payload.isResult()) {
                        SettingsManager.getInstance().setValue(SettingsConstants.KEY_VIDEOS_VERSION, videosVersion);
                    }
                } else {
                    payload.setResultResponse("Media storage not ready");
                }
            }
        }

        return payload;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        if (syncManager == null) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }
    }

    @Override
    protected void onPostExecute(Payload response) {
        if (syncManager==null) {
            closeDialog();
        }

        synchronized (this) {
            if (requestListener != null) {
                requestListener.VideoDownloadRequestComplete(response);
            }
        }
    }
}