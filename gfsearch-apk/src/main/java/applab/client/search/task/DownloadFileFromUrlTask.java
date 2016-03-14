package applab.client.search.task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import applab.client.search.MainActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


class DownloadFileFromURL extends AsyncTask<String, String, String> {

    private String fileName = "";

    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    public DownloadFileFromUrlTask(MainActivity activity)

    protected Dialog showDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog();
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showDialog(progress_bar_type);
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream
            fileName = Environment.getExternalStorageDirectory().toString() + "/gfsearch"+
                    f_url[0].substring(f_url[0].lastIndexOf('/'));
            OutputStream output = new FileOutputStream(fileName);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
        dismissDialog(progress_bar_type);
        try {
            Uri data = Uri.parse("file://"+fileName);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(data, "video/mp4");
            startActivity(intent);
        } catch(Exception ex) {
            Log.e("TMEDIA ERROR", ex.getMessage());
        }
    }
}
