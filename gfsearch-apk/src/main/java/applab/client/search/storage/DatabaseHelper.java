package applab.client.search.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import applab.client.search.model.CommunityCounterWrapper;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.model.MeetingProcedure;

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
    public void onCreate(SQLiteDatabase database) {
        System.out.println("Create DB Started");
        createDatabaseTables(database);
        System.out.println("After SB Created");
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

        System.out.println("ICTC table L ");
        //Create ICTC FarmerTable
        database.execSQL(getICTCFarmerTable());
        database.execSQL(getICTCFarmerMeetings());
        database.execSQL(getICTCFarmerMeetingsProcedure());
        System.out.println("After table");
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

    /**
     *
     * @return
     */
    private String getICTCFarmerMeetings() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.ICTC_FARMER_MEETING);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TYPE).append(" TEXT,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_MEEING_INDEX).append(" INT DEFAULT 0,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_TITLE).append(" TEXT DEFAULT '',");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ACTUAL_MEETING_DATE).append(" DATE DEFAULT  NULL,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_SCHEDULE_DATE).append(" DATE,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_ATTENDED).append(" INT DEFAULT -1,");
        sqlCommand.append(DatabaseHelperConstants.ICTC_REMARK).append(" TEXT DEFAULT '' ");
        sqlCommand.append(");");
        return sqlCommand.toString();

    } private String getICTCFarmerMeetingsProcedure() {
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
    //CREATING THE ICTC FARMER TABLE
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
        sqlCommand.append(DatabaseHelperConstants.MONTH_FINAL_PRODUCT_SOLD).append(" TEXT DEFAULT ''");

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
                             String education, String cluster, String farmID) {
        return saveFarmer(firstName, lastName, nickname, community, village, district,
                region, age, gender, maritalStatus, numberOfChildren, numberOfDependants,
                education, cluster, farmID, "", "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "");
    }

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
                             String mainCrop) {
        SQLiteDatabase db = getWritableDatabase();
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
        values.put(DatabaseHelperConstants.DISTRICT, district);
        values.put(DatabaseHelperConstants.REGION, region);
        values.put(DatabaseHelperConstants.AGE, age);
        values.put(DatabaseHelperConstants.GENDER, gender);
        values.put(DatabaseHelperConstants.MARITAL_STATUS, maritalStatus);
        values.put(DatabaseHelperConstants.NO_OF_CHILD, numberOfChildren);
        values.put(DatabaseHelperConstants.NO_OF_DEPENDANT, numberOfDependants);

        values.put(DatabaseHelperConstants.CLUSTER, cluster);
        values.put(DatabaseHelperConstants.EDUCATION, education);

        System.out.println("Saving MainCrop : " + maritalStatus);


        values.put(DatabaseHelperConstants.FARMER_ID, farmID);

        db.insert(DatabaseHelperConstants.ICTC_FARMER, null, values);
        //    public Farmer(String firstName, String lastName, String village, String nickname, String community, String district, String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants, String education, String cluster, String farmID, String sizePlot, String labour, String dateOfLandIdentification, String locationOfLand, String targetArea, String expectedPriceInTon, String variety, String targetNextSeason, String techNeeds1, String techNeeds2, String farmerBasedOrg, String plantingDate, String landArea, String dateManualWeeding, String posContact, String monthSellingStarts, String monthFinalProductSold) {

        return new Farmer(firstName, lastName, nickname, community, village, district, region, age, gender, maritalStatus, numberOfChildren, numberOfDependants, education, cluster, farmID,
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
                MONTH_FINAL_PRODUCT_SOLD, mainCrop
        );
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
        return  met;
    }


    public List<MeetingProcedure> getMeetingProcedure(String q){
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<MeetingProcedure> response = new ArrayList<MeetingProcedure>();
        while (localCursor.moveToNext()) {
            response.add(getMeetingProcedure(localCursor));
        }
        return response;
    }


    public List<MeetingProcedure> getMeetingAllProcedure(){

        String q=findAllQuery(DatabaseHelperConstants.ICTC_FARMER_MEETING_PROCEDURE);
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<MeetingProcedure> response = new ArrayList<MeetingProcedure>();
        while (localCursor.moveToNext()) {
            response.add(getMeetingProcedure(localCursor));
        }
        return response;
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
        return null;
    }



    private Meeting getMeeting(Cursor localCursor){

        Date scheduled=null;
        Date actual=null;
        String schduleDate   = localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME));
        String actulDate   = localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME));
