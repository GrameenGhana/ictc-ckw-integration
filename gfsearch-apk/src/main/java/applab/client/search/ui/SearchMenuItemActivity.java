package applab.client.search.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.location.GpsManager;
import applab.client.search.model.FavouriteRecord;
import applab.client.search.model.ListObject;
import applab.client.search.model.SearchLog;
import applab.client.search.model.SearchMenuItem;
import applab.client.search.services.MenuItemService;
import applab.client.search.settings.SettingsConstants;
import applab.client.search.settings.SettingsManager;
import applab.client.search.utils.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Activity to display a single menu item
 */
public class SearchMenuItemActivity extends Activity {
    public static final String EXTRA_LIST_OBJECT_IDENTIFIER = "menu_extraz";
    public static final String CLIENT_IDENTIFIER = "CLIENT_ID";
    public static final String BREAD_CRUMB = "BREAD_CRUMB";

    private ListObject searchMenuItem = null;
    private LayoutInflater layoutInflater = null;
    private MenuItemService menuItemService = new MenuItemService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMenuItem = (ListObject) getIntent().getSerializableExtra(EXTRA_LIST_OBJECT_IDENTIFIER);
        String clientId = (String) getIntent().getSerializableExtra(CLIENT_IDENTIFIER);
        String breadCrumb = (String) getIntent().getSerializableExtra(BREAD_CRUMB);

        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.searchmenuitem, null, false);
        TextView textView = (TextView) view.findViewById(R.id.item_content);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_img);

        textView.setText(searchMenuItem.getDescription());

        if (ImageUtils.imageExists(searchMenuItem.getId(), true)) {
            imageView.setImageDrawable(ImageUtils.getImageAsDrawable(this, searchMenuItem.getId(), true));
        }

        generateSearchLog(searchMenuItem, clientId, breadCrumb);

        super.setContentView(view);
    }

    private void generateSearchLog(ListObject searchMenuItem, String clientId, String breadCrumb) {
        if (searchMenuItem instanceof SearchMenuItem) {
            SearchLog searchLog = new SearchLog();
            searchLog.setCategory(breadCrumb.contains("|") ? breadCrumb.substring(0, breadCrumb.indexOf("|")) : "");
            searchLog.setContent(breadCrumb.replace("|", " "));
            searchLog.setClientId(clientId);
            searchLog.setDateCreated(Calendar.getInstance().getTime());

            GpsManager.getInstance().update();
            searchLog.setGpsLocation(GpsManager.getInstance().getLocationAsString());
            searchLog.setMenuItemId(searchMenuItem.getId());

            if (SettingsManager.getInstance().getBooleanValue(SettingsConstants.KEY_TEST_SEARCHING_ENABLED, false)) {
                searchLog.setTestLog(true);
            }

            menuItemService.save(searchLog);
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
}
