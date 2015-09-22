package applab.client.search.utils;

import applab.client.search.model.MeetingActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class  AgentVisitUtil {

    public static String TV_PROGRAM="Press OK to view the schedule of radio and TV programmes nationwide";
    String tv=   "GTV\nGTV_DATE\n" +
            "3:00pm - 3:30pm\n" +
            "Radio \n" +
            "Volta Region (Twi-Ewe)" +
            "\nVOLTA_RADIO_DATE\n " +
            "7pm-9pm\n" +
            "Brong Ahafo 7 to 8 \n" +
            "Volta - Twi, Ewe (Volta Star Radio) \n" +
            "Brong Ahafo Radio B in Twi" +
            "\nMore Information Call: 057665186";
    public static String LAUNCH_CKW="" +
            "LAUNCH CKW" +
            "\nSelect Farmer and Indicate Assistance Required" ;
    public static String LAUNCH_TAROWORKS="" +
            "Launch Tarowork \n" +
            "Select Job \n" ;

    public static String ATTENDANCE="" +
            "" +
            "\n Select farmers who attended this meeting. \nSet the meeting date, \nas well as the start \nand end times for this meeting. " ;
    public static String MEETING_QUESITONS="" +
            "CLICK ON Meetings" +
            "\nAdd Meetings and take Attendance " ;

    public static  String TAKE_ATTENDANCE ="Take Attendance";
    public static  String COLLECT_FARM_MEASUREMENT ="Collect farm Measurement Data";
    public static  String SHARE_INPUT_PACKAGE ="Input Package";

    public static  String DELIVER_INPUTS ="Deliver Inputs";
    public static  String SELECT_FARMER ="Select Farmer";
    public static List<MeetingActivity> getMeetingActivity(int meetingIndex){
        List<MeetingActivity> activities = new ArrayList<MeetingActivity>();
        if(1==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Get Ploughing Dates for Tractor","G","A",1,"",false));
            activities.add(new applab.client.search.model.MeetingActivity("Farmer Registration", "I", "T",2,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
//            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Data","I","T",3,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
            activities.add(new applab.client.search.model.MeetingActivity(SHARE_INPUT_PACKAGE,"-","A",4,SELECT_FARMER+"\nShare Inputs"));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","A","A",5, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","T",6,LAUNCH_TAROWORKS+"\nTechnical Needs Survey"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",7,MEETING_QUESITONS,false));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",8,ATTENDANCE));
        }else if(2==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity(COLLECT_FARM_MEASUREMENT,"G","A",1,"Select a farmer, and measure his farm according to the instructions provided. "));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline Data Collection", "I", "T",2,LAUNCH_TAROWORKS+"3. Farmer Baseline Data"));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Management Planning (Production)","I","T",3,LAUNCH_TAROWORKS+"5. FMP PRODUCTION NEW"));
            activities.add(new applab.client.search.model.MeetingActivity(DELIVER_INPUTS,"-","A",4,SELECT_FARMER));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Delivery of Inputs/Receipt","A","A",5,"",false));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","T","A",7,LAUNCH_TAROWORKS+"\nTechnical Needs Survey",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",8,MEETING_QUESITONS,false));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",9,replaceRadioTVSchedule()));
        }else if(3==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Delivery of Inputs/Receipt","A","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activities Agreed on Previous visit", "I", "A",2,"to decided",false));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Play A Game","-","A",4,"Game Coming Soon",false));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",5, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for ASSISTANCE","G","T",6,LAUNCH_TAROWORKS+"\nTechnical Needs Survey",true));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",7,MEETING_QUESITONS,false));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",8,ATTENDANCE));
        }else if(4==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Farm Management Planning (Post Harvest)","G","T",1,LAUNCH_TAROWORKS+" 5. FMP POST HARVEST NEW"));
            activities.add(new applab.client.search.model.MeetingActivity("Crop Inspection and update of Crop Status", "I", "T",2,LAUNCH_TAROWORKS+"5. Farm Management Plan",false));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline on Post Harvest Management","I","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Post harvest management Planning","-","T",5,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance on Post harvest Management","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Agree Activities to be completed by Next Level","G","A",7,"to",false));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",8, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",9,MEETING_QUESITONS,false));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",10,ATTENDANCE));

        }else if(5==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activity Agreed on Preview Visit","G","A",1,"",false));

            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance on Post Harvest","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss Food Security", "I", "C",2,LAUNCH_CKW,false));
            activities.add(new applab.client.search.model.MeetingActivity("Play Game","I","A",3,"Coming Soon",false));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan Update","-","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Agree Activities to be completed on next visit","A","A",5,"Things agreed on",false));

            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","G","A",9, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","C",7,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",8,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Questions","G","A",8,MEETING_QUESITONS,false));
        }else if(6==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Provide Results","G","A",1,"Results",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Payment", "I", "A",2,"Collect Payments",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Feedback","I","A",3,"Collect Farmer Feedback",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Agent Feedback","-","A",4,"Collect Agent Feedback",false));
        }
        return  activities;
    }


    public static String replaceRadioTVSchedule(){
  return TV_PROGRAM.replaceAll("GTV_DATE",IctcCKwUtil.getNextDate(Calendar.SUNDAY)).replaceAll("VOLTA_RADIO_DATE",IctcCKwUtil.getNextDate(Calendar.SATURDAY));
    }





}