//        if(!schduleDate.isEmpty())

        Meeting met = new Meeting(
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                scheduled,
                actual,
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getInt(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME)),
                localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.FIRST_NAME))
        );
        return  met;
    }


    public List<Meeting> getMeetings(String q){
        Cursor localCursor = this.getWritableDatabase().rawQuery(q, null);
        List<Meeting> response = new ArrayList<Meeting>();
        while (localCursor.moveToNext()) {
            response.add(getMeeting(localCursor));
        }

        return response;
    }
    public List<Meeting> getFarmerMeetings(String farmer){
        return getMeetings(findAllQuery(DatabaseHelperConstants.ICTC_FARMER )+" WHERE "+DatabaseHelperConstants.ICTC_FARMER+" ='"+farmer+"'");
    }
    public int getMeetingsAttended(String farmer){
        return getAggregateValue("SELECT count(*) FROM "+DatabaseHelperConstants.ICTC_FARMER_MEETING+" WHERE "+DatabaseHelperConstants.ICTC_FARMER+" ='"+farmer+"' and attended=1");
    }

/**
     * @return
     */

    public List<Farmer> getFarmersSearch(String query) {
        Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
        List<Farmer> response = new ArrayList<Farmer>();
        while (localCursor.moveToNext()) {

            System.out.println("MM : " + localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MAIN_CROP)));
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
                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.ICTC_ID)),

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

                    localCursor.getString(localCursor.getColumnIndex(DatabaseHelperConstants.MAIN_CROP))));
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
        String query =findAllQuery(DatabaseHelperConstants.ICTC_FARMER ) + "  where  " + searchBy + "= '" + searchValue + "' ";
        return getFarmersSearch(query);
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
        String query =getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER )+ "  where   " + searchBy + " = '" + searchValue + "'";

        return getAggregateValue(query);
    }

    public int getAggregateValue(String query) {

        try {
            Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
            Farmer response = null;
            while (localCursor.moveToNext()) {
                return localCursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean resetFarmer() {
        return deleteTable(DatabaseHelperConstants.ICTC_FARMER, "");
    }    public boolean resetFarmerMeetings() {
        return deleteTable(DatabaseHelperConstants.ICTC_FARMER_MEETING, "");
    }


    public boolean deleteTable(String table, String xtraSearch) {

        String q = "delete from " + table + " " + xtraSearch;
        this.getWritableDatabase().execSQL(q);
        return true;
    }

    public int farmerCountGroup(String groupBy) {
        String query = getGeneralCountQuery(DatabaseHelperConstants.ICTC_FARMER,"distinct" , DatabaseHelperConstants.COMMUNITY )  + "  group by   " + groupBy;
        return getAggregateValue(query);
    }


    public List<CommunityCounterWrapper> farmerCountByCommunityGroup() {
        List<CommunityCounterWrapper> wr = new ArrayList<CommunityCounterWrapper>();

        String query = " select  count(*)," + DatabaseHelperConstants.COMMUNITY + " from " + DatabaseHelperConstants.ICTC_FARMER + "  group by    " + DatabaseHelperConstants.COMMUNITY;
        try {
            Cursor localCursor = this.getWritableDatabase().rawQuery(query, null);
            Farmer response = null;
            while (localCursor.moveToNext()) {
                wr.add(new CommunityCounterWrapper(localCursor.getString(1), localCursor.getInt(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wr;
    }

    private String findAllQuery(String table)
    {

        return "select * from "+table;
    }
    private String getGeneralCountQuery(String table)
    {

        return getGeneralCountQuery(table,"","*");
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
}
