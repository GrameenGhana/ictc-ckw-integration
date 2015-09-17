package applab.client.search.storage;

import android.provider.BaseColumns;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Utility class that contains the data storage contacts.
 *
 * @author Charles Tumwebaze
 */
public final class DatabaseHelperConstants {
    private DatabaseHelperConstants() {
    }

    /**
     * default date format;
     */
    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    /* Full farmer list - Local table */
    public static final String FARMERS_ROWID_COLUMN = "id";
    public static final String FARMERS_FARMER_ID = "farmer_id";
    public static final String FARMERS_FIRST_NAME = "first_name";
    public static final String FARMERS_LAST_NAME = "last_name";
    public static final String FARMERS_CREATION_DATE = "creation_date";
    public static final String FARMERS_SUBCOUNTY = "subcounty";
    public static final String FARMERS_VILLAGE = "village";

    /**
     * search log table columns
     */
    public static final String SEARCH_LOG_ROW_ID_COLUMN = "id";
    public static final String SEARCH_LOG_MENU_ITEM_ID_COLUMN = "menu_item_id";
    public static final String SEARCH_LOG_DATE_CREATED_COLUMN = "date_created";
    public static final String SEARCH_LOG_CLIENT_ID_COLUMN = "client_id";
    public static final String SEARCH_LOG_GPS_LOCATION_COLUMN = "gps_location";
    public static final String SEARCH_LOG_CONTENT_COLUMN = "content";
    public static final String SEARCH_LOG_CONTENT_CATEGORY_COLUMN = "category";
    public static final String SEARCH_LOG_TEST_LOG = "test_log";

    /**
     * favourite record table columns
     */
    public static final String FAVOURITE_RECORD_ROW_ID_COLUMN = "id";
    public static final String FAVOURITE_RECORD_NAME_COLUMN = "name";
    public static final String FAVOURITE_RECORD_CATEGORY_COLUMN = "category";
    public static final String FAVOURITE_RECORD_DATE_CREATED_COLUMN = "date_created";
    public static final String FAVOURITE_RECORD_MENU_ITEM_ID_COLUMN = "menu_item_id";

    /**
     * table names
     */
    public static final String MENU_TABLE_NAME = "menu";
    public static final String MENU_ITEM_TABLE_NAME = "menu_item";
    public static final String AVAILABLE_FARMER_ID_TABLE_NAME = "available_farmer_id";
    public static final String FARMER_LOCAL_CACHE_TABLE_NAME = "farmer_local_cache";
    public static final String FARMER_LOCAL_DATABASE_TABLE_NAME = "farmer_local_database";
    public static final String SEARCH_LOG_TABLE_NAME = "search_log";
    public static final String FAVOURITE_RECORD_TABLE_NAME = "favourite_record";

    public static final String DATABASE_NAME = "gfsearch";
    public static final int DATABASE_VERSION = 4;

    // ICTC Stuff
    public static final String ICTC_FARMER = "ictc_farmer";
    public static final String FIRST_NAME = "fname";
    public static final String OTHER_NAMES = "lname";
    public static final String COMMUNITY = "community";
    public static final String DISTRICT = "district";
    public static final String REGION = "region";
    public static final String GENDER = "gender";
    public static final String EDUCATION = "edu";
    public static final String NICKNAME = "nickname";
    public static final String VILLAGE = "village";
    public static final String NO_OF_CHILD = "noc";
    public static final String AGE = "age";
    public static final String MARITAL_STATUS = "ms";
    public static final String NO_OF_DEPENDANT = "nod";
    public static final String FARMER_ID = "farmer_id";
    public static final String CLUSTER = "cluster";



    public static final String ICTC_FARMER_MEETING= "ictc_meeting";
    public static final String ICTC_TYPE= "meeting_type";
    public static final String ICTC_SEASON= "season";
    public static final String ICTC_REMARK= "remark";
    public static final String ICTC_DESC= "desc";
    public static final String ICTC_MEEING_INDEX="meeting_index";
    public static final String ICTC_TITLE= "title";
    public static final String ICTC_ATTENDED= "attended";
    public static final String ICTC_ACTUAL_MEETING_DATE= "actual_meet_date";
    public static final String ICTC_SCHEDULE_DATE= "schedule_date";
    public static final String ICTC_PROCEDUREE= "meeting_procedure";
    public static final String ICTC_CROP= "crop";
    public static final String ICTC_FARMER_MEETING_PROCEDURE= "ictc_meeting_PROCEDURE";




