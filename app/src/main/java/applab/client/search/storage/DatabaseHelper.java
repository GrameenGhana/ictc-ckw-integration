package applab.client.search.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import applab.client.search.model.*;
import applab.client.search.model.wrapper.MeetingSettingWrapper;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * utility class responsible for initializing and upgrading the database the database
 *
 * @author Charles Tumwebaze
 */
public final class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelperConstants.DATABASE_NAME, null, DatabaseHelperConstants.DATABASE_VERSION);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
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
        database.execSQL(getICTCWeather());
        database.execSQL(getICTCUser());
        database.execSQL(getICTCFarmerTable());
        database.execSQL(getICTCFarmerMeetings());
        database.execSQL(getICTCFarmerMeetingsProcedure());
        database.execSQL(getICTCFarmGpsLocation());
        database.execSQL(getICTCFarmInputs());
        database.execSQL(getICTCTrackerTable());
        database.execSQL(getICTCMeetingSettingItem());

        // myAgriHub
        //database.execSQL(getAGSMOUser());
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


    private String getICTCFarmerMeetings() {
        StringBuilder sqlCommand = new StringBuilder();
        //
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER_MEETING);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TYPE).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MEEING_INDEX).append(" INT DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TITLE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ACTUAL_MEETING_DATE).append(" INT DEFAULT  0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SCHEDULE_DATE).append("  INT DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ATTENDED).append(" INT DEFAULT -1,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_REMARK).append(" TEXT DEFAULT '' ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_ID).append(" TEXT DEFAULT '' ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SEASON).append(" int DEFAULT 1 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_CROP).append(" TEXT DEFAULT '' ," +
                "UNIQUE ("+DatabaseHelperConstants.ICTC_FARMER_ID+", "+DatabaseHelperConstants.ICTC_MEEING_INDEX+","+DatabaseHelperConstants.ICTC_TYPE+") ON CONFLICT REPLACE");
        sqlCommand.append(");");
        return sqlCommand.toString();

    }


    private String getICTCUser() {
        StringBuilder sqlCommand = new StringBuilder();
        //
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_USER_ID).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_USER_NAME).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ORGANISATION).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_USER_SF_ID).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_LAST_MODIFIED_DATE).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_USER_FULL_NAME).append(" TEXT DEFAULT ''");
          sqlCommand.append(");");
        return sqlCommand.toString();

    }

    private String getICTCWeather() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_WEATHER_TABLE);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_LOCATION).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TIME).append(" INTEGER DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_DETAIL).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ICON).append(" TEXT DEFAULT '' ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TEMPERATURE).append(" DOUBLE DEFAULT 0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MIN_TEMPERATURE).append(" DOUBLE DEFAULT 0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MAX_TEMPERATURE).append(" DOUBLE DEFAULT 0 " );
        sqlCommand.append(");");
        return sqlCommand.toString();
    }

    public String getICTCMeetingSettingItem() {
        StringBuilder sqlCommand = new StringBuilder();
        System.out.println("Order to create "+DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS);
        //
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS
        );
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TYPE).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MEEING_INDEX).append(" INT DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SEASON).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME).append(" TEXT DEFAULT  '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME).append("  TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ACTIVITY).append(" text DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_CROP).append(" TEXT DEFAULT '' ," +
                "UNIQUE ("+DatabaseHelperConstants.ICTC_CROP+", "+DatabaseHelperConstants.ICTC_MEEING_INDEX+","+DatabaseHelperConstants.ICTC_TYPE+") ON CONFLICT REPLACE");
        sqlCommand.append(");");
        return sqlCommand.toString();

    }

    private String getICTCFarmGpsLocation(){
        StringBuilder sqlCommand = new StringBuilder();
        //
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_GPS_LOCATION);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sqlCommand.append(DatabaseHelperConstants.ICTC_LATITUDE).append(" DOUBLE DEFAULT 0.0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_LONGITUDE).append(" DOUBLE DEFAULT 0.0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_ID).append(" TEXT DEFAULT '' ");
        sqlCommand.append(");");
        return sqlCommand.toString();
    }

    private String getICTCFarmInputs(){
        StringBuilder sqlCommand = new StringBuilder();
        //
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARM_INPUTS);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sqlCommand.append(DatabaseHelperConstants.ICTC_INPUT_NAME).append(" TEXT DEFAULT '', ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_QTY_GIVEN).append(" DOUBLE DEFAULT 0.0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_QTY_RECEIVED).append(" DOUBLE DEFAULT 0.0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_STATUS).append(" int DEFAULT 0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_DATE_RECEIVED).append(" int DEFAULT 0 ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_ID).append(" TEXT DEFAULT '' ");
        sqlCommand.append(");");
        return sqlCommand.toString();

    }

    private String getICTCFarmerMeetingsProcedure() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER_MEETING_PROCEDURE);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TYPE).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MEEING_INDEX).append(" INT DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TITLE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SCHEDULE_DATE).append(" int ,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SEASON).append(" INT DEFAULT 1,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_REMARK).append(" TEXT DEFAULT '' ");
        sqlCommand.append(");");
        return sqlCommand.toString();

    }

    private String getICTCFarmerTable() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER);
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
        sqlCommand.append(DatabaseHelperConstants.AGE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_CHILD).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_DEPENDANT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.MARITAL_STATUS).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.CLUSTER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.FARMER_ID).append(" TEXT DEFAULT '',");

        sqlCommand.append(DatabaseHelperConstants.SIZE_PLOT).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.MAIN_CROP).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.LABOUR).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.DATE_OF_LAND_IDENTIFICATION).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.LOCATION_LAND).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.TARGET_AREA).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.EXPECTED_PRICE_TON).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.VARIETY).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.TARGET_NEXT_SEASON).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.TECH_NEEDS_I).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.TECH_NEEDS_II).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.FARMER_BASE_ORG).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.PLANTING_DATE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.LAND_AREA).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.DATE_MANUAL_WEEDING).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.POS_CONTACT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.MONTH_SELLING_STARTS).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.MONTH_FINAL_PRODUCT_SOLD).append(" TEXT DEFAULT '',");
//jUST ADDED
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_POSTHARVEST).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_PRODUCTION).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_BASELINEPOSTHARVEST).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_FARMER_BASELINEPRODUCTION).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TECH_NEEDS).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_BASELINE_POST_HARVEST_BADGET).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_BASELINE_PRODUCTION_BADGET).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.PHONENUMBER).append(" TEXT DEFAULT ''");

