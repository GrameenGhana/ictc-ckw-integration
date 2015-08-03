package applab.client.search.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import applab.client.search.model.Farmer;

import java.util.ArrayList;
import java.util.List;

/**
 * utility class responsible for initializing and upgrading the database the database
 *
 * @author Charles Tumwebaze
 */
final class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelperConstants.DATABASE_NAME, null, DatabaseHelperConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createDatabaseTables(database);
    }

    private void createDatabaseTables(SQLiteDatabase database) {
        // Create Menu Table
        database.execSQL(getMenuTableInitializationSql());

        // Create Menu Item Table
        database.execSQL(getMenuItemTableInitializationSql());

        // Create Available Farmer Id Table
        database.execSQL(getAvailableFarmerIdTableInitializationSql());

        // Create All Farmers Table
        database.execSQL(getAllFarmersLocalDatabaseTableInitializationSql());

        // Create Farmer Local Cache Table
        database.execSQL(getFarmerLocalCacheTableInitializationSql());

        //create search log table
        database.execSQL(getSearchLogTableInitializationSql());

        //create the favourite record table
        database.execSQL(getFavouriteTableInitializationSql());

        //add test log column
        database.execSQL(getSearchLogTestColumnSql());

        //Create ICTC FarmerTable
        database.execSQL(getICTCFarmerTable());
    }

    /**
     * gets the SQL statement for adding a test column in the search log table.
     *
     * @return
     */


    private String getSearchLogTestColumnSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("ALTER TABLE ").append(DatabaseHelperConstants.SEARCH_LOG_TABLE_NAME);
        sqlCommand.append(" ADD COLUMN ").append(DatabaseHelperConstants.SEARCH_LOG_TEST_LOG).append(" INTEGER DEFAULT 0;");

        return sqlCommand.toString();
    }

    //CREATING THE ICTC FARMER TABLE
    private String getICTCFarmerTable() {
    StringBuilder sqlCommand=new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.FIRST_NAME).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.OTHER_NAMES).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.COMMUNITY).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.DISTRICT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.REGION).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.GENDER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.EDUCATION).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NICKNAME).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.VILLAGE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_CHILD).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_DEPENDANT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.CLUSTER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.FARMER_ID).append(" TEXT DEFAULT ''");
        sqlCommand.append(");");
        return sqlCommand.toString();
}
    private String getSearchLogTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.SEARCH_LOG_TABLE_NAME);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_ROW_ID_COLUMN).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_CLIENT_ID_COLUMN).append(" VARCHAR, ");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_CONTENT_COLUMN).append(" TEXT NOT NULL,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_CONTENT_CATEGORY_COLUMN).append(" TEXT NOT NULL,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_DATE_CREATED_COLUMN).append(" DEFAULT CURRENT_TIMESTAMP,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_GPS_LOCATION_COLUMN).append(" VARCHAR,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_MENU_ITEM_ID_COLUMN).append(" VARCHAR");
        sqlCommand.append(" );");

        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for Menu Table creation
     *
     * @return String
     */
    private String getMenuTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand
                .append("CREATE TABLE IF NOT EXISTS " + DatabaseHelperConstants.MENU_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.MENU_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, " + DatabaseHelperConstants.MENU_LABEL_COLUMN
                + " TEXT NOT NULL);");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for MenuItem Table creation
     *
     * @return String
     */
    private String getMenuItemTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.MENU_ITEM_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.MENU_ITEM_LABEL_COLUMN + " TEXT NOT NULL, "
                + DatabaseHelperConstants.MENU_ITEM_MENUID_COLUMN + " CHAR(16), "
                + DatabaseHelperConstants.MENU_ITEM_PARENTID_COLUMN + " CHAR(16), "
                + DatabaseHelperConstants.MENU_ITEM_POSITION_COLUMN + " INTEGER, "
                + DatabaseHelperConstants.MENU_ITEM_CONTENT_COLUMN + " TEXT, "
                + DatabaseHelperConstants.MENU_ITEM_ATTACHMENTID_COLUMN + " CHAR(16), ");
        sqlCommand.append(" FOREIGN KEY(menu_id) REFERENCES "
                + DatabaseHelperConstants.MENU_TABLE_NAME
                + "(id) ON DELETE CASCADE, ");
        sqlCommand.append(" FOREIGN KEY(parent_id) REFERENCES "
                + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME
                + "(id) ON DELETE CASCADE ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for AvailableFarmerId Table creation
     *
     * @return String
     */
    private String getAvailableFarmerIdTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.AVAILABLE_FARMER_ID_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_FARMER_ID + " CHAR(16), "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_STATUS + " INTEGER ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for FarmerLocalCache Table creation
     *
     * @return String
     */
    private String getFarmerLocalCacheTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.FARMER_LOCAL_CACHE_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FARMER_ID + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FIRST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_MIDDLE_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_LAST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_AGE + " INTEGER, "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FATHER_NAME + " CHAR(16) ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for FarmerLocalDatabase Table creation
     *
     * @return String
     */
    private String getAllFarmersLocalDatabaseTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.FARMER_LOCAL_DATABASE_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.FARMERS_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.FARMERS_FARMER_ID + " CHAR(16), "
                + DatabaseHelperConstants.FARMERS_FIRST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMERS_LAST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMERS_CREATION_DATE + " VARCHAR DEFAULT CURRENT_TIMESTAMP, "
                + DatabaseHelperConstants.FARMERS_SUBCOUNTY + " CHAR(16), "
                + DatabaseHelperConstants.FARMERS_VILLAGE + " CHAR(16) ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w("DatabaseHelper", "***Upgrading database from version*** "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        createDatabaseTables(database);
    }

    /**
     * gets the sql dml statement for creating the favourite record table.
     *
     * @return
     */
    public String getFavouriteTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.FAVOURITE_RECORD_TABLE_NAME);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.FAVOURITE_RECORD_ROW_ID_COLUMN).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.FAVOURITE_RECORD_NAME_COLUMN).append(" VARCHAR, ");
        sqlCommand.append(DatabaseHelperConstants.FAVOURITE_RECORD_CATEGORY_COLUMN).append(" VARCHAR,");
        sqlCommand.append(DatabaseHelperConstants.FAVOURITE_RECORD_DATE_CREATED_COLUMN).append(" VARCHAR DEFAULT CURRENT_TIMESTAMP,");
        sqlCommand.append(DatabaseHelperConstants.FAVOURITE_RECORD_MENU_ITEM_ID_COLUMN).append(" VARCHAR");
        sqlCommand.append(" );");

        return sqlCommand.toString();
    }

    /**
     *
     */
    public Farmer saveFarmer(String firstName, String lastName, String nickname, String community, String village, String district,
                             String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                             String education, String cluster, String farmID){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.FIRST_NAME, firstName);
        values.put(DatabaseHelperConstants.OTHER_NAMES, lastName);
        values.put(DatabaseHelperConstants.NICKNAME, nickname);
        values.put(DatabaseHelperConstants.COMMUNITY, community);
        values.put(DatabaseHelperConstants.DISTRICT, district);
        values.put(DatabaseHelperConstants.REGION, region);
        values.put(DatabaseHelperConstants.AGE, age);
        values.put(DatabaseHelperConstants.GENDER, gender);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.NO_OF_CHILD, numberOfChildren);
        values.put(DatabaseHelperConstants.NO_OF_DEPENDANT, numberOfDependants);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.CLUSTER, cluster);
        values.put(DatabaseHelperConstants.EDUCATION, education);
        values.put(DatabaseHelperConstants.FARMER_ID, farmID);

        db.insert(DatabaseHelperConstants.ICTC_FARMER, null, values);
        return new Farmer(firstName, lastName, nickname, community, village, district, region, age, gender, maritalStatus, numberOfChildren, numberOfDependants, education, cluster, farmID);
    }

    /**
     *
     * @return
     */

    public List<Farmer> getFarmers() {
        String query = " select  * from " + DatabaseHelperConstants.ICTC_FARMER + "  ";
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        List<Farmer> response = new ArrayList<Farmer>();
        while (localCursor.moveToNext()) {
            response.add(new Farmer(
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.OTHER_NAMES)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NICKNAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.COMMUNITY)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.VILLAGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DISTRICT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.REGION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.AGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.GENDER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MARITAL_STATUS)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_CHILD)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_DEPENDANT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.EDUCATION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.CLUSTER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID))));
        }
        return response;
    }

    /**
     *
     * @param searchBy
     * @param searchValue
     * @return
     */

    public List<Farmer> getSearchedFarmers(String searchBy,String searchValue) {
        String query = " select  * from " + DatabaseHelperConstants.ICTC_FARMER + "  where  "+searchBy+"= '"+searchValue+"' ";
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        List<Farmer> response = new ArrayList<Farmer>();
        while (localCursor.moveToNext()) {
            response.add(new Farmer(
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.OTHER_NAMES)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NICKNAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.COMMUNITY)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.VILLAGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DISTRICT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.REGION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.AGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.GENDER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MARITAL_STATUS)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_CHILD)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_DEPENDANT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.EDUCATION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.CLUSTER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID))));
        }
        return response;
    }

    /**
     *
     * @param id
     * @return
     */


    public Farmer findFarmers(String id) {
        String query = " select  * from " + DatabaseHelperConstants.ICTC_FARMER + "  where  "+DatabaseHelperConstants.FARMER_ID+" = '"+id+"'";
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        Farmer response = null ;
        while (localCursor.moveToNext()) {
            response = (new Farmer(
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.OTHER_NAMES)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NICKNAME)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.COMMUNITY)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.VILLAGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DISTRICT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.REGION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.AGE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.GENDER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MARITAL_STATUS)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_CHILD)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.NO_OF_DEPENDANT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.EDUCATION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.CLUSTER)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID))));
        }
        return response;
    }

}
