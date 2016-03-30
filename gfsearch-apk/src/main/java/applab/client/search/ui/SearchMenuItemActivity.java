package applab.client.search.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.activity.BaseActivity;
import applab.client.search.location.GpsManager;
import applab.client.search.model.*;
import applab.client.search.model.wrapper.VideoRequestData;
import applab.client.search.services.MenuItemService;
import applab.client.search.settings.SettingsConstants;
import applab.client.search.settings.SettingsManager;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.task.DownloadFileRequestListener;
import applab.client.search.task.VideoDownloadRequestListener;
import applab.client.search.task.DownloadFileFromUrlTask;
import applab.client.search.task.VideoDownloadRequestTask;
import applab.client.search.utils.ImageUtils;
import applab.client.search.utils.MediaUtils;
import applab.client.search.utils.PinchZoom;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


// TODO: Get video url from service
// TODO: Fix dialog for download video
// TODO: Move download video to it's own class

/**
 * Activity to display a single menu item
 */
public class SearchMenuItemActivity extends BaseActivity implements VideoDownloadRequestListener, DownloadFileRequestListener {
    public static final String EXTRA_LIST_OBJECT_IDENTIFIER = "menu_extraz";
    public static final String CLIENT_IDENTIFIER = "CLIENT_ID";
    public static final String BREAD_CRUMB = "BREAD_CRUMB";

    private ListObject searchMenuItem = null;
    private MenuItemService menuItemService = new MenuItemService();
    public static final String MEDIA_PLACEHOLDER = "\\{|\\}";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMenuItem = (ListObject) getIntent().getSerializableExtra(EXTRA_LIST_OBJECT_IDENTIFIER);
        String clientId = (String) getIntent().getSerializableExtra(CLIENT_IDENTIFIER);
        String breadCrumb = (String) getIntent().getSerializableExtra(BREAD_CRUMB);

        /** New code for dynamic layout to allow inline embedding of multimedia */
        LinearLayout outerLL = new LinearLayout(this);
        outerLL.setOrientation(LinearLayout.VERTICAL);
        outerLL.setVerticalScrollbarPosition(LinearLayout.VERTICAL);
        outerLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout innerLL = new LinearLayout(this);
        innerLL.setOrientation(LinearLayout.VERTICAL);
        innerLL.setVerticalScrollbarPosition(LinearLayout.VERTICAL);
        innerLL.setPadding(20,10,20,10);
        innerLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        String[] sections = searchMenuItem.getDescription().split(MEDIA_PLACEHOLDER);
        ArrayList<Object> widgets = new ArrayList<Object>();

        for(String s : sections)
        {
            if(s.contains("image")) {
              //TextView t = getTextView(s, 30, "#666666");
              //widgets.add(t);

            } else if(s.contains("video:")) {
                String[] params = s.split(":");
                Log.e("TMEDIA"," Working on "+params[1]);
                ImageButton videoView = getVideoView(params[1]);
                widgets.add(videoView);

            } else if(s.contains("{audio:")) {
                /*ImageView audioView = getAudioView();
                if (ContentUtils.containsAudio(content)) {
                    audioView.setImageResource(R.drawable.sound_large);
                }
                widgets.add(audioView);
                */

            } else {
                TextView t = getTextView(s, 30, "#666666");
                widgets.add(t);
            }
        }

        if (ImageUtils.imageExists(searchMenuItem.getId(), true)) {
            HorizontalScrollView v = getImageView(ImageUtils.getImageAsDrawable(this, searchMenuItem.getId(), true));
            widgets.add(v);
        }

        // Create layout
        innerLL.addView(getTextView(searchMenuItem.getLabel(), 40, "#333333"));
        for (Object o : widgets) { innerLL.addView((View) o); }
        sv.addView(innerLL);
        outerLL.addView(sv);

        generateSearchLog(searchMenuItem, clientId, breadCrumb);