/**
 values.put(DatabaseHelperConstants., SIZE_PLOT);
 values.put(DatabaseHelperConstants., LABOUR);
 values.put(DatabaseHelperConstants., DATE_OF_LAND_IDENTIFICATION);
 values.put(DatabaseHelperConstants., LOCATION_LAND);
 values.put(DatabaseHelperConstants., TARGET_AREA);
 values.put(DatabaseHelperConstants., EXPECTED_PRICE_TON);
 values.put(DatabaseHelperConstants., VARIETY);
 values.put(DatabaseHelperConstants.EDUCATION, gender);
 values.put(DatabaseHelperConstants., maritalStatus);
 values.put(DatabaseHelperConstants., TECH_NEEDS_I);
 values.put(DatabaseHelperConstants., TECH_NEEDS_II);
 values.put(DatabaseHelperConstants., FARMER_BASE_ORG);
 values.put(DatabaseHelperConstants., PLANTING_DATE);
 values.put(DatabaseHelperConstants., LAND_AREA);
 values.put(DatabaseHelperConstants., DATE_MANUAL_WEEDING);
 values.put(DatabaseHelperConstants., POS_CONTACT);
 values.put(DatabaseHelperConstants., MONTH_SELLING_STARTS);
 values.put(DatabaseHelperConstants., MONTH_FINAL_PRODUCT_SOLD);
 **/

        sqlCommand.append(");");
        return sqlCommand.toString();
    }

    public void alterFarmerTable(){
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sqlCommand = new StringBuilder();

        try{
       /* if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_BASELINE_POST_HARVEST_BADGET)==false) {
            db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_BASELINE_POST_HARVEST_BADGET + " TEXT DEFAULT '{}'");
        }
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_TECH_NEEDS)==false) {
                db.execSQL("ALTER TABLE  " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_TECH_NEEDS + " TEXT DEFAULT '{}'");
            }
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_FARMER_POSTHARVEST)==false) {
                db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_FARMER_POSTHARVEST + " TEXT DEFAULT '{}'");
            }
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_FARMER_PRODUCTION)==false) {
                db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_FARMER_PRODUCTION + " TEXT DEFAULT '{}'");
            }
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_FARMER_BASELINEPOSTHARVEST)==false) {
                db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_FARMER_BASELINEPOSTHARVEST + " TEXT DEFAULT '{}'");
            }
                if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_FARMER_BASELINEPRODUCTION)==false) {
                    db.execSQL("ALTER TABLE  " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_FARMER_BASELINEPRODUCTION + " TEXT DEFAULT '{}'");
                }
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_BASELINE_PRODUCTION_BADGET)==false) {
                db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.ICTC_BASELINE_PRODUCTION_BADGET + " TEXT DEFAULT '{}'");
            }*/
            if(isFieldExist(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.PHONENUMBER)==false) {
                System.out.println("Adding phone number column");
               // db.execSQL("ALTER TABLE " + DatabaseHelperConstants.ICTC_FARMER + " ADD COLUMN " + DatabaseHelperConstants.PHONENUMBER + " TEXT DEFAULT '{}'");
           }
        }catch(Exception e){
e.printStackTrace();
        }
    }

    public void alterSearchMenuItem(){
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sqlCommand = new StringBuilder();
//        db.execSQL("ALTER TABLE ").append(DatabaseHelperConstants.ICTC_FARMER);
        try{
            db.execSQL("ALTER TABLE " +DatabaseHelperConstants.MENU_ITEM_TABLE_NAME+" ADD COLUMN "+DatabaseHelperConstants.MENU_ITEM_HAS_IMAGE+" INTEGER DEFAULT 0 ");
            db.execSQL("ALTER TABLE " + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME + " ADD COLUMN " + DatabaseHelperConstants.MENU_ITEM_HAS_AUDIO+" INTEGER DEFAULT 0 ");
            db.execSQL("ALTER TABLE " + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME + " ADD COLUMN " + DatabaseHelperConstants.MENU_ITEM_HAS_VIDEO+" INTEGER DEFAULT  0 ");
        }catch(Exception e){

        }
    }

    public void alterUserTable(){
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sqlCommand = new StringBuilder();

        try{
            db.execSQL("ALTER TABLE "+DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE+" ADD COLUMN "+DatabaseHelperConstants.ICTC_LAST_MODIFIED_DATE+" TEXT DEFAULT '0'");
            db.execSQL("ALTER TABLE "+DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE+" ADD COLUMN "+DatabaseHelperConstants.ICTC_USER_SF_ID+" TEXT DEFAULT ''");
         }catch(Exception e){}

    }

    private String getICTCFarmerMngPlanTable() {
        StringBuilder sqlCommand = new StringBuilder();
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
        sqlCommand.append(DatabaseHelperConstants.AGE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_CHILD).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_DEPENDANT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.MARITAL_STATUS).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.CLUSTER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.FARMER_ID).append(" TEXT DEFAULT ''");
        sqlCommand.append(");");
        return sqlCommand.toString();
    }

    private String getICTCCropCalendar() {
        /**
         * public static final String ICTC_CROP_CALENDAR = "ictc_crop_calendar";
         public static final String ICTC_ACTIVITY = "actiity";
         public static final String ICTC_ACTIVITY_INDEX = "activity_index";
         public static final String ICTC_WEEKS_FROM_PLANTING = "week_frm_planting";
         public static final String ICTC_COMMENT = "comment";
         //    public static final String ICTC_SEASON= "season";
         public static final String ICTC_FARMER_ID= "farmer_id";
         public static final String ICTC_EARLIEST_START_DATE = "earliest_start";
         public static final String ICTC_LATEST_START_DATE= "latest_start";
         public static final String ICTC_ACTUAL_DATE= "actual_date";

         */
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_CROP_CALENDAR);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ACTIVITY).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ACTIVITY_INDEX).append(" INT ,");
        sqlCommand.append(DatabaseHelperConstants.COMMUNITY).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.DISTRICT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.REGION).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.GENDER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.EDUCATION).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NICKNAME).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.VILLAGE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.AGE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_CHILD).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.NO_OF_DEPENDANT).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.MARITAL_STATUS).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.CLUSTER).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.FARMER_ID).append(" TEXT DEFAULT ''");
        sqlCommand.append(");");
        return sqlCommand.toString();
    }

    private String getICTCTrackerTable() {
        String l_sql = "create table if not exists " + DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE + " ("
                + DatabaseHelperConstants.ICTC_ID + " integer primary key autoincrement, "
                + DatabaseHelperConstants.ICTC_USER_ID + " text, "
                + DatabaseHelperConstants.ICTC_TRACKER_MODULE + " text, "
                + DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME + " string , "
                + DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME + " string , "

                + DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED_DATE + " string , "
                +DatabaseHelperConstants.ICTC_TRACKER_DATA
                + " text, " + DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED + " integer default 0, "

                + DatabaseHelperConstants.ICTC_TRACKER_INPROGRESS + " integer default 0)";
        return l_sql;
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

    private String getMenuTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand
                .append("CREATE TABLE IF NOT EXISTS " + DatabaseHelperConstants.MENU_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.MENU_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, " + DatabaseHelperConstants.MENU_LABEL_COLUMN
                + " TEXT NOT NULL);");
        return sqlCommand.toString();
    }

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

                + DatabaseHelperConstants.MENU_ITEM_HAS_AUDIO + " INTEGER, "

                + DatabaseHelperConstants.MENU_ITEM_HAS_IMAGE + " INTEGER, "

                + DatabaseHelperConstants.MENU_ITEM_HAS_VIDEO + " INTEGER, "
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "***Upgrading database from version*** "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        createDatabaseTables(database);
    }

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

    public Farmer saveFarmer(String firstName, String lastName, String nickname, String community, String village, String district,
                             String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                             String education, String cluster, String farmID, String telephonenumber) {
        //changed adding telephone number
        return saveFarmer(firstName, lastName, nickname, community, village, district,
                region, age, gender, maritalStatus, numberOfChildren, numberOfDependants,
                education, cluster, farmID, "", "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "","{}","{}","{}","{}","{}","{}","{}",telephonenumber);
    }
// changed adding telephone number
    public Farmer saveFarmer(String firstName, String lastName, String nickname, String community, String village, String district,
                             String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants,
                             String education, String cluster, String farmID,
                             String SIZE_PLOT,
                             String LABOUR,
                             String DATE_OF_LAND_IDENTIFICATION,
                             String LOCATION_LAND,
                             String TARGET_AREA,
                             String EXPECTED_PRICE_TON,
                             String VARIETY,
                             String TARGET_NEXT_SEASON,
                             String TECH_NEEDS_I,
                             String TECH_NEEDS_II,
                             String FARMER_BASE_ORG,
                             String PLANTING_DATE,
                             String LAND_AREA,
                             String DATE_MANUAL_WEEDING,
                             String POS_CONTACT,
                             String MONTH_SELLING_STARTS,
                             String MONTH_FINAL_PRODUCT_SOLD,
                             String mainCrop, String production,String postHarvest,String budget,String baselineProduction,String baselinePostHarvest,
                             String techNeeds,String baselinePostHarvestBudget,String telephonenumber) {
        SQLiteDatabase db = getWritableDatabase();
        alterFarmerTable();
        if ("mainpointofsaleorcontact".equalsIgnoreCase(POS_CONTACT))
            POS_CONTACT = "";
        if ("fboname".equalsIgnoreCase(FARMER_BASE_ORG))
            POS_CONTACT = "";
        if (DATE_MANUAL_WEEDING.equalsIgnoreCase("Date Man Weed"))
            DATE_MANUAL_WEEDING = "";
        if (LABOUR.equalsIgnoreCase("labouruse"))
            LABOUR = "";
        if ("monthsellingbegins".equalsIgnoreCase(MONTH_SELLING_STARTS))
            MONTH_SELLING_STARTS = "";
        if (MONTH_FINAL_PRODUCT_SOLD.equalsIgnoreCase("monthfinalbatchsold"))
            MONTH_FINAL_PRODUCT_SOLD = "";
        if (LABOUR.equalsIgnoreCase("labouruse"))
            LABOUR = "";

        String  [] reg = region.split(" ");
        if(reg.length>1)
          region  =reg[1];
        reg = gender.split(" ");
        if(reg.length>1)
            gender  =reg[1];
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.SIZE_PLOT, SIZE_PLOT);
        values.put(DatabaseHelperConstants.LABOUR, LABOUR);
        values.put(DatabaseHelperConstants.DATE_OF_LAND_IDENTIFICATION, DATE_OF_LAND_IDENTIFICATION);
        values.put(DatabaseHelperConstants.LOCATION_LAND, LOCATION_LAND);
        values.put(DatabaseHelperConstants.TARGET_AREA, TARGET_AREA);
        values.put(DatabaseHelperConstants.EXPECTED_PRICE_TON, EXPECTED_PRICE_TON);
        values.put(DatabaseHelperConstants.VARIETY, VARIETY);
        values.put(DatabaseHelperConstants.EDUCATION, gender);
        values.put(DatabaseHelperConstants.TARGET_NEXT_SEASON, TARGET_NEXT_SEASON);
        values.put(DatabaseHelperConstants.TECH_NEEDS_I, TECH_NEEDS_I);
        values.put(DatabaseHelperConstants.TECH_NEEDS_II, TECH_NEEDS_II);
        values.put(DatabaseHelperConstants.FARMER_BASE_ORG, FARMER_BASE_ORG);
        values.put(DatabaseHelperConstants.PLANTING_DATE, PLANTING_DATE);
        values.put(DatabaseHelperConstants.LAND_AREA, LAND_AREA);
        values.put(DatabaseHelperConstants.DATE_MANUAL_WEEDING, DATE_MANUAL_WEEDING);
        values.put(DatabaseHelperConstants.POS_CONTACT, POS_CONTACT);
        values.put(DatabaseHelperConstants.MONTH_SELLING_STARTS, MONTH_SELLING_STARTS);
        values.put(DatabaseHelperConstants.MONTH_FINAL_PRODUCT_SOLD, MONTH_FINAL_PRODUCT_SOLD);

        values.put(DatabaseHelperConstants.MAIN_CROP, mainCrop);
        values.put(DatabaseHelperConstants.FIRST_NAME, firstName);
        values.put(DatabaseHelperConstants.OTHER_NAMES, lastName);
        values.put(DatabaseHelperConstants.NICKNAME, nickname);
        values.put(DatabaseHelperConstants.COMMUNITY, community);
        values.put(DatabaseHelperConstants.VILLAGE, village);
        values.put(DatabaseHelperConstants.DISTRICT, district);
        values.put(DatabaseHelperConstants.REGION, region);
        values.put(DatabaseHelperConstants.AGE, age);
        values.put(DatabaseHelperConstants.GENDER, gender);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.NO_OF_CHILD, numberOfChildren);
        values.put(DatabaseHelperConstants.NO_OF_DEPENDANT, numberOfDependants);

        values.put(DatabaseHelperConstants.CLUSTER, cluster);
        values.put(DatabaseHelperConstants.EDUCATION, education);


        values.put(DatabaseHelperConstants.ICTC_FARMER_BASELINEPOSTHARVEST, baselinePostHarvest);
        values.put(DatabaseHelperConstants.ICTC_FARMER_BASELINEPRODUCTION, baselineProduction);
        values.put(DatabaseHelperConstants.ICTC_BASELINE_PRODUCTION_BADGET, budget);
        values.put(DatabaseHelperConstants.ICTC_FARMER_PRODUCTION, production);
        values.put(DatabaseHelperConstants.ICTC_FARMER_POSTHARVEST, postHarvest);
        values.put(DatabaseHelperConstants.ICTC_TECH_NEEDS, techNeeds);
        values.put(DatabaseHelperConstants.ICTC_BASELINE_POST_HARVEST_BADGET, baselinePostHarvestBudget);
        values.put(DatabaseHelperConstants.PHONENUMBER, telephonenumber);

        System.out.println("Saving MainCrop : " + maritalStatus);


        values.put(DatabaseHelperConstants.FARMER_ID, farmID);

        long id = db.insert(DatabaseHelperConstants.ICTC_FARMER, null, values);

        //    public Farmer(String firstName, String lastName, String village, String nickname, String community, String district, String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants, String education, String cluster, String farmID, String sizePlot, String labour, String dateOfLandIdentification, String locationOfLand, String targetArea, String expectedPriceInTon, String variety, String targetNextSeason, String techNeeds1, String techNeeds2, String farmerBasedOrg, String plantingDate, String landArea, String dateManualWeeding, String posContact, String monthSellingStarts, String monthFinalProductSold) {


        Farmer f = new Farmer(firstName, lastName, nickname, community, village, district, region, age, gender, maritalStatus, numberOfChildren, numberOfDependants, education, cluster, farmID,
                SIZE_PLOT,
                LABOUR,
                DATE_OF_LAND_IDENTIFICATION,
                LOCATION_LAND,
                TARGET_AREA,
                EXPECTED_PRICE_TON,
                VARIETY,
                TARGET_NEXT_SEASON,
                TECH_NEEDS_I,
                TECH_NEEDS_II,
                FARMER_BASE_ORG,
                PLANTING_DATE,
                LAND_AREA,
                DATE_MANUAL_WEEDING,
                POS_CONTACT,
                MONTH_SELLING_STARTS,
                MONTH_FINAL_PRODUCT_SOLD, mainCrop, telephonenumber
        );
        f.setId(String.valueOf(id));
        Log.i(this.getClass().getName(),"Serrve Farmer 1 "+id+" Crop "+f.getMainCrop());
        return f;

    }


    public  long saveGPSLocation(double lat,double longitude,String farmerId){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_LATITUDE, lat);
        values.put(DatabaseHelperConstants.ICTC_LONGITUDE, longitude);
        values.put(DatabaseHelperConstants.ICTC_FARMER_ID, farmerId);

       return  db.insert(DatabaseHelperConstants.ICTC_GPS_LOCATION, null, values);
    }

    public boolean deleteFarmerGPS(String farmer){
        return deleteQuery(DatabaseHelperConstants.ICTC_GPS_LOCATION,DatabaseHelperConstants.ICTC_FARMER_ID,farmer);
    }


    public  FarmGPSLocation getGPSLocation(Cursor localCursor){


        try {

        FarmGPSLocation met = new FarmGPSLocation(
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID)),
                localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_LATITUDE)),
                localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_LONGITUDE)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_ID))
                );

        return  met;


        }finally {
//            localCursor.close();
        }
    }



    public List<FarmGPSLocation> getFarmerCoordinates(String q){
        String qr="select * from "+DatabaseHelperConstants.ICTC_GPS_LOCATION+" WHERE "+DatabaseHelperConstants.ICTC_FARMER_ID+"= '"+q+"'";
        System.out.println("QR : "+qr);
        Cursor localCursor = this.getWritableDatabase().rawQuery("select * from "+DatabaseHelperConstants.ICTC_GPS_LOCATION+" WHERE "+DatabaseHelperConstants.ICTC_FARMER_ID+"= '"+q+"'", null);

        List<FarmGPSLocation> response = new ArrayList<FarmGPSLocation>();
        try{
        while (localCursor.moveToNext()) {
            response.add(getGPSLocation(localCursor));
        }
        }finally {
            localCursor.close();
        }
        return response;
    }
    public Meeting saveMeeting(String id, String type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer, String remark,String crop,String season){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_TYPE, type);
        values.put(DatabaseHelperConstants.ICTC_TITLE, title);
        values.put(DatabaseHelperConstants.ICTC_SCHEDULE_DATE, IctcCKwUtil.formatStringDateTime(scheduledDate));
        values.put(DatabaseHelperConstants.ICTC_ACTUAL_MEETING_DATE, IctcCKwUtil.formatStringDateTime(meetingDate));
        values.put(DatabaseHelperConstants.ICTC_ATTENDED, attended);
        values.put(DatabaseHelperConstants.ICTC_FARMER_ID, farmer);
        values.put(DatabaseHelperConstants.ICTC_MEEING_INDEX, meetingIndex);
        values.put(DatabaseHelperConstants.ICTC_REMARK, remark);
        values.put(DatabaseHelperConstants.ICTC_CROP, crop);
        values.put(DatabaseHelperConstants.ICTC_SEASON, season);


        db.insert(DatabaseHelperConstants.ICTC_FARMER_MEETING, null, values);


        return new Meeting(id,type,title,scheduledDate,meetingDate,attended,meetingIndex,farmer,remark,crop,season);
    }

    private  Meeting getMeeting(Cursor localCursor){

//        public Meeting(String id, String type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer, String remark,String crop) {


            Meeting met = new Meeting(
                String.valueOf(localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID))),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TYPE)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TITLE)),
                    IctcCKwUtil.formatDateTime(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_SCHEDULE_DATE))),
                    IctcCKwUtil.formatDateTime(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ACTUAL_MEETING_DATE))),
                    localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ATTENDED)),
                    localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_MEEING_INDEX)),
                         localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_ID)),
                         localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_REMARK)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_CROP)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_SEASON))
                 );
