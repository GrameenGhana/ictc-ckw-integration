package org.grameenfoundation.search.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import org.grameenfoundation.search.R;
import org.grameenfoundation.search.location.GpsManager;
import org.grameenfoundation.search.model.ListObject;
import org.grameenfoundation.search.model.SearchLog;
import org.grameenfoundation.search.model.SearchMenuItem;
import org.grameenfoundation.search.services.MenuItemService;
import org.grameenfoundation.search.utils.ImageUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Activity to display a single menu item
 */
public class SearchMenuItemActivity extends Activity {
    public static final String EXTRA_LIST_OBJECT_IDENTIFIER = "menu_extraz";

    private ListObject searchMenuItem = null;
    private LayoutInflater layoutInflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMenuItem = (ListObject) getIntent().getSerializableExtra(EXTRA_LIST_OBJECT_IDENTIFIER);
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.searchmenuitem, null, false);
        TextView textView = (TextView) view.findViewById(R.id.item_content);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_img);

        textView.setText(searchMenuItem.getDescription());

        if (ImageUtils.imageExists(searchMenuItem.getId(), true)) {
            imageView.setImageDrawable(ImageUtils.getImageAsDrawable(this, searchMenuItem.getId(), true));
        }

        generateSearchLog(searchMenuItem);

        super.setContentView(view);
    }

    private void generateSearchLog(ListObject searchMenuItem) {
        if (searchMenuItem instanceof SearchMenuItem) {
            SearchLog searchLog = new SearchLog();
            searchLog.setContent(((SearchMenuItem) searchMenuItem).getContent());

            searchLog.setDateCreated(Calendar.getInstance().getTime());

            GpsManager.getInstance().update();
            searchLog.setGpsLocation(GpsManager.getInstance().getLocationAsString());
            searchLog.setMenuItemId(searchMenuItem.getId());

            new MenuItemService().save(searchLog);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.content_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = this.getParentActivityIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                this.startActivity(intent);
                break;
            case R.id.action_send_message:
                sendContentAsMessage();
                break;
        }
        return true;
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