        super.setContentView(outerLL);
    }

    private ImageView getAudioView() {
        ImageView audioView = new ImageView(this);
        audioView.setScaleType(ImageView.ScaleType.MATRIX);
        audioView.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { viewAudio(v); } });
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        audioView.setLayoutParams(params);
        return audioView;
    }

    private ImageButton getVideoView(String uri) {

        ImageButton videoView = new ImageButton(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                         LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(100, 10, 20, 0);
        params.height = 200;
        params.width = 300;
        videoView.setLayoutParams(params);
        videoView.setBackgroundResource(R.drawable.play);
        videoView.setContentDescription("Video");
        videoView.setTag(uri);

        videoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   viewVideo(v);
            }
        });
        return videoView;
    }

    private HorizontalScrollView getImageView(Drawable image) {
        PinchZoom pz = new PinchZoom(this);
        pz.setScaleType(ImageView.ScaleType.MATRIX);
        pz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        pz.setImageDrawable(image);

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        hsv.addView(pz);
        return hsv;
    }

    private TextView getTextView(String content, int size, String color) {
        int padding = dp2px(3);
        TextView textView = new TextView(this);
        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        textView.setTextColor(Color.parseColor(color));
        textView.setPadding(padding,padding,padding,padding);
        textView.setLineSpacing(10, 1);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(content);
        return textView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics());
    }

    public void viewVideo(View view) {

        String url = view.getTag().toString();

        try {
            String fName = Environment.getExternalStorageDirectory().toString()+ "/gfsearch/"+url+".mp4";

            if (MediaUtils.fileExists(url, false)) {
                playVideo(fName);
            } else {
                VideoRequestData vrd = new VideoRequestData(Arrays.asList(url), "videos");
                VideoDownloadRequestTask task = new VideoDownloadRequestTask(this);
                task.setRequestListener(this);
                ArrayList<Object> data = new ArrayList<Object>();
                data.add(vrd);
                Payload p = new Payload(data);
                task.execute(p);
            }

        } catch(Exception ex) {
            Log.e("TMEDIA Error", ex.getLocalizedMessage());
        }
    }

    public void VideoDownloadRequestComplete(Payload response) {
            if (response.isResult()) {
                String fName = Environment.getExternalStorageDirectory().toString()+ "/gfsearch/"+
                               response.getResultResponse()+".mp4";
                playVideo(fName);
            }
    }

    public void viewAudio(View view) {
        try {
//            Uri data = Uri.parse(ContentUtils.getAudioLocation(content));
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(getApplicationContext(), data);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//            Uri data = Uri.parse(url)i
//            intent.setDataAndType(data, "audio/mp3");
//            startActivity(intent);
        } catch (Exception e) {

        }
    }

    private void generateSearchLog(ListObject searchMenuItem, String clientId, String breadCrumb) {
        if (searchMenuItem instanceof SearchMenuItem) {
            //    public void setDetails(DatabaseHelper dh, String module, String data,String section){

            //etDetails(DatabaseHelper dh, String module, String page,String section,String data){
            //System.out.println("CKW set details : "+searchMenuItem.getLabel());
            setDetails(new DatabaseHelper(getBaseContext()),"CKW",searchMenuItem.getLabel(),((SearchMenuItem) searchMenuItem).getParentId(),"");
            SearchLog searchLog = new SearchLog();
            searchLog.setCategory(breadCrumb.contains("|") ? breadCrumb.substring(0, breadCrumb.indexOf("|")) : "");
            setDetails(new DatabaseHelper(getBaseContext()),"CKW",searchMenuItem.getLabel(),searchLog.getCategory(),"");

            searchLog.setContent(breadCrumb.replace("|", " "));
            searchLog.setClientId(clientId);
            searchLog.setDateCreated(Calendar.getInstance().getTime());

            GpsManager.getInstance().update();
            searchLog.setGpsLocation(GpsManager.getInstance().getLocationAsString());
            searchLog.setMenuItemId(searchMenuItem.getId());

            if (SettingsManager.getInstance().getBooleanValue(SettingsConstants.KEY_TEST_SEARCHING_ENABLED, false)) {
                searchLog.setTestLog(true);
            }
            //System.out.println("CKW Output : "+searchLog.getCategory());
            menuItemService.save(searchLog);
//            save();
        }else{
            //System.out.println("CKW set detailser : "+searchMenuItem.getLabel());
            setDetails(new DatabaseHelper(getBaseContext()),"CKW",searchMenuItem.getLabel(),((SearchMenuItem) searchMenuItem).getParentId(),"");


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.content_view, menu);

        MenuItem menuItem = menu.findItem(R.id.action_mark_favourite);
        if (menuItem != null) {
            FavouriteRecord record = menuItemService.getFavouriteRecord(searchMenuItem.getId());
            if (record != null) {
                menuItem.setIcon(R.drawable.rating_important);
            } else {
                menuItem.setIcon(R.drawable.rating_not_important);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent = this.getParentActivityIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                this.startActivity(intent);*/
                onBackPressed();
                break;
            case R.id.action_send_message:
                sendContentAsMessage();
                break;
            case R.id.action_mark_favourite:
                markContentFavourite(item);
                break;
        }
        return true;
    }

    private void markContentFavourite(MenuItem item) {
        FavouriteRecord record = menuItemService.getFavouriteRecord(searchMenuItem.getId());
        if (record == null) {
            record = new FavouriteRecord();
            record.setMenuItemId(searchMenuItem.getId());
            record.setName(searchMenuItem.getLabel());
            record.setDateCreated(Calendar.getInstance().getTime());
            menuItemService.save(record);
            item.setIcon(R.drawable.rating_important);
        } else {
            menuItemService.delete(record);
            item.setIcon(R.drawable.rating_not_important);
        }
    }

    private void sendContentAsMessage() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, searchMenuItem.getLabel());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, searchMenuItem.getDescription());

        if (ImageUtils.imageExists(searchMenuItem.getId(), true)) {
            ArrayList<Uri> list = new ArrayList<Uri>();
            list.add(Uri.fromFile(new File(ImageUtils.getFullPath(searchMenuItem.getId(), true))));
            sharingIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, list);
        }

        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    public void downloadRequestComplete(String fileName) {
        playVideo(fileName);
    }

    private void playVideo(String fileName) {
        try {
            //System.out.println("TMEDIA Playing "+fileName);
            Uri data = Uri.parse("file://"+fileName);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(data, "video/mp4");
            startActivity(intent);
        } catch(Exception ex) {
            Log.e("TMEDIA ERROR", ex.getMessage());
        }
    }
}