//        localCursor.close();
        Farmer f = findFarmer(met.getFarmer());
        System.out.println("Meeting farmer :"+f);
        met.setFarmerDetails(f);
        return  met;
    }

    public void meetingSettingCreation(){
        SQLiteDatabase db = getWritableDatabase();
        System.out.println("Creating Table");
        db.execSQL(getICTCMeetingSettingItem());

        System.out.println("Creating Table After Meeting creation");

    }


    public void saveMeetingSetting(String crop,String type,String index,String season,String start,String end,String activity){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_TYPE, type);
        values.put(DatabaseHelperConstants.ICTC_CROP, crop);
        values.put(DatabaseHelperConstants.ICTC_MEEING_INDEX, index);
        values.put(DatabaseHelperConstants.ICTC_SEASON, season);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME, start);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME, end);
        values.put(DatabaseHelperConstants.ICTC_ACTIVITY, activity);


        long id = db.insert(DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS, null, values);

        System.out.println("MSettingID : "+id);
    }

    public long getMeetingSettingCount(){
        try {
            return getAggregateValue(getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS));

        }catch(Exception e){

        }
        return 0l;
    }

    public List<MeetingSettingWrapper> getAllMeetingSettings(){
        String q=findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);

        return getCursorMeetingSettings(localCursor);
    }


    public List<MeetingSettingWrapper> getMeetingSettings(String type,String index,String crop){

        System.out.println(index+" -  "+type+" - "+crop);
        String q=findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_SETTINGS)+"  where "+DatabaseHelperConstants.ICTC_MEEING_INDEX+"="+index+" and "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"'  and  "+DatabaseHelperConstants.ICTC_TYPE+"='"+type+"' ";
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);

        return getCursorMeetingSettings(localCursor);
    }

    public List<MeetingSettingWrapper> getCursorMeetingSettings(Cursor localCursor){
        List<MeetingSettingWrapper> settings = new ArrayList<MeetingSettingWrapper>();
        while (localCursor.moveToNext()) {
            settings.add(getMeetingDetails(localCursor));
        }
//        localCursor.close();

        return settings;
    }




    private MeetingSettingWrapper getMeetingDetails(Cursor localCursor){


        //String crop,String type, String meetingIndex, String season, String startDate, String endDate, String acts
        MeetingSettingWrapper met = new MeetingSettingWrapper(
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_CROP)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TYPE)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_MEEING_INDEX)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_SEASON)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ACTIVITY))
        );