    public static final String SIZE_PLOT = "size_plot";
    public static final String MAIN_CROP = "main_crop";
    public static final String LABOUR = "labour";
    public static final String DATE_OF_LAND_IDENTIFICATION = "date_land_ident";
    public static final String LOCATION_LAND = "loc_land";
    public static final String TARGET_AREA = "target_area";
    public static final String EXPECTED_PRICE_TON = "exp_price_ton";
    public static final String VARIETY = "variety";
    public static final String EDUCATOION = "edu";
    public static final String TARGET_NEXT_SEASON = "target_nxt_season";
    public static final String TECH_NEEDS_I = "techneed1";
    public static final String TECH_NEEDS_II = "techneed2";
    public static final String FARMER_BASE_ORG = "fbo";
    //    public static final String REGION = "reg";
    public static final String PLANTING_DATE = "date_plant";
    public static final String LAND_AREA = "landarea";
    public static final String DATE_MANUAL_WEEDING = "date_man_weed";
    public static final String POS_CONTACT = "pos_contact";
    public static final String MONTH_SELLING_STARTS = "mon_sell_start";
    public static final String MONTH_FINAL_PRODUCT_SOLD = "mon_fin_pro_sold";

    public static final String ICTC_ID = BaseColumns._ID;


    //Crop calendar

    public static final String ICTC_CROP_CALENDAR = "ictc_crop_calendar";
    public static final String ICTC_ACTIVITY = "actiity";
    public static final String ICTC_ACTIVITY_INDEX = "activity_index";
    public static final String ICTC_WEEKS_FROM_PLANTING = "week_frm_planting";
    public static final String ICTC_COMMENT = "comment";
//    public static final String ICTC_SEASON= "season";
    public static final String ICTC_FARMER_ID= "farmer_id";
    public static final String ICTC_EARLIEST_START_DATE = "earliest_start";
    public static final String ICTC_LATEST_START_DATE= "latest_start";
    public static final String ICTC_ACTUAL_DATE= "actual_date";


    public static final String ICTC_GPS_LOCATION = "ictc_farm_grp";
    public static final String ICTC_LATITUDE= "latitude";
    public static final String ICTC_LONGITUDE= "longitude";


    public static final String ICTC_FARM_INPUTS= "ictc_farm_inputs";
    public static final String ICTC_INPUT_NAME= "name";
    public static final String ICTC_QTY_RECEIVED= "qty";
    public static final String ICTC_QTY_GIVEN= "qty_given";
    public static final String ICTC_DATE_RECEIVED= "date_received";
    public static final String ICTC_STATUS= "status";
    public static final String ICTC_DATE_CREATED= "date_created";


    /**
     * Farm Management Plan
     */


