package applab.client.search.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import applab.client.search.R;
import applab.client.search.activity.FarmerDetailActivity;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;
import applab.client.search.model.UserDetails;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.ui.ImageViewerActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by skwakwa on 9/10/15.
 */
public class IctcCKwUtil {

    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");

    public  static  final String KEY_USER_NAME="key_app_cache_username";
    public  static  final String KEY_FULNAME="key_app_cache_fullname";
    public  static  final String KEY_VERSION="key_app_cache_version";

    public static final String APP_VERSION="1.1.4";

    public static Date formatDateTime(String timeToFormat) {
        return formatDateTime(timeToFormat,"yyyyMMddHHmmss");
    }

    public static String getReadableDate(String timeToFormat) {
        if(!timeToFormat.isEmpty()) {
            Date dt = formatDateTime(timeToFormat, "YYYY-mm-dd");
            return formatStringDateTime(dt, "dd MMM, YYYY");
        }
        return  "";
    }

    public static Date formatSlashDates(String timeToFormat) {
        return formatDateTime(timeToFormat,"dd/MM/yyyy");
    }

    public static Date formatDateTime(String timeToFormat,String format) {

        String finalDateTime = "";
        Date date = null;
        try {

        SimpleDateFormat iso8601Format = new SimpleDateFormat( format);
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }
        }
        }catch(Exception e){

        }
        return date;
    }

    public static String formatStringDateTime(Date timeToFormat) {
        return formatStringDateTime(timeToFormat,"yyyyMMddHHmmss");
    }

    public static String formatStringDateTime(Date timeToFormat,String  dateFormat) {
        String finalDateTime = "";
        String date = "";
        try {
            SimpleDateFormat iso8601Format = new SimpleDateFormat(
                    dateFormat);


            if (timeToFormat != null) {
                try {
                    date = iso8601Format.format(timeToFormat);
                } catch (Exception e) {
                    date = "";
                }
            }
        }catch(Exception e){
        }
        return date;
    }
    public static  String getNextDate(int dayOfWeek){
        Date date  = new Date();
        Calendar cal = Calendar.getInstance();
        String replaceWith ="";
        int currentDayofWeek =  cal.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == currentDayofWeek)
            replaceWith =  "Today ";
        else if((dayOfWeek-1)%7 ==  currentDayofWeek)
            replaceWith =  "Tomorrow ";
        else
        {
            int weekAddition =7;
            if(dayOfWeek>currentDayofWeek)
                weekAddition = 0;
            cal.add(Calendar.DATE, (weekAddition-currentDayofWeek+dayOfWeek));
            SimpleDateFormat df = new SimpleDateFormat("E  MMM d");
            replaceWith = df.format(cal.getTime());
        }
        
        return replaceWith;
    }

    public static  String getNextDate(int dayOfWeek,int weeksMore){
        Date date  = new Date();
        Calendar cal = Calendar.getInstance();
        String replaceWith ="";
        int currentDayofWeek =  cal.get(Calendar.DAY_OF_WEEK);

            int weekAddition =7+(weeksMore*7);
//            if(dayOfWeek>currentDayofWeek)
//                weekAddition = 0;
            cal.add(Calendar.DATE, (weekAddition-currentDayofWeek+dayOfWeek));
            SimpleDateFormat df = new SimpleDateFormat("E  MMM d");
            replaceWith = df.format(cal.getTime());


        return replaceWith;
    }

    public static String formatDouble(double amt){
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(amt);
    }

    public static String formatDoubleNoDecimal(double amt){
        DecimalFormat df = new DecimalFormat("#");
        return df.format(amt);
    }

    public static String formatDouble(String amt){
        DecimalFormat df = new DecimalFormat("#.000");
        try { return df.format(Double.parseDouble(amt)); }catch (Exception e){ }
        return "0.0";
    }

    public static float getBatteryLevel(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        return batteryPct*100;
    }

    public static String getImei(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return  telephonyManager.getDeviceId();
    }

    public static String getAppVersion()
    {
        return "0.1";
    }

    public static String getAppVersion(Context context){
        try {

            String versionName =(String) ApplicationRegistry.retrieve(KEY_VERSION);
            if(versionName==null)
                versionName=APP_VERSION;
//            System.out.println("Version Name: "+versionName);
            return versionName;
        }catch (Exception e){


        }
                //getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

        return APP_VERSION;
    }

    public static void setFarmerDetails(Activity container,int parentID,String farmerName, final Farmer farmer, boolean clickable){
        setFarmerDetails(container.getWindow().getDecorView().getRootView(),parentID,farmerName,farmer,clickable);
    }

    public static void setFarmerDetails(final View container,int parentID,String farmerName, final Farmer farmer, boolean clickable){

        LinearLayout ll =  (LinearLayout)container.findViewById(parentID);

        if(!farmerName.isEmpty()){
            String farmerId = farmer.getFarmID();
            ImageView img = (ImageView) container.findViewById(R.id.farmerImg);

            final String fileLoc = ImageUtils.FULL_URL_PROFILE_PIX+"/"+farmer.getFarmID()+".jpg";
            File f =  new File(fileLoc);
            if(f.exists()){
                System.out.println("File Loc"+fileLoc);
                img.setImageDrawable(ImageUtils.getICTCImageAsDrawable(container.getContext(),fileLoc));
                img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            System.out.println("on touch : "+fileLoc);

                                    Intent intent = new Intent(container.getContext(),ImageViewerActivity.class);
                                    intent.putExtra(ImageViewerActivity.IMAGE_TYPE, "ictc");
                            intent.putExtra(ImageViewerActivity.IMAGE_LOC, fileLoc);
                            intent.putExtra(ImageViewerActivity.IMAGE_DETAILS, farmer.getFullname());
                                    container.getContext().startActivity(intent);

                                    return true;


                        }
                        return false;
                    }
                });
                }else{
                img.setImageResource(R.mipmap.ic_person);

            }


            TextView names = (TextView) container.findViewById(R.id.textView_name);
            TextView locations = (TextView) container.findViewById(R.id.textView_location);
            TextView mainCrops = (TextView)container.findViewById(R.id.textView_mainCrop);
            TextView group = (TextView) container.findViewById(R.id.textView_groups);
            ImageView icon = (ImageView) container.findViewById(R.id.imageView_icon);


            String crop = farmer.getMainCrop();
            if (crop.equalsIgnoreCase("Maize")) {
                Drawable drawable = container.getContext().getResources().getDrawable(R.mipmap.ic_maize);
                // icon.setBackground(drawable);
                mainCrops.setText("Maize");
               // mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            } else if (crop.equalsIgnoreCase("Cassava")) {
                Drawable drawable = container.getContext().getResources().getDrawable(R.mipmap.ic_cassava);
                mainCrops.setText("Cassava");
                //mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                //icon.setBackgroundDrawable(drawable);
            } else if (crop.equalsIgnoreCase("Yam")) {
                Drawable drawable = container.getContext().getResources().getDrawable(R.mipmap.ic_yam);
                mainCrops.setText("Yam");
                //mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                // icon.setBackgroundDrawable(drawable);
            } else if (crop.equalsIgnoreCase("Rice")) {
                Drawable drawable = container.getContext().getResources().getDrawable(R.mipmap.ic_rice);
                mainCrops.setText("Rice");
               // mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                // icon.setBackgroundDrawable(drawable);
            }else if (crop.contains("soya")) {
                Drawable drawable = container.getContext().getResources().getDrawable(R.mipmap.ic_rice);
                mainCrops.setText("Soyabean");
                // mainCrops.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                // icon.setBackgroundDrawable(drawable);
            }
            TextView locView  = (TextView)container.findViewById(R.id.textView12);


            if(!farmer.getNickname().isEmpty()){
                names.setText(farmer.getFullname() + " (" + farmer.getNickname() + ")");
            }else
                names.setText(farmer.getFullname());

            if(farmer.getLandArea().isEmpty()){
                locations.setTextColor(container.getResources().getColor(R.color.colorAccent));
                //locView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_location, 0, 0, 0);
            }else{
                locations.setTextColor(container.getResources().getColor(R.color.accent_material_light));
            }
            locations.setText(farmer.getVillage()+", "+farmer.getRegion());
            group.setText(getClusterText(farmer.getCluster()));

            if(clickable) {
                ll.setClickable(clickable);
                ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Clicked : "+farmer.getFullname());
                    processFarmerSelect(v,farmer);
                }
            });
            }
        }else
        {
            ll.setVisibility(View.GONE);
        }
    }
   public static String getClusterText(String c){
       String cluster="";
       switch (c){
           case "1":
               cluster="Experienced farmers";
               break;
           case "2":
               cluster="Moderately experienced farmers";
               break;
           case "3":
               cluster="Farmers on the rise";
               break;
           case "4":
               cluster="Moving from subsistence";
               break;
           case "":
               cluster="No Cluster";
               break;
       }
       return cluster;
   }
    public static void setFarmerDetails(View container,int parentID,String farmerName, final Farmer farmer){
        setFarmerDetails(container,parentID,farmerName,farmer,false);
    }

    public static void  processFarmerSelect(View container,Farmer f){
        Intent t = new Intent(container.getContext(),FarmerDetailActivity.class);
        t.putExtra("farmerId", f.getFarmID());
        container.getContext().startActivity(t);
    }

    public static List<Meeting> sortMeeting(List<Meeting> arr) {
        boolean swapped = true;
        int j = 0;
        Meeting tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.size() - j; i++) {
                if (arr.get(i).getMonthMod() > arr.get(i + 1).getMonthMod()) {
                    tmp = arr.get(i);
                    arr.set(i,arr.get(i + 1));
                    arr.set(i + 1,tmp);
                    swapped = true;
                }
            }
        }
        return arr;
    }
    public static UserDetails getUser(Context c){
        Activity activity = (Activity) c;
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString(c.getResources().getString(R.string.username), "guest");
        String fullname = sharedPref.getString(c.getResources().getString(R.string.fullname),"guest");
        UserDetails user = new UserDetails();
        user.setFullName(fullname);
        user.setUserName(username);
        return  user;
    }

    public static void setUserDetails(Context c , UserDetails u){
        try {
            ApplicationRegistry.setApplicationContext(c);
            ApplicationRegistry.register(GlobalConstants.KEY_CACHED_DEVICE_IMEI,
                    DeviceMetadata.getDeviceImei(c));
            ApplicationRegistry.register(GlobalConstants.KEY_CACHED_APPLICATION_VERSION,
                    c.getResources().getString(R.string.app_name) + "/" + c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName);
            ApplicationRegistry.register(IctcCKwUtil.KEY_FULNAME,u.getFullName() );

            ApplicationRegistry.register(IctcCKwUtil.KEY_VERSION,c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName );
            ApplicationRegistry.register(IctcCKwUtil.KEY_USER_NAME, u.getUserName());
        }catch(Exception e) {

        }
    }

    public static  void setActionbarUserDetails(Context c, View mCustomView){

        boolean showDetails = true;
        String username = (String)ApplicationRegistry.retrieve(IctcCKwUtil.KEY_USER_NAME);

        if(null==username){
            UserDetails u = new DatabaseHelper(c).getUserItem();
            if(u!=null)
            setUserDetails(c,u);
            else
                showDetails=false;
        }

        if(showDetails) {
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.user_fullname);
            UserDetails us = getUser(c);
            mTitleTextView.setText(((String) ApplicationRegistry.retrieve(IctcCKwUtil.KEY_FULNAME)).replace(" lastname", ""));
            mTitleTextView = (TextView) mCustomView.findViewById(R.id.user_username);
            mTitleTextView.setText("[" + username + "]");
        }
    }

    public  static String getUsername(){
        return (String)ApplicationRegistry.retrieve(IctcCKwUtil.KEY_USER_NAME);
    }
    public  static String getFullName(Context c){

       String fullName=(String) ApplicationRegistry.retrieve(IctcCKwUtil.KEY_FULNAME);
            return (String) fullName;
    }

    public static double meterSqdToAcre(double m2){
        double m2toARation=0.000247105;
        return m2toARation*m2;
    }

    public static String cropToCKWLabel(String crop) {
        String ckwCrop = crop;
        if(crop.equalsIgnoreCase("maize"))
            ckwCrop = "b. "+crop+" (ADVANCE)";
        else if(crop.equalsIgnoreCase("rice"))
            ckwCrop = "C. "+crop;
        else if(crop.equalsIgnoreCase("yam"))
            return "D. "+crop;
        else if(crop.equalsIgnoreCase("cassava"))
            return "E. "+crop;


        return ckwCrop;
    }
    public static boolean haveNetworkConnection(Context c) {

        ConnectivityManager conMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();


    }
}
