package org.grameenfoundation.search.services;

import android.content.ContentValues;
import android.database.Cursor;
import org.grameenfoundation.search.model.SearchMenu;
import org.grameenfoundation.search.model.SearchMenuItem;
import org.grameenfoundation.search.storage.DatabaseHelperConstants;
import org.grameenfoundation.search.storage.StorageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handler menu related tasks
 *
 * @author Charles Tumwebaze
 */
public class MenuItemService {

    /**
     * gets all the search menus in the system.
     *
     * @return
     */
    public List<SearchMenu> getAllSearchMenus() {
        return null;
    }

    /**
     * gets the search menus starting at the given offset and ending at the given limit.
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<SearchMenu> getSearchMenus(int offset, int limit) {
        return null;
    }

    /**
     * gets the total number of search menu items.
     *
     * @return
     */
    public int countSearchMenus() {
        return StorageManager.getInstance().recordCount(DatabaseHelperConstants.MENU_TABLE_NAME);
    }

    /**
     * saves the given search menus into the data store
     *
     * @param searchMenus
     */
    public void save(SearchMenu... searchMenus) {

    }

    /**
     * gets all the search menu items in the system.
     *
     * @return
     */
    public List<SearchMenuItem> getAllSearchMenuItems() {
        return null;
    }

    /**
     * gets the search menus starting at the given offset and ending at the given limit.
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<SearchMenuItem> getSearchMenuItems(int offset, int limit) {
        return null;
    }

    /**
     * gets the total number of search menu items in the system.
     *
     * @return
     */
    public int countSearchMenuItems() {
        return StorageManager.getInstance().recordCount(DatabaseHelperConstants.MENU_ITEM_TABLE_NAME);
    }

    /**
     * gets the total number of search menu items for the given parent menu identifier.
     *
     * @param parentMenuItemId
     * @return
     */
    public int countSearchMenuItems(String parentMenuItemId) {
        String sql = String.format("SELECT COUNT(*) as total FROM %1$s WHERE %2$s = %3$s",
                DatabaseHelperConstants.MENU_TABLE_NAME, DatabaseHelperConstants.MENU_ITEM_TABLE_NAME,
                parentMenuItemId);
        Cursor cursor = StorageManager.getInstance().sqlSearch(sql);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }


    /**
     * saves the given search menu items into the data store
     *
     * @param searchMenuItems
     */
    public void save(SearchMenuItem... searchMenuItems) {
        List<ContentValues> values = getContentValues(searchMenuItems);

        StorageManager.getInstance().replace(DatabaseHelperConstants.MENU_ITEM_TABLE_NAME,
                values.toArray(new ContentValues[]{}));
    }

    private List<ContentValues> getContentValues(SearchMenuItem[] searchMenuItems) {
        List<ContentValues> values = new ArrayList<ContentValues>();
        for (SearchMenuItem item : searchMenuItems) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_ROWID_COLUMN, item.getId());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_LABEL_COLUMN, item.getLabel());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_POSITION_COLUMN, item.getPosition());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_CONTENT_COLUMN, item.getContent());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_MENUID_COLUMN, item.getMenuId());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_PARENTID_COLUMN, item.getParentId());
            contentValue.put(DatabaseHelperConstants.MENU_ITEM_ATTACHMENTID_COLUMN, item.getAttachmentId());

            values.add(contentValue);
        }
        return values;
    }
}