    private static String FMP_DATEOFLANDIDENTIFICATION = "dateoflandidentification";
    private static String FMP_LOCATIONOFLAND = "locationofland";
    private static String FMP_TARGETAREAOFLAND = "targetareaofland";
    private static String FMP_COSTOFRENTINGLAND = "costofrentingland";
    private static String FMP_TARGETYIELDPERACRE = "targetyieldperacre";
    private static String FMP_EXPECTEDPRICEPERTON = "expectedpriceperton";
    private static String FMP_TARGETPRODUCTION = "targetproduction";
    private static String FMP_NAMEOFVARIETY = "nameofvariety";
    private static String FMP_PURCHASEOFSTAKESDATE = "purchaseofstakesdate";
    private static String FMP_QUANTITYOFPLANTINGMATERIAL = "quantityofplantingmaterial";
    private static String FMP_WEIGHTOFPLANTINGMATERIALPERACRE = "weightofplantingmaterialperacre";
    private static String FMP_QUANTITYOFPLANTINGMATERIALPERACRE = "quantityofplantingmaterialperacre";
    private static String FMP_COSTPERUNITOFPLANTINGMATERIAL = "costperunitofplantingmaterial";
    private static String FMP_METHODOFLANDCLEARING = "methodoflandclearing";
    private static String FMP_TYPEOFHERBICIDEFORPREPLANTING = "typeofherbicideforpreplanting";
    private static String FMP_QUANTITYOFHERBICIDE = "quantityofherbicide";
    private static String FMP_PURCHASEOFHERBICIDEDATE = "purchaseofherbicidedate";
    private static String FMP_APPLICATIONOFHERBICIDEDATE = "applicationofherbicidedate";
    private static String FMP_COSTOFAPPLICATIONOFHERBICIDE = "costofapplicationofherbicide";
    private static String FMP_PLOUGHINGDATE = "ploughingdate";
    private static String FMP_PLOUGHINGCOSTPERACRE = "ploughingcostperacre";
    private static String FMP_HARROWINGDATE = "harrowingdate";
    private static String FMP_HARROWINGCOSTPERACRE = "harrowingcostperacre";
    private static String FMP_HOEPLOUGHINGDATE = "hoeploughingdate";
    private static String FMP_HOEPLOUGHINGNUMBEROFLABORHANDS = "hoeploughingnumberoflaborhands";
    private static String FMP_HOEPLOUGHINGNUMBEROFHIREDLABOR = "hoeploughingnumberofhiredlabor";
    private static String FMP_HOEPLOUGHINGNUMBEROFFAMILYLABOR = "hoeploughingnumberoffamilylabor";
    private static String FMP_HOEPLOUGHINGCOMPENSATION = "hoeploughingcompensation";
    private static String FMP_SEEDBEDFORMTYPE = "seedbedformtype";
    private static String FMP_SEEDBEDPREPARATIONDATE = "seedbedpreparationdate";
    private static String FMP_SEEDBEDPREPERATIONNUMBEROFLABORHANDS = "seedbedpreperationnumberoflaborhands";
    private static String FMP_SEEDBEDPREPARATIONTIME = "seedbedpreparationtime";
    private static String FMP_SEEDBEDPREPARATIONHIREDLABOR = "seedbedpreparationhiredlabor";
    private static String FMP_SEEDBEDPREPARATIONFAMILYLABOR = "seedbedpreparationfamilylabor";
    private static String FMP_SEEDBEDPREPARATIONCOMPENSATION = "seedbedpreparationcompensation";
    private static String FMP_PLANTINGDATE = "plantingdate";
    private static String FMP_LENGTHOFCUTTINGSUSED = "lengthofcuttingsused";
    private static String FMP_PLANTINGDISTANCEBETWEENROWS = "plantingdistancebetweenrows";
    private static String FMP_PLANTINGDISTANCEBETWEENPLANTS = "plantingdistancebetweenplants";
    private static String FMP_PLANTINGNUMBEROFLABORHANDS = "plantingnumberoflaborhands";
    private static String FMP_PLANTINGTIME = "plantingtime";
    private static String FMP_PLANTINGHIREDLABOR = "plantinghiredlabor";
    private static String FMP_PLANTINGCOMPENSATION = "plantingcompensation";
    private static String FMP_REFILLINGGAPSNUMBEROFLABORHANDS = "refillinggapsnumberoflaborhands";
    private static String FMP_REFILLINGGAPSTIME = "refillinggapstime";
    private static String FMP_REFILLINGGAPSHIREDLABOR = "refillinggapshiredlabor";
    private static String FMP_REFILLINGGAPSCOMPENSATION = "refillinggapscompensation";
    private static String FMP_PURCHASEOFHERBICIDEPOSTPLANT = "purchaseofherbicidepostplant";
    private static String FMP_TYPEOFHERBICIDEFORPOSTPLANTWEEDCONTROL = "typeofherbicideforpostplantweedcontrol";
    private static String FMP_QUANTITYOFPOSTPLANTHERBICIDE = "quantityofpostplantherbicide";
    private static String FMP_PRICEOFHERBICIDEPOSTPLANT = "priceofherbicidepostplant";
    private static String FMP_SEEDBEDFORMTYPEPOSTPLANT = "seedbedformtypepostplant";
    private static String FMP_COSTOFAPPLICATIONOFHERBICIDEPOSTPLANT = "costofapplicationofherbicidepostplant";
    private static String FMP_TYPEOFBASALFERTILIZER = "typeofbasalfertilizer";
    private static String FMP_PURCHASEOFBASALFERTILIZERDATE = "purchaseofbasalfertilizerdate";
    private static String FMP_QUANTITYOFBASALFERTILIZERPURCHASED = "quantityofbasalfertilizerpurchased";
    private static String FMP_COSTPERUNITOFFERTILIZERAPPLIED = "costperunitoffertilizerapplied";
    private static String FMP_METHODOFBASALFERTILIZERAPPLICATION = "methodofbasalfertilizerapplication";
    private static String FMP_TIMEAPPLICATIONOFBASALFERTILIZER = "timeapplicationofbasalfertilizer";
    private static String FMP_TOTALNUMBEROFLABORBASALFERTILIZER = "totalnumberoflaborbasalfertilizer";
    private static String FMP_TIMEFORCOMPLETIONBASALAPPLICATION = "timeforcompletionbasalapplication";
    private static String FMP_NUMBEROFFAMILYLABORBASALFERTILIZER = "numberoffamilylaborbasalfertilizer";
    private static String FMP_NUMBEROFHIREDLABORBASALFERTILIZER = "numberofhiredlaborbasalfertilizer";
    private static String FMP_TOTALCOSTOFHIREDLABORBASALFERTILIZER = "totalcostofhiredlaborbasalfertilizer";
    private static String FMP_TYPEOFTOPDRESSFERTILIZER = "typeoftopdressfertilizer";
    private static String FMP_QUANTITYOFTOPDRESSERFERTILIZER = "quantityoftopdresserfertilizer";
    private static String FMP_PURCHASEOFTOPDRESSFERTILIZERDATE = "purchaseoftopdressfertilizerdate";
    private static String FMP_PRICEOFTOPDRESSERFERTILIZER = "priceoftopdresserfertilizer";
    private static String FMP_METHODOFTOPDRESSERFERTILIZERAPPLICATION = "methodoftopdresserfertilizerapplication";
    private static String FMP_TIMEAPPLICATIONTOPDRESSING = "timeapplicationtopdressing";
    private static String FMP_NUMBEROFLABORTOPDRESSINGFERTILIZER = "numberoflabortopdressingfertilizer";
    private static String FMP_TIMEFORCOMPLETIONTOPDRESSERFERTILIZER = "timeforcompletiontopdresserfertilizer";
    private static String FMP_NUMBEROFFAMILYLABOURTOPDRESSER = "numberoffamilylabourtopdresser";
    private static String FMP_NUMBEROFHIREDLABORTOPDRESS = "numberofhiredlabortopdress";
    private static String FMP_COSTOFHIREDLABORTOPDRESSERFERTILIZER = "costofhiredlabortopdresserfertilizer";
    private static String FMP_NUMBEROCCASIONMANUALWEEDCONTROL = "numberoccasionmanualweedcontrol";
    private static String FMP_DATEFIRSTOCCASIONMANUALWEEDCONTROL = "datefirstoccasionmanualweedcontrol";
    private static String FMP_NUMBEROFLABORFIRSTOCCASIONWEEDCONTROL = "numberoflaborfirstoccasionweedcontrol";
    private static String FMP_TIMECOMPLETIONFIRSTMANUALWEEDCONTROL = "timecompletionfirstmanualweedcontrol";
    private static String FMP_NUMBERFAMILYLABORFIRSTMANUALWEEDCONTROL = "numberfamilylaborfirstmanualweedcontrol";
    private static String FMP_NUMBERHIREDLABORFIRSTMANUALWEEDCONTROL = "numberhiredlaborfirstmanualweedcontrol";
    private static String FMP_COSTHIREDLABORFIRSTMANUALWEEDCONTROL = "costhiredlaborfirstmanualweedcontrol";
    private static String FMP_NUMBEROFLABORSECONDWEEDCONTROL = "numberoflaborsecondweedcontrol";
    private static String FMP_TIMECOMPLETIONSECONDMANUALWEENCONTROL = "timecompletionsecondmanualweencontrol";
    private static String FMP_NUMBERFAMILYLABORSECONDMANUALWEEDCONTROL = "numberfamilylaborsecondmanualweedcontrol";
    private static String FMP_NUMBERHIREDLABORSECONDMANUALWEEDCONTROL = "numberhiredlaborsecondmanualweedcontrol";
    private static String FMP_COSTOFHIREDLABORSECONDMANUALWEEDCONTROL = "costofhiredlaborsecondmanualweedcontrol";
    private static String FMP_DATEOFTHIRDMANUALWEEDCONTROL = "dateofthirdmanualweedcontrol";
    private static String FMP_NUMBEROFLABORTHIRDMANUALWEEDCONTROL = "numberoflaborthirdmanualweedcontrol";
    private static String FMP_TIMECOMPLETIONTHIRDMANUALWEEDCONTROL = "timecompletionthirdmanualweedcontrol";
    private static String FMP_NUMBERFAMILYLABORTHIRDMANUALWEEDCONTROL = "numberfamilylaborthirdmanualweedcontrol";
    private static String FMP_NUMBERHIREDLABORTHIRDMANUALWEEDCONTROL = "numberhiredlaborthirdmanualweedcontrol";
    private static String FMP_COSTOFHIREDLABOURTHIRDMANUALWEEDCONTROL = "costofhiredlabourthirdmanualweedcontrol";
    private static String FMP_DATEFOURTHMANUALWEEDCONTROL = "datefourthmanualweedcontrol";
    private static String FMP_NUMBERLABORFOURTHMANUALWEEDCONTROL = "numberlaborfourthmanualweedcontrol";
    private static String FMP_TIMECOMPLETIONFOURTHMANUALWEEDCONTROL = "timecompletionfourthmanualweedcontrol";
    private static String FMP_NUMBERFAMILYLABORFOURTHMANUALWEEDCONTROL = "numberfamilylaborfourthmanualweedcontrol";
    private static String FMP_NUMBERHIREDLABORFOURTHMANUALWEEDCONTROL = "numberhiredlaborfourthmanualweedcontrol";
    private static String FMP_COSTHIREDLABORFOURTHMANUALWEEDCONTROL = "costhiredlaborfourthmanualweedcontrol";
    private static String FMP_DATEFIFTHMANUALWEEDCONTROL = "datefifthmanualweedcontrol";
    private static String FMP_NUMBERLABORFIFTHWEEDCONTROL = "numberlaborfifthweedcontrol";
    private static String FMP_TIMECOMPLETIONFIFTHMANUALWEEDCONTROL = "timecompletionfifthmanualweedcontrol";
    private static String FMP_NUMBERFAMILYLABORFIFTHMANUALWEEDCONTROL = "numberfamilylaborfifthmanualweedcontrol";
    private static String FMP_NUMBERHIREDLABOURFIFTHMANUAWEEDCONTROL = "numberhiredlabourfifthmanuaweedcontrol";
    private static String FMP_COSTHIREDLABORFIFTHMANUALWEEDCONTROL = "costhiredlaborfifthmanualweedcontrol";
    private static String FMP_HARVESTDATE = "harvestdate";
    private static String FMP_NUMBEROFLABORHARVEST = "numberoflaborharvest";
    private static String FMP_TIMECOMPLETIONHARVEST = "timecompletionharvest";
    private static String FMP_SEEDBEDFORMTYPEHARVEST = "seedbedformtypeharvest";
    private static String FMP_NUMBEROFHIREDLABORHARVEST = "numberofhiredlaborharvest";
    private static String FMP_YIELDPERACRE = "yieldperacre";
    private static String FMP_QUANTITYCASSAVAHARVESTEDPROCESSED = "quantitycassavaharvestedprocessed";
    private static String FMP_PEELINGSTARTOFDRYINGDATE = "peelingstartofdryingdate";
    private static String FMP_METHODOFPROCESSINGDEHUSKINGPEELING = "methodofprocessingdehuskingpeeling";
    private static String FMP_NUMBEROFLABORDEHUSKINPEELING = "numberoflabordehuskinpeeling";
    private static String FMP_TIMECOMPLETIONDEHUSKINGPEELING = "timecompletiondehuskingpeeling";
    private static String FMP_NUMBERFAMILYLABORDEHUSKINGPEELING = "numberfamilylabordehuskingpeeling";
    private static String FMP_NUMBERHIREDLABORDEHUSKINGPEELING = "numberhiredlabordehuskingpeeling";
    private static String FMP_COSTHIREDLABORDEHUSKINGPEELING = "costhiredlabordehuskingpeeling";
    private static String FMP_METHODOFDRYING = "methodofdrying";
    private static String FMP_NUMBEROFLABORDRYING = "numberoflabordrying";
    private static String FMP_TIMECOMPLETIONDRYING = "timecompletiondrying";
    private static String FMP_NUMBERFAMILYLABORDRYING = "numberfamilylabordrying";
    private static String FMP_NUMBERHIREDLABORDRYING = "numberhiredlabordrying";
    private static String FMP_COSTOFHIREDLABORDRYING = "costofhiredlabordrying";
    private static String FMP_METHODOFTHRESHING = "methodofthreshing";
    private static String FMP_NUMBEROFLABORTTHRESHING = "numberoflabortthreshing";
    private static String FMP_TIMECOMPLETIONTHRESHING = "timecompletionthreshing";
    private static String FMP_NUMBERFAMILYLABORTHRESHING = "numberfamilylaborthreshing";
    private static String FMP_NUMBEROFHIREDLABORTHRESHING = "numberofhiredlaborthreshing";
    private static String FMP_COSTOFHIREDLABORTHRESHING = "costofhiredlaborthreshing";
    private static String FMP_METHODOFWINNOWING = "methodofwinnowing";
    private static String FMP_NUMBEROFLABORWINNOWING = "numberoflaborwinnowing";
    private static String FMP_TIMECOMPLETIONWINNOWING = "timecompletionwinnowing";
    private static String FMP_NUMBERFAMILYLABORWINNOWING = "numberfamilylaborwinnowing";
    private static String FMP_NUMBERHIREDLABORWINNOWING = "numberhiredlaborwinnowing";
    private static String FMP_METHODOFDRYINGGRAIN = "methodofdryinggrain";
    private static String FMP_NUMBERLABORDRYINGGRAIN = "numberlabordryinggrain";
    private static String FMP_TIMECOMPLETIONDRYINGGRAIN = "timecompletiondryinggrain";
    private static String FMP_NUMBERFAMILYLABORDRYINGGRAIN = "numberfamilylabordryinggrain";
    private static String FMP_NUMBERHIREDLABORDRYINGGRAIN = "numberhiredlabordryinggrain";
    private static String FMP_COSTOFHIREDLABORDRYINGGRAIN = "costofhiredlabordryinggrain";
    private static String FMP_TYPEOFBAGSSTORINGGRAIN = "typeofbagsstoringgrain";
    private static String FMP_NUMBERSTORAGEBAGSPURCHASEDUSED = "numberstoragebagspurchasedused";
    private static String FMP_COSTPERUNITSTORAGEBAGS = "costperunitstoragebags";
    private static String FMP_TYPEOFCHEMICALSTORAGE = "typeofchemicalstorage";
    private static String FMP_COSTPERUNITSTORAGECHEMICAL = "costperunitstoragechemical";
    private static String FMP_NUMBEROFUNITSSTORAGECHEMICAL = "numberofunitsstoragechemical";
    private static String FMP_APPLICATIONRATESTORAGECHEMICAL = "applicationratestoragechemical";
    private static String FMP_NUMBERLABORBAGGING = "numberlaborbagging";
    private static String FMP_TIMECOMPLETIONBAGGING = "timecompletionbagging";
    private static String FMP_NUMBERFAMILYLABORBAGGING = "numberfamilylaborbagging";
    private static String FMP_NUMBERHIREDLABORBAGGING = "numberhiredlaborbagging";
    private static String FMP_COSTHIREDLABORBAGGING = "costhiredlaborbagging";
    private static String FMP_TYPEOFSTORAGESTRUCTURE = "typeofstoragestructure";
    private static String FMP_OWNERSHIPCONDITIONSFORSTORAGE = "ownershipconditionsforstorage";
    private static String FMP_AMOUNTCHARGEDPERUNITTIMEOFSTORAGE = "amountchargedperunittimeofstorage";
    private static String FMP_TYPEOFSTORAGECHEMICALUSEDSTORAGESTRUCTURE = "typestoragechemicalusedstoragestructure";
    private static String FMP_QUANTITYUSEDPERUNITOFPRODUCE = "quantityusedperunitofproduce";
    private static String FMP_PRICEPERUNITSTORAGECHEMICALUSEDINSTORAGE = "priceperunitstoragechemicalusedinstorage";
    private static String FMP_MAINPOINTOFSALEORCONTACT = "mainpointofsaleorcontact";
    private static String FMP_MONTHSELLINGBEGINS = "monthsellingbegins";
    private static String FMP_PRICEFIRSTHARVESTPRODUCESOLD = "pricefirstharvestproducesold";
    private static String FMP_MONTHCASSAVAFRESHROOTHARVESTSOLD = "monthcassavafreshrootharvestsold";
    private static String FMP_PRICEHARVESTPRODUCESOLD = "priceharvestproducesold";
    private static String FMP_MONTHFINALPRODUCTSOLD = "monthfinalproductsold";
    private static String FMP_PRICEFINALBATCHPRODUCESOLD = "pricefinalbatchproducesold";


}