//        localCursor.close();
        return  met;
    }

    private  MeetingProcedure getMeetingProcedure(Cursor localCursor){
        MeetingProcedure met = new MeetingProcedure(
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME))
        );
        //localCursor.close();
        return  met;
    }


    public List<MeetingProcedure> getMeetingProcedure(String q){
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<MeetingProcedure> response = new ArrayList<MeetingProcedure>();
        while (localCursor.moveToNext()) {
            response.add(getMeetingProcedure(localCursor));
        }
        localCursor.close();
        return response;
    }


    public List<MeetingProcedure> getMeetingAllProcedure(){

        String q=findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_PROCEDURE);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<MeetingProcedure> response = new ArrayList<MeetingProcedure>();
        while (localCursor.moveToNext()) {
            response.add(getMeetingProcedure(localCursor));
        }
        localCursor.close()
;       return response;
    }
    public MeetingProcedure getMeetingProcedure(int meetingIndex,String crop){
            return getMeetingProcedure(meetingIndex,crop,1);
    }

    public MeetingProcedure getMeetingProcedure(int meetingIndex,String crop,int season){

        String q=findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_PROCEDURE)+" where "+DatabaseHelperConstants.ICTC_MEEING_INDEX+"="+meetingIndex+" and "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"'  ";
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        while (localCursor.moveToNext()) {
            return(getMeetingProcedure(localCursor));
        }
        localCursor.close();
        return null;
    }


    public List<Meeting> getGroupMeetings(){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='group'  group by "+DatabaseHelperConstants.ICTC_MEEING_INDEX+" ,"+DatabaseHelperConstants.ICTC_CROP+" order by "+DatabaseHelperConstants.ICTC_SCHEDULE_DATE+" ASC ";;
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();

        return response;
    }

    public List<Meeting> getIndividualMeetings(){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='individual'  order by "+DatabaseHelperConstants.ICTC_SCHEDULE_DATE+" ASC ";
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();
        return response;
    }

    public List<Meeting> getIndividualMeetings(String crop){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='individual'  and  "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"' order by "+DatabaseHelperConstants.ICTC_SCHEDULE_DATE+" ASC ";
        System.out.println("Query  : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();

        System.out.println("Meeting Cnt  : "+response.size());
        return response;
    }

    public List<Meeting> getGroupMeetings(String crop){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='group'  and  "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"' order by "+DatabaseHelperConstants.ICTC_SCHEDULE_DATE+" ASC ";
        System.out.println("Query  : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();
        System.out.println("Meeting Cnt  : "+response.size());
        return response;
    }
    public List<Meeting> getIndividualMeetings(String crop,String index){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='individual'  and  "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"' and "+DatabaseHelperConstants.ICTC_MEEING_INDEX+"= "+index+"  and "+DatabaseHelperConstants.ICTC_FARMER_ID+" is not null order by "+DatabaseHelperConstants.ICTC_ATTENDED+"  ASC ";
        System.out.println("Query  : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        try {
        while (localCursor.moveToNext()) {
                Meeting  m = getMeeting(localCursor);
                if(m.getFarmerDetails()!=null)
                    response.add(m);
        }
    }finally {
            System.out.println("Closed for "+crop+"  -- "+index);
                localCursor.close();
        }
        System.out.println("Meeting Cnt  : "+response.size());
        return response;
    }public List<Meeting> getGroupMeetings(String crop,String index){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_TYPE+" ='group'  and  "+DatabaseHelperConstants.ICTC_CROP+"='"+crop+"' and "+DatabaseHelperConstants.ICTC_MEEING_INDEX+"= "+index+" order by "+DatabaseHelperConstants.ICTC_ATTENDED+"  ASC ";
        System.out.println("Query  : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();

        System.out.println("Meeting Cnt  : "+response.size());
        return response;
    }


    public List<Meeting> getMeetings(String q){
        System.out.println("Query : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }
        localCursor.close();
        return response;
    }
    public List<Meeting> getFarmerMeetings(String farmer){
        return getMeetings(findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING) + " WHERE " + DatabaseHelperConstants.ICTC_FARMER_ID + " ='" + farmer + "'  order by "+DatabaseHelperConstants.ICTC_ATTENDED+"  ASC ,  "+DatabaseHelperConstants.ICTC_ID+" asc ");
    }


    public List<Farmer> getFarmerByMeetingAttended(String index,String type ){
//        return getMeetings(findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING) + " WHERE " + DatabaseHelperConstants.ICTC_FARMER_ID + " ='" + farmer + "'  order by "+DatabaseHelperConstants.ICTC_ATTENDED+"  ASC ,  "+DatabaseHelperConstants.ICTC_ID+" asc ");
        List<Farmer> mt = getFarmersSearch(findAllQuery(DatabaseHelperConstants.ICTC_FARMER) + "  inner join " + DatabaseHelperConstants.ICTC_FARMER_MEETING + " m on  t." + DatabaseHelperConstants.ICTC_FARMER_ID + "=m." + DatabaseHelperConstants.ICTC_FARMER_ID + " WHERE t." + DatabaseHelperConstants.ICTC_ATTENDED + " =1"  );
return mt;

    }

    public Meeting getFarmerMeetings(String farmer,int meetingIndex){
        String q = findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING) + " WHERE " + DatabaseHelperConstants.ICTC_FARMER_ID + " ='" + farmer + "'  and "+DatabaseHelperConstants.ICTC_MEEING_INDEX+"="+meetingIndex+" and "+DatabaseHelperConstants.ICTC_TYPE+" ='individual' ";
        List<Meeting> mt = getMeetings(q);
        if(mt.size()>0)
        return     mt.get(0);
        return  null;
    }

    public Meeting getFarmerMeetingsById(String id){
        List<Meeting> mt = getMeetings(findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING) + " WHERE " + DatabaseHelperConstants.ICTC_ID + " =" + id );
        if(mt.size()>0)
            mt.get(0);
        return  null;
    }
    public int getMeetingsAttended(String farmer){
        return getAggregateValue("SELECT count(*) FROM " + DatabaseHelperConstants.ICTC_FARMER_MEETING + " WHERE " + DatabaseHelperConstants.ICTC_FARMER + " ='" + farmer + "' and attended=1");
    }
/**
     * @return
     */
    public List<Farmer> getFarmersSearch(String query) {
        Cursor localCursor = this.getWritableDatabase().rawQuery(query+" order by "+DatabaseHelperConstants.OTHER_NAMES+" asc ,"+DatabaseHelperConstants.FIRST_NAME+" asc", null);
        List<Farmer> response = new ArrayList<Farmer>();
        try {
        while (localCursor.moveToNext()) {
            System.out.println("MM : " + localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MAIN_CROP)));
           Farmer f  = new Farmer(
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
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_ID)),

                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.SIZE_PLOT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.LABOUR)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DATE_OF_LAND_IDENTIFICATION)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.LOCATION_LAND)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.TARGET_AREA)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.EXPECTED_PRICE_TON)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.VARIETY)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.TARGET_NEXT_SEASON)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.TECH_NEEDS_I)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.TECH_NEEDS_II)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FARMER_BASE_ORG)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.PLANTING_DATE)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.LAND_AREA)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.DATE_MANUAL_WEEDING)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.POS_CONTACT)),
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MONTH_SELLING_STARTS)),

                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MONTH_FINAL_PRODUCT_SOLD)),

                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MAIN_CROP)),
                   localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.PHONENUMBER)));

            f.setBaselinepostharvest(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_BASELINEPOSTHARVEST)));
            f.setBaselineProduction(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_BASELINEPRODUCTION)));
            f.setBaselineProductionBudget(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_BASELINE_PRODUCTION_BADGET)));
            f.setPostharvest(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_POSTHARVEST)));
            f.setProduction(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_PRODUCTION)));
            f.setTechnicalNeeds(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TECH_NEEDS)));

            f.setBaselinepostharvestBudget(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_BASELINE_POST_HARVEST_BADGET)));



