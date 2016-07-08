package applab.client.search.utils;

import applab.client.search.model.MeetingActivity;
import applab.client.search.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
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
            "\nSelect Farmer/Crop and Indicate Assistance Required" ;
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
    public static  String FARMER_PROFILE ="Farmer Profile";
    public static  String COLLECT_FARM_MEIASUREMENT ="Measure ploughed and planted farm area";
    public static  String SHARE_INPUT_PACKAGE ="Input Package";

    public static  String DELIVER_INPUTS ="Deliver Inputs";
    public static  String SELECT_FARMER ="Select Farmer";
    public static  String AGREED_ACTIVITIES_FOR_NEXT_MEETING ="Activities for Next Meeting";
//    public static String COLLECT_FARM_MEASUREMENT="";


    public static List<MeetingActivity> getMeetingActivity(int meetingIndex){
        List<MeetingActivity> activities = new ArrayList<MeetingActivity>();
        MeetingActivity activity=  new MeetingActivity(AGREED_ACTIVITIES_FOR_NEXT_MEETING,"A","A",1,"Agreed Activities for Next Meeting");
        if(1==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Sensitize farmers","I","A",1,"content not available"));
            activities.add(new applab.client.search.model.MeetingActivity("Explain Value Proposition", "I", "A",2,"content not available"));
            activities.add(new applab.client.search.model.MeetingActivity("Explain Processes and Commitment","I","A",4,"content not available"));
            //activities.add(new applab.client.search.model.MeetingActivity("Explain value proposition","I","A",5, replaceRadioTVSchedule()));
        }else if(2==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Enroll and Profile Farmers","I","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Previous Performance(Producton)", "I", "T",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Previous Performance(Producton)", "I", "T",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Previous Performance(Farm Credit)", "I", "T",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Input Package", "I", "A",4,""));
            activities.add(new applab.client.search.model.MeetingActivity("Activities for Next Visit", "I", "A",4,""));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Technical Assistance", "I", "C",3,""));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Key Learning Needs", "I", "C",3,""));
            activities.add(new applab.client.search.model.MeetingActivity("Share Radio and TV Schedules", "I", "A",4,LAUNCH_TAROWORKS));
            // activities.add(new applab.client.search.model.MeetingActivity("Document your farmers’ previous performance (BASELINE)","I","A",1,LAUNCH_TAROWORKS));
            // activities.add(new applab.client.search.model.MeetingActivity("Baseline Data Collection", "I", "T",2,LAUNCH_TAROWORKS+" Farmer Baseline Data"));
        }else if(3==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan(Production)","T","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan(Post-harvest)","T","A",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Previous Performance(Farm Credit)", "I", "T",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Input Package", "I", "A",4,""));
            activities.add(new applab.client.search.model.MeetingActivity("Activities for Next Visit", "I", "A",4,""));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Technical Assistance", "I", "C",3,""));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Key Learning Needs", "I", "C",3,""));
            //activities.add(new applab.client.search.model.MeetingActivity("Land preparation tractor services","T","A",4,"LAND PLOUGH"));
            activities.add(new applab.client.search.model.MeetingActivity("Share  Radio and TV schedules with farmer","I","A",5, replaceRadioTVSchedule()));
            //activities.add(new applab.client.search.model.MeetingActivity("Requests from farmers","I","T",6,LAUNCH_TAROWORKS+"\n"));

        }else if(4==meetingIndex){

            activities.add(new applab.client.search.model.MeetingActivity(COLLECT_FARM_MEIASUREMENT,"I","A",1,"Measurement of Farm"));
            activities.add(new applab.client.search.model.MeetingActivity(DELIVER_INPUTS, "I", "A",2,DELIVER_INPUTS));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of inputs","I","A",3,""));
            activities.add(new applab.client.search.model.MeetingActivity("Update Farm Plan(After Planting)","I","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Activities for Next Visit","I","A",5,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Technical Assistance","I","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Key Learning Needs","I","A",7,"to"));
            activities.add(new applab.client.search.model.MeetingActivity("Share Radio and TV schedules","I","A",8, replaceRadioTVSchedule()));

        }else if(5==meetingIndex){


            activities.add(new applab.client.search.model.MeetingActivity("Crop Assessment","T","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Activities for Next Visit","I","A",6,""));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Technical Assistance", "I", "C",2,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Key Learning Needs","I","A",7,"to"));
            activities.add(new applab.client.search.model.MeetingActivity("Share Radio and TV schedules","I","A",3,replaceRadioTVSchedule()));
        }else if(6==meetingIndex){

            activities.add(new applab.client.search.model.MeetingActivity("Update Farm Plan(After harvest)","I","A",1,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Activities for Next Visit", "I", "A",2,"DISCCUSSION"));
            // activities.add(new applab.client.search.model.MeetingActivity("Provide farmer with upcoming activities on farm plan","I","A",3,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Technical Assistance","I","A",4,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Key Learning Needs","I","A",7,"to"));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV Schedule","I","A",3,"Collect Farmer Feedback"));
            // activities.add(new applab.client.search.model.MeetingActivity("Collect request for assistance","I","A",4,"Collect Agent Feedback"));
        }


        else if(7==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Update Farm Plan(Post-harvest)","G","T",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Update Farm Plan(After Selling)","G","T",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Update Farm Plan(Farm Credit)","G","T",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Reconcile Credit and Repayments","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide Farmers with Overall Results", "G", "C",2,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Feedback","G","T",3,LAUNCH_TAROWORKS));
            // activities.add(new applab.client.search.model.MeetingActivity("Collect agent feedback", "G", "T",2,LAUNCH_TAROWORKS));

        }else if(8==meetingIndex){


            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Initial","G","A",1,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide input package", "G", "A",2,SHARE_INPUT_PACKAGE));
            activities.add(new applab.client.search.model.MeetingActivity("Radio program on seed","G","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Video on selecting varieties, use of good quality seed, land preparation, planting","G","C",4,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide input package","G","A",3,SHARE_INPUT_PACKAGE));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of input credit package","G","A",4,"Confirm Delivery"));
            activities.add(new applab.client.search.model.MeetingActivity("PLAY THE GAME","G","A",3,"Play a Game",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect feedback on session","G","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Final","G","A",4,ATTENDANCE));
        }


        else if(9==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Initial","G","A",1,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide input package", "I", "A",2,"Collect Payments"));
            activities.add(new applab.client.search.model.MeetingActivity("Radio program on fertilizer","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Video on fertilizer application","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Provide input package","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of input credit package","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("PLAY THE GAME","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect feedback on session","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Final","-","A",4,ATTENDANCE));
        }else if(10==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Initial","G","A",1,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Radio program on fertilizer","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Video on fertilizer application and soil fertility maintenance","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of input credit package","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("PLAY THE GAME","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect feedback on session","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Final","-","A",4,ATTENDANCE));
        }
        else if(11==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Initial","G","A",1,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss Food Security","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Video on harvest and post harvest","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of input credit package","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("PLAY THE GAME","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect feedback on session","-","A",4,"Collect Agent Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE+" Final","-","A",4,ATTENDANCE));


        }
        activities.add(activity);
        return  activities;
    }
    public static String replaceRadioTVSchedule(){
        return TV_PROGRAM.replaceAll("GTV_DATE",IctcCKwUtil.getNextDate(Calendar.SUNDAY)).replaceAll("VOLTA_RADIO_DATE",IctcCKwUtil.getNextDate(Calendar.SATURDAY));
    }
    public  static int getMeetingPosition(int index,String type){
        if(type.equalsIgnoreCase("Group")){
            if(index==1)
                return 1;
            else if(index==2) return 3;
            else if(index>4) return index;
            else return index+2;
        }else if(type.equalsIgnoreCase("Individual")){
            if(index==1) return 2; else return 4;
        }
        return 1;
    }
    public static  String getMeetingTitle(int index, String meeting_type)
    {
        return getMeetingTitles(meeting_type)[index];

    }
    public static MeetingActivity getMeetingDetails(int index,String meeting_type)
    {
        if(index==1)
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Group",1);
        else if(index==2)
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Individual",1);
        else if(index==3)
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Group",2);
        else if(index==4)
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Individual",2);
        else if(index==5)
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Group",3);
        else
            return new MeetingActivity(getMeetingTitle(index,meeting_type),"Group",4);

    }

    public static String  [] getMeetingTitles(String meeting_type) {
        return getMeetingTitles(false, meeting_type);
    }

    public static String  [] getMeetingTitles(boolean withFirst, String meeting_type){

        //PRE-VISIT	VISIT 1	VISIT 2	VISIT 3	VISIT 4	VIS
        // IT 5		VISIT 6
        String [] titles = new String[0];
        if(meeting_type.equalsIgnoreCase("group")){
            titles = new String []{
                    "",
                    "MULTIMEDIA MEETING  1 (Preparing To Plant)",
                    "MULTIMEDIA MEETING  2 (Fertilizer Application)",
                    "MULTIMEDIA MEETING  3 (Fertilizer Application)",
                    "MULTIMEDIA MEETING  4 (Harvest And Post Harvest)",};
        }else if (meeting_type.equalsIgnoreCase("individual")) {
             titles = new String[]{
                    "",
                    "PRE-VISIT",
                    "VISIT 1 (Registration, Profiling,etc)",
                    "VISIT 2 (Farm Planning)",
                    "VISIT 3 (Field Measurement, Plan Update 1)",
                    "VISIT 4 (Field Assessment)",
                    "VISIT 5 (Fertilizer Application)",
                    "VISIT 6 (Farm Plan Update 3, 4 5) ",};
        }
        //MULTIMEDIA MEETING 1
        if(withFirst){
            titles = Arrays.copyOfRange(titles,1,titles.length);
        }
        return titles;
    }

    public static void setMeetingSettings(DatabaseHelper helper){
        helper.meetingSettingCreation();
        helper.saveMeetingSetting("Maize", "Group", "1", "1", "02", "02", "Land Clearing,Land preparation (ploughing)");
        helper.saveMeetingSetting("Maize", "Individual", "1", "1", "03", "03", "Planting,First weed control (if no herbicide applied),Basal Fertilizer application");
        helper.saveMeetingSetting("Maize", "Group", "2", "2", "05", "05", "First/Second Weeding,Top-dress fertilizer application");
        helper.saveMeetingSetting("Maize", "Individual", "2", "1", "06", "06", "Harvesting ");
        helper.saveMeetingSetting("Maize", "Group", "3", "3", "07", "07", "Post-harvest processing");
        helper.saveMeetingSetting("Maize", "Group", "4", "4", "09", "09", "");
        helper.saveMeetingSetting("Cassava", "Group", "1", "1", "02-10", "02-01", "Land Clearing,Land preparation (ploughing)");
        helper.saveMeetingSetting("Cassava", "Individual", "1", "1", "02-10", "02-01", "Planting,First weed control (if no herbicide applied),Basal Fertilizer application");
        helper.saveMeetingSetting("Cassava", "Group", "2", "2", "02-10", "02-01", "First/Second Weeding,Third Weeding");
        helper.saveMeetingSetting("Cassava", "Individual", "2", "1", "02-10", "02-01", "Harvesting ");
        helper.saveMeetingSetting("Cassava", "Group", "3", "3", "02-10", "02-01", "Post-harvest processing");
        helper.saveMeetingSetting("Cassava", "Group", "4", "4", "02-10", "02-01", "");
        helper.saveMeetingSetting("Rice", "Group", "1", "1", "02-10", "02-01", "Land Clearing,Land preparation (ploughing)");
        helper.saveMeetingSetting("Rice", "Individual", "1", "1", "02-10", "02-01", "Planting,First weed control (if no herbicide applied),Basal Fertilizer application");
        helper.saveMeetingSetting("Rice", "Group", "2", "2", "02-10", "02-01", " First/Second Weeding,Top-dress fertilizer application");
        helper.saveMeetingSetting("Rice", "Individual", "2", "1", "02-10", "02-01", "Harvesting");
        helper.saveMeetingSetting("Rice", "Group", "3", "3", "02-10", "02-01", "Post-harvest processing");
        helper.saveMeetingSetting("Rice", "Group", "4", "4", "02-10", "02-01", "");
        helper.saveMeetingSetting("Yam", "Group", "1", "1", "02-10", "02-01", "Land Clearing,Land preparation (ploughing)");
        helper.saveMeetingSetting("Yam", "Individual", "1", "1", "02-10", "02-01", "Planting,First weed control (if no herbicide applied),Basal Fertilizer application");
        helper.saveMeetingSetting("Yam", "Group", "2", "2", "02-10", "02-01", "First/Second Weeding");
        helper.saveMeetingSetting("Yam", "Individual", "2", "1", "02-10", "02-01", "Second/Third weeding,Harvesting");
        helper.saveMeetingSetting("Yam", "Group", "3", "3", "02-10", "02-01", "Third/fourth weeding,Second HarvestingPost-harvest processing");
        helper.saveMeetingSetting("Yam", "Group", "4", "4", "02-10", "02-01", "");
    }
}