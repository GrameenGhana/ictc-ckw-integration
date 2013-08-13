package org.grameenfoundation.search.storage;

/**
 * Utility class that contains the data storage contacts.
 *
 * @author Charles Tumwebaze
 */
public final class DatabaseHelperConstants {
    private DatabaseHelperConstants() {
    }

    /* Menu Table Columns */
    public static final String MENU_ROWID_COLUMN = "id";
    public static final String MENU_LABEL_COLUMN = "label";

    /* Menu Item Table Columns */
    public static final String MENU_ITEM_ROWID_COLUMN = "id";
    public static final String MENU_ITEM_LABEL_COLUMN = "label";
    public static final String MENU_ITEM_POSITION_COLUMN = "position";
    public static final String MENU_ITEM_CONTENT_COLUMN = "content";
    public static final String MENU_ITEM_MENUID_COLUMN = "menu_id";
    public static final String MENU_ITEM_PARENTID_COLUMN = "parent_id";
    public static final String MENU_ITEM_ATTACHMENTID_COLUMN = "attachment_id";

    /* Available Farmer Ids Table Columns */
    public static final String AVAILABLE_FARMER_ID_ROWID_COLUMN = "id";
    public static final String AVAILABLE_FARMER_ID_FARMER_ID = "farmer_id";
    public static final String AVAILABLE_FARMER_ID_STATUS = "status";

    /* Farmer Local Cache Table Columns */
    public static final String FARMER_LOCAL_CACHE_ROWID_COLUMN = "id";
    public static final String FARMER_LOCAL_CACHE_FARMER_ID = "farmer_id";
    public static final String FARMER_LOCAL_CACHE_FIRST_NAME = "first_name";
    public static final String FARMER_LOCAL_CACHE_MIDDLE_NAME = "middle_name";
    public static final String FARMER_LOCAL_CACHE_LAST_NAME = "last_name";
    public static final String FARMER_LOCAL_CACHE_AGE = "age";
    public static final String FARMER_LOCAL_CACHE_FATHER_NAME = "father_name";

    /**
     * table names
     */
    public static final String MENU_TABLE_NAME = "menu";
    public static final String MENU_ITEM_TABLE_NAME = "menu_item";
    public static final String AVAILABLE_FARMER_ID_TABLE_NAME = "available_farmer_id";
    public static final String FARMER_LOCAL_CACHE_TABLE_NAME = "farmer_local_cache";

    public static final String DATABASE_NAME = "gfsearch";
    public static final int DATABASE_VERSION = 1;
}