if(!f.getBaselinepostharvestBudget().isEmpty())
            System.out.println("F setBaselinepostharvestBudget : "+f.getBaselinepostharvestBudget());
            response.add(f);
        }
        }finally {
            localCursor.close();
        }
        return response;
    }

    public List<Farmer> getFarmers() {
        return getFarmersSearch(findAllQuery(DatabaseHelperConstants.ICTC_FARMER ));
    }


    /**
     * @param searchBy
     * @param searchValue
     * @return
     */

    public List<Farmer> getSearchedFarmers(String searchBy, String searchValue) {
        String query =findAllQuery(DatabaseHelperConstants.ICTC_FARMER ) + "  where  " + searchBy + "= '" + searchValue + "'  ";
        return getFarmersSearch(query);
    }
    public Farmer findFarmer(String  id) {
        String query =findAllQuery(DatabaseHelperConstants.ICTC_FARMER ) + "  where  " + DatabaseHelperConstants.ICTC_FARMER_ID+ "= '" + id + "' ";
        System.out.println("Query : "+query);
        List<Farmer> f = getFarmersSearch(query);
        if(f.size()>0)
            return f.get(0);

        return null;
    }

    public List<Farmer> searchFarmer(String queryString) {
        String query = findAllQuery(DatabaseHelperConstants.ICTC_FARMER )+ "  where  " + DatabaseHelperConstants.OTHER_NAMES + " like  '%" + queryString + "%' or  " + DatabaseHelperConstants.NICKNAME + " like  '%" + queryString + "%' or  " + DatabaseHelperConstants.FIRST_NAME + " LIKE '%" + queryString + "%'";
        System.out.println("Query : " + query);
        return getFarmersSearch(query);
    }

    /**
     * @return
     */
    public int farmerCount() {
        String query =getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER )+"  ";
        return getAggregateValue(query);

    }
    public int farmerCount(String searchBy, String searchValue) {
        String query =getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER)+ "  where   " + searchBy + " = '" + searchValue + "'";

        return getAggregateValue(query);
    }

    public int getAggregateValue(String query) {
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        try {

            Farmer response = null;
            while (localCursor.moveToNext()) {
                return localCursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

            localCursor.close();
        }
        return 0;
    }

    public boolean resetFarmer() {
        return deleteTable(DatabaseHelperConstants.ICTC_FARMER, "");
    }

    public boolean deleteFarmer(String id) {

        return deleteQuery(DatabaseHelperConstants.ICTC_FARMER,DatabaseHelperConstants.ICTC_FARMER_ID,id,"=");

    }
    public boolean resetWeather() {
        return deleteTable(DatabaseHelperConstants.ICTC_WEATHER_TABLE, "");
    }

    public boolean resetFarmerMeetings() {
        return deleteTable(DatabaseHelperConstants.ICTC_FARMER_MEETING, "");
    }

    public boolean resetGPSLocs() {
        return deleteTable(DatabaseHelperConstants.ICTC_GPS_LOCATION, "");
    }


    public boolean deleteQuery(String table,String fieldName,String fieldValue,String equator){

        return deleteTable(table, " where " + fieldName + equator + " '" + fieldValue + "'");
    }
    public boolean deleteQuery(String table,String fieldName,String fieldValue){

        return deleteQuery(table, fieldName, fieldValue, "=");
    }


    public boolean deleteTable(String table, String xtraSearch) {

        String q = "delete from " + table + " " + xtraSearch;
        this.getWritableDatabase().execSQL(q);
        return true;
    }



    public void updateFarmer(String farmerId,double area,double perimeter){

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.SIZE_PLOT, String.valueOf(perimeter));

        newValues.put(DatabaseHelperConstants.LAND_AREA, String.valueOf(area));
        newValues.put(DatabaseHelperConstants.TARGET_AREA, String.valueOf(area));
        System.out.println("Updating Farmer : "+farmerId);
        this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARMER, newValues, DatabaseHelperConstants.ICTC_FARMER_ID+"='"+farmerId+"'", null);

    }

    public int farmerCountGroup(String groupBy) {
        String query = getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER,"distinct" , DatabaseHelperConstants.COMMUNITY )  + "  group by   " + groupBy;
        return getAggregateValue(query);
    }


    public List<CommunityCounterWrapper> farmerCountByCommunityGroup() {
        List<CommunityCounterWrapper> wr = new ArrayList<CommunityCounterWrapper>();

        String query = " select  count(*)," + DatabaseHelperConstants.VILLAGE + " from " + DatabaseHelperConstants.ICTC_FARMER + "  group by    " + DatabaseHelperConstants.COMMUNITY;
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        try {

            Farmer response = null;
            while (localCursor.moveToNext()) {
                wr.add(new CommunityCounterWrapper(localCursor.getString(1), localCursor.getInt(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            localCursor.close();
        }
        return wr;
    }

    public List<Community> farmerCountByCommunityGroupName() {
        List<Community> wr = new ArrayList<Community>();

        String query = " select  count(*)," + DatabaseHelperConstants.VILLAGE + " from " + DatabaseHelperConstants.ICTC_FARMER + "  group by    " + DatabaseHelperConstants.VILLAGE;
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        localCursor.moveToFirst();
        try {
            Farmer response = null;
            while (localCursor.isAfterLast()==false) {
                Community c=new Community();
                c.setName(localCursor.getString(1));
                c.setMemberCount(localCursor.getString(0));
                wr.add(c);
                localCursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            localCursor.close();
        }
        return wr;
    }

    public List<Meeting> meetingTimes() {
        List<Meeting> wr = new ArrayList<Meeting>();

        String query = " select  * from " +DatabaseHelperConstants.ICTC_FARMER_MEETING;
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        localCursor.moveToFirst();
        try {
            Farmer response = null;
            while (localCursor.isAfterLast()==false) {
                Meeting m=new Meeting();
                m.setScheduledMeetingDate(localCursor.getString(6));
                m.setType(localCursor.getString(2));
                m.setTitle(localCursor.getString(4));
                wr.add(m);
                localCursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            localCursor.close();
        }
        return wr;
    }

    private String findAllQuery(String table)
    {

        return "select t.* from "+table+" t ";
    }
    private String getGeneralCountQuery(String table)
    {

        return getGeneralCountQuery(table, "", "*");
    }

public void markAttendance(String meetingIndex,String ids){

    ContentValues newValues = new ContentValues();
    newValues.put(DatabaseHelperConstants.ICTC_ATTENDED, String.valueOf(1));

    this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARMER_MEETING, newValues, DatabaseHelperConstants.ICTC_ID+" IN ("+ids+") ", null);
}
    public void markAttendanceByMeetingIndex(String meetingIndex,String ids){

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_ATTENDED, String.valueOf(1));

        this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARMER_MEETING, newValues, DatabaseHelperConstants.ICTC_MEEING_INDEX+" = "+meetingIndex+" and  "+DatabaseHelperConstants.ICTC_FARMER_ID+" IN ("+ids+") ", null);
    }

    public void markAttendanceByMeetingIndex(String meetingIndex,String ids,int attended){

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_ATTENDED, String.valueOf(attended));

        this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARMER_MEETING, newValues, DatabaseHelperConstants.ICTC_MEEING_INDEX+" = "+meetingIndex+" and  "+DatabaseHelperConstants.ICTC_FARMER_ID+" IN ("+ids+")  and  "+DatabaseHelperConstants.ICTC_TYPE+"='group' ", null);
    }


    public void markAttendanceByMeetingIndex(String meetingIndex,String ids,String type,int attended){

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_ATTENDED, String.valueOf(attended));

       int cnt =  this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARMER_MEETING, newValues, DatabaseHelperConstants.ICTC_MEEING_INDEX+" = "+meetingIndex+" and  "+DatabaseHelperConstants.ICTC_FARMER_ID+" IN ("+ids+") and  "+DatabaseHelperConstants.ICTC_TYPE+"='"+type+"'", null);


        System.out.println("Cnt  : "+cnt);

    }


    /**
     * returns queries like select count(*) from tableName
     * select count(id) from tableName , select count(distinct id) from tableName
     * where the type would be distinct leave blank if there is no extra filter
     * @param table
     * @param type
     * @param countField
     * @return
     */
    private String getGeneralCountQuery(String table,String type,String countField)
    {
        if(!type.isEmpty())
type+=" ";

        return "select count("+type+countField+") from "+table;
    }



    public FarmerInputs  getFarmerInputs(Cursor localCursor){




        // FarmerInputs(int id,String name, Date dateReceived, String status, double qty, String farmer) {
        FarmerInputs met = new FarmerInputs(
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_INPUT_NAME)),
                null,
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_STATUS)),
              localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_QTY_GIVEN)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_FARMER_ID))
        );


        return met;
    }


    public long saveFarmInput(FarmerInputs farmerInputs)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_QTY_GIVEN, farmerInputs.getQty());
        newValues.put(DatabaseHelperConstants.ICTC_FARMER_ID, farmerInputs.getFarmer());
        newValues.put(DatabaseHelperConstants.ICTC_INPUT_NAME, farmerInputs.getName());
        newValues.put(DatabaseHelperConstants.ICTC_QTY_GIVEN, farmerInputs.getQty());
        newValues.put(DatabaseHelperConstants.ICTC_DATE_RECEIVED, IctcCKwUtil.formatStringDateTime(farmerInputs.getDateReceived()));
        newValues.put(DatabaseHelperConstants.ICTC_QTY_RECEIVED, farmerInputs.getQtyReceived());
        newValues.put(DatabaseHelperConstants.ICTC_STATUS, farmerInputs.getStatus());
        return this.getWritableDatabase().insert(DatabaseHelperConstants.ICTC_FARM_INPUTS, null, newValues);
    }


    public void updateFarmInput(FarmerInputs farmerInputs){

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_QTY_GIVEN, farmerInputs.getQty());

        newValues.put(DatabaseHelperConstants.ICTC_QTY_RECEIVED, farmerInputs.getQtyReceived());
        newValues.put(DatabaseHelperConstants.ICTC_STATUS, farmerInputs.getStatus());

        this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_FARM_INPUTS, newValues, DatabaseHelperConstants.ICTC_ID+"='"+farmerInputs.getId()+"'", null);

    }


    public List<FarmerInputs> getIndividualFarmerInputs(String farmerId){
        String q="select * from "+DatabaseHelperConstants.ICTC_FARM_INPUTS+"  WHERE "+ DatabaseHelperConstants.ICTC_FARMER_ID+"= '"+farmerId+"' order by "+DatabaseHelperConstants.ICTC_INPUT_NAME+" ASC ";
        System.out.println("Query  : "+q);
        return getFarmInputQuery(q);
    }



    public List<FarmerInputs> getFarmInputQuery(String q){
        System.out.println("Query : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<FarmerInputs> response = new ArrayList<FarmerInputs>();
        while (localCursor.moveToNext()) {
            response.add(getFarmerInputs(localCursor));
        }
        localCursor.close();
        return response;
    }

    public void insertCCHLog(String module, String data, long starttime,
                             long endtime) {

        SQLiteDatabase db = this.getWritableDatabase();

        String userid = IctcCKwUtil.getUsername();
//                prefs.getString(ctx.getString(R.string.prefs_username), "noid");
        insertCCHLog(userid, module, data, String.valueOf(starttime),String.valueOf(endtime));

    }


    public void insertCCHLog(String module, String page,String section, long starttime,long endtime,Context  context) {

        System.out.println("Insert insertCCHLog ");
        SQLiteDatabase db = this.getWritableDatabase();
        String userid = IctcCKwUtil.getUsername();
        JSONObject obj = new JSONObject();
        try {
            obj.put("page", page);
            obj.put("section", section);
            obj.put("battery", IctcCKwUtil.getBatteryLevel(context));
            obj.put("version", IctcCKwUtil.getAppVersion(context));
            obj.put("imei", IctcCKwUtil.getImei(context));
        }catch (Exception e ){


        }
        String data =obj.toString();
//                prefs.getString(ctx.getString(R.string.prefs_username), "noid");
        insertCCHLog(userid,module,data,String.valueOf(starttime),String.valueOf(endtime));

    }
    /**
     *  obj.put("page", page);
     obj.put("section", section);
     obj.put("battery", (battery));
     obj.put("version", version);
     obj.put("imei", imei);
     * @param module
     * @param data
     * @param starttime
     * @param endtime
     */

    public void insertCCHLog(String module, String data, String starttime,
                             String endtime) {

        System.out.println("Insert insertCCHLog ");
        SQLiteDatabase db = this.getWritableDatabase();
        String userid = IctcCKwUtil.getUsername();
//                prefs.getString(ctx.getString(R.string.prefs_username), "noid");
      insertCCHLog(userid,module,data,starttime,endtime);

    }


    public void insertCCHLog(String userid,String module, String data, String starttime,
                             String endtime) {

        SQLiteDatabase db = this.getWritableDatabase();

        System.out.println("Data4Phone : "+userid+" - "+data);
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_USER_ID, userid);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_MODULE, module);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_DATA, data);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME, starttime);
        values.put(DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME, endtime);
        // Log.v("insertCCHLOG", values.toString());
        db.insertOrThrow(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE, null, values);
        db.close();
    }

    public int markCCHLogSubmitted(long rowId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED, 1);
        return  this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE, values, DatabaseHelperConstants.ICTC_ID + "="
                + rowId, null);
    }


    public int markCCHLogsAsSubmitted(String rowIds) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED, 1);
        return  this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE, values, DatabaseHelperConstants.ICTC_ID + " in ("
                + rowIds+")", null);
    }

    public int markCCHLogsAsSubmitting(String rowIds) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED, 2);
        return  this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE, values, DatabaseHelperConstants.ICTC_ID + " in ("
                + rowIds+")", null);
    }

    public Payload getCCHUnsentLog() {


        String s = DatabaseHelperConstants.ICTC_TRACKER_SUBMITTED + "=? ";
        String[] args = new String[] { "0" };
        Cursor c = this.getWritableDatabase().query(DatabaseHelperConstants.ICTC_TRACKER_LOG_TABLE, null, s, args, null, null, null);
        c.moveToFirst();

        int cnt=0;
        ArrayList<Object> sl = new ArrayList<Object>();
        while (c.isAfterLast() == false) {
            TrackerLog so = new TrackerLog();
            so.setId(c.getLong(c.getColumnIndex(DatabaseHelperConstants.ICTC_ID)));

            String content = "";

            try {
                JSONObject json = new JSONObject();
                json.put("user_id",
                        c.getString(c.getColumnIndex(DatabaseHelperConstants.ICTC_USER_ID)));
                json.put("id",
                        c.getString(c.getColumnIndex(DatabaseHelperConstants.ICTC_ID)));
                json.put("data",
                        c.getString(c.getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_DATA)));
                json.put("module",
                        c.getString(c.getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_MODULE)));
                json.put("start_time", c.getString(c
                        .getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_START_DATETIME)));
                json.put("end_time",
                        c.getString(c.getColumnIndex(DatabaseHelperConstants.ICTC_TRACKER_END_DATETIME)));
                content = json.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cnt++;
            so.setContent(content);
            sl.add(so);
            c.moveToNext();
        }
        System.out.println("Payload to Send Counter "+cnt);

        Payload p = new Payload(sl);
        c.close();

        return p;
    }


    public  Payload getImagePayload(ArrayList<Object> images){
        List<Object> sl=  images;
        Payload p =new Payload(images);

        return p;
    }

    private  Weather getWeather(Cursor localCursor){
        Weather met = new Weather();
        met.setDetail( localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_DETAIL)));
        met.setIcon(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ICON)));
        met.setLocation(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_LOCATION)));
        met.setTemprature(localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TEMPERATURE)));
        met.setMinTemprature(localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_MIN_TEMPERATURE)));
        met.setMaxTemprature( localCursor.getDouble(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_MAX_TEMPERATURE)));
        met.setTime(localCursor.getLong(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_TIME)));

        return  met;
    }

    public long saveWeather(Weather w)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_DETAIL, w.getDetail());
        newValues.put(DatabaseHelperConstants.ICTC_ICON, w.getIcon());
        newValues.put(DatabaseHelperConstants.ICTC_LOCATION, w.getLocation());
        newValues.put(DatabaseHelperConstants.ICTC_TEMPERATURE, w.getTemprature());
        newValues.put(DatabaseHelperConstants.ICTC_MIN_TEMPERATURE, (w.getMinTemprature()));
        newValues.put(DatabaseHelperConstants.ICTC_MAX_TEMPERATURE, w.getMinTemprature());
        newValues.put(DatabaseHelperConstants.ICTC_TIME, w.getTime());
        return this.getWritableDatabase().insert(DatabaseHelperConstants.ICTC_WEATHER_TABLE, null, newValues);
    }

    public List<Weather> getWeatherList(String q){
        System.out.println("Query : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Weather> response = new ArrayList<Weather>();
        while (localCursor.moveToNext()) {
            response.add(getWeather(localCursor));
        }
        localCursor.close();
        return response;
    }

    public List<Weather> getWeatherByCommunity(){

        Date d = new Date();
        long dt = d.getTime()/1000;

        //1450243527
        //1450612800
        System.out.println("Date k :"+new Date(dt));
        System.out.println("Date l :"+d);
        String query = findAllQuery(DatabaseHelperConstants.ICTC_WEATHER_TABLE)+ "  where  " + DatabaseHelperConstants.ICTC_TIME +" > " +dt +" "
              +  " ORDER BY "+DatabaseHelperConstants.ICTC_TIME+" ASC limit 4 ";
        System.out.println("Query Weather: " + query);
        return getWeatherList(query);
    }



    private  UserDetails getUserDetail(Cursor localCursor){
        UserDetails met = new UserDetails();
        met.setId(localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID)));
        met.setUserName(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_USER_NAME)));
        met.setFullName(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_USER_FULL_NAME)));
        met.setAgentID(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_USER_ID)));
        met.setSalesForceId(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_USER_SF_ID)));
        met.setLastModifiedDate(localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_LAST_MODIFIED_DATE)));

        return  met;
    }

    public int updateUser(String userId, long lastModified) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperConstants.ICTC_LAST_MODIFIED_DATE, String.valueOf(lastModified));
        return  this.getWritableDatabase().update(DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE, values,"", null);
    }

    public long saveUserDetail(UserDetails w)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelperConstants.ICTC_USER_NAME, w.getUserName());
        newValues.put(DatabaseHelperConstants.ICTC_USER_ID, w.getAgentID());
        newValues.put(DatabaseHelperConstants.ICTC_USER_FULL_NAME, w.getFullName());
        newValues.put(DatabaseHelperConstants.ICTC_ORGANISATION, w.getFullName());
        newValues.put(DatabaseHelperConstants.ICTC_USER_SF_ID, w.getSalesForceId());
        newValues.put(DatabaseHelperConstants.ICTC_LAST_MODIFIED_DATE,w.getLastModifiedDate());
        return this.getWritableDatabase().insert(DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE, null, newValues);
    }

    public List<UserDetails> getUserDetailList(String q){
        System.out.println("Query : "+q);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<UserDetails> response = new ArrayList<UserDetails>();
        while (localCursor.moveToNext()) {
            response.add(getUserDetail(localCursor));
        }localCursor.close();
        return response;
    }

    public void resetUser(){
        deleteTable(DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE,"");
    }


    public UserDetails getUserItem(){
        List<UserDetails> users  =  getUserDetailList(findAllQuery(DatabaseHelperConstants.ICTC_USER_DETAIL_TABLE));
        if(users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    public void createUser(){

        this.getWritableDatabase().execSQL(getICTCUser());
        this.getWritableDatabase().execSQL(getICTCWeather());
    }


    // myAgriHub module methods
    public boolean needDataUpdate() {
        return false;
    }


    private String getAGSMOUser() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.AGSMO_USER_TABLE);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_TYPE).append(" text,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_NAME).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_FULLNAME).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_AGE).append(" integer ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_GENDER).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_PHONE).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_MOBILE).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_EMAIL).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_USER_LOCATION).append(" text ,");
        sqlCommand.append(DatabaseHelperConstants.AGSMO_LAST_MODIFIED_DATE).append(" text");
        sqlCommand.append(");");
        return sqlCommand.toString();
    }
    public boolean isFieldExist(String tableName, String fieldName)
    {
        boolean isExist = true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("PRAGMA table_info("+tableName+")",null);
        int value = res.getColumnIndex(fieldName);

        if(value == -1)
        {
            isExist = false;
        }
            return isExist;

    }
}
