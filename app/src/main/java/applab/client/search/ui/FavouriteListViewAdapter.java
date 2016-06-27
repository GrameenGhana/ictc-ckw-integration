package applab.client.search.ui;

import android.content.Context;
import applab.client.search.model.FavouriteRecord;
import applab.client.search.model.SearchMenuItem;

import java.util.List;

/**
 * extends the MainListViewAdapter to support the display of
 * favourite lists.
 */
public class FavouriteListViewAdapter extends MainListViewAdapter {
    List<FavouriteRecord> favouriteRecords = null;

    public FavouriteListViewAdapter(Context context) {
        super(context);
    }


    @Override
    public int getCount() {
        favouriteRecords = menuItemService.getAllFavouriteRecords();
        return menuItemService.countFavouriteRecords();
    }


    @Override
    public Object getItem(int position) {
        SearchMenuItem searchMenuItem = null;
        if (position >= 0 && position < favouriteRecords.size()) {
            FavouriteRecord favouriteRecord = favouriteRecords.get(position);
            searchMenuItem = menuItemService.getSearchMenuItem(favouriteRecord.getMenuItemId());
        }

        return searchMenuItem;
    }
}
