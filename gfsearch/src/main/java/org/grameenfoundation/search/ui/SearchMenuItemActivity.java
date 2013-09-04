package org.grameenfoundation.search.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.grameenfoundation.search.R;
import org.grameenfoundation.search.model.ListObject;
import org.grameenfoundation.search.utils.ImageUtils;


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

        super.setContentView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = this.getParentActivityIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}
