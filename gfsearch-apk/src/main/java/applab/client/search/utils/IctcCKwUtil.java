package applab.client.search.utils;

import android.content.Context;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by skwakwa on 9/10/15.
 */
public class IctcCKwUtil {

    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");


    public static Date formatDateTime(String timeToFormat) {

        return formatDateTime(timeToFormat,"yyyyMMddHHmmss");

    }
    public static Date formatSlashDates(String timeToFormat) {
        return formatDateTime(timeToFormat,"dd/MM/yyyy");
    }


    public static Date formatDateTime(String timeToFormat,String format) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                format);

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

        }
        return date;
    }

    public static String formatStringDateTime(Date timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyyMMddHHmmss");

        String date = "";
        if (timeToFormat != null) {
            try {
                date = iso8601Format.format(timeToFormat);
            } catch (Exception e) {
                date = "";
            }

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



    public static String formatDouble(double amt){

        DecimalFormat df = new DecimalFormat("#.000");

        return df.format(amt);
    } public static String formatDouble(String amt){

        DecimalFormat df = new DecimalFormat("#.000");

        try {
            return df.format(Double.parseDouble(amt));

        }catch (Exception e){


        }

        return "0.0";
        }
}
