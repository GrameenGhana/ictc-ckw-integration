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

    /**
    public static List<MeetingActivity> getMeetingActivity(int meetingIndex){
        List<MeetingActivity> activities = new ArrayList<MeetingActivity>();
        MeetingActivity activity=  new MeetingActivity(AGREED_ACTIVITIES_FOR_NEXT_MEETING,"A","A",1,"Agreed Activities for Next Meeting");
        if(1==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Get Ploughing Dates for Tractor","G","A",1,"",false));
            activities.add(new applab.client.search.model.MeetingActivity("Farmer Registration", "I", "T",2,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
//            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Data","I","T",3,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));
            activities.add(new applab.client.search.model.MeetingActivity(SHARE_INPUT_PACKAGE,"-","A",4,SELECT_FARMER+"\nShare Inputs"));
            activities.add(new applab.client.search.model.MeetingActivity("Share Media Schedule","A","A",5, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","T",6,LAUNCH_TAROWORKS+"\nTechnical Needs Survey",false)) ;
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
            activities.add(new applab.client.search.model.MeetingActivity("Share Media Schedule","G","A",9,replaceRadioTVSchedule()));
        }else if(3==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Delivery of Inputs/Receipt","A","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activities Agreed on Previous visit", "I", "A",2,"to decided",false));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance","G","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Play A Game","-","A",4,"Game Coming Soon",false));
            activities.add(new applab.client.search.model.MeetingActivity("Share Media Schedule","G","A",5, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for ASSISTANCE","G","T",6,LAUNCH_TAROWORKS+"\nTechnical Needs Survey",false));
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
            activities.add(new applab.client.search.model.MeetingActivity("Share Media Schedule","G","A",8, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Question","G","A",9,MEETING_QUESITONS,false));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",10,ATTENDANCE));
        }else if(5==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Confirm Activity Agreed on Preview Visit","G","A",1,"",false));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical Assistance on Post Harvest","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss Food Security", "I", "C",2,LAUNCH_CKW,false));
            activities.add(new applab.client.search.model.MeetingActivity("Play Game","I","A",3,"Coming Soon",false));
            activities.add(new applab.client.search.model.MeetingActivity("Farm Plan Update","-","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Agree Activities to be completed on next visit","A","A",5,"Things agreed on",false));
            activities.add(new applab.client.search.model.MeetingActivity("Share Media Schedule","G","A",9, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Request for Assistance","G","C",7,LAUNCH_CKW,false));
            activities.add(new applab.client.search.model.MeetingActivity(TAKE_ATTENDANCE,"G","A",8,ATTENDANCE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Meeting Questions","G","A",8,MEETING_QUESITONS,false));
        }else if(6==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Provide Results","G","A",1,"Results",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Payment", "I", "A",2,"Collect Payments",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Farmer Feedback","I","A",3,"Collect Farmer Feedback",false));
            activities.add(new applab.client.search.model.MeetingActivity("Collect Agent Feedback","-","A",4,"Collect Agent Feedback",false));
        }
        activities.add(activity);
        return  activities;
    }
**/


    public static List<MeetingActivity> getMeetingActivity(int meetingIndex){
        List<MeetingActivity> activities = new ArrayList<MeetingActivity>();
        MeetingActivity activity=  new MeetingActivity(AGREED_ACTIVITIES_FOR_NEXT_MEETING,"A","A",1,"Agreed Activities for Next Meeting");
        if(1==meetingIndex){


            activities.add(new applab.client.search.model.MeetingActivity("Sensitize farmers","I","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Explain", "I", "A",2,LAUNCH_TAROWORKS+"2. FARMER REGISTRATION"));

            activities.add(new applab.client.search.model.MeetingActivity("Explain processes and commitment","I","A",4,SELECT_FARMER+"\nShare Inputs"));
            activities.add(new applab.client.search.model.MeetingActivity("Explain value proposition","I","A",5, replaceRadioTVSchedule()));
        }else if(2==meetingIndex){
            activities.add(new applab.client.search.model.MeetingActivity("Enroll and profile farmers","I","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline Data Collection", "I", "T",2,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Document your farmers’ previous performance (BASELINE)","I","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Baseline Data Collection", "I", "T",2,LAUNCH_TAROWORKS+" Farmer Baseline Data"));
        }else if(3==meetingIndex){

            activities.add(new applab.client.search.model.MeetingActivity("Support farmer to develop a farm plan","T","A",1,LAUNCH_TAROWORKS));

            activities.add(new applab.client.search.model.MeetingActivity("Provide farmer with upcoming activities on farm plan","I","C",3,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical advice on upcoming farm plan operations","I","C",3,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Land preparation tractor services","T","A",4,"LAND PLOUGH"));
            activities.add(new applab.client.search.model.MeetingActivity("Share  Radio and TV schedules with farmer","I","A",5, replaceRadioTVSchedule()));
            activities.add(new applab.client.search.model.MeetingActivity("Requests from farmers","I","T",6,LAUNCH_TAROWORKS+"\n"));

        }else if(4==meetingIndex){

            activities.add(new applab.client.search.model.MeetingActivity(COLLECT_FARM_MEIASUREMENT,"I","A",1,"Measurement of Farm"));
            activities.add(new applab.client.search.model.MeetingActivity(DELIVER_INPUTS, "I", "A",2,DELIVER_INPUTS));
            activities.add(new applab.client.search.model.MeetingActivity("Confirm delivery of inputs","I","A",3,""));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss operations undertaken and update farmer records","I","T",4,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide farmer with upcoming activities on farm plan","I","A",5,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical advice on upcoming farm plan operations","I","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Collect request for assistance","I","A",7,"to"));
            activities.add(new applab.client.search.model.MeetingActivity("Share Radio and TV schedules","I","A",8, replaceRadioTVSchedule()));

        }else if(5==meetingIndex){


            activities.add(new applab.client.search.model.MeetingActivity("Crop inspection and update of crop status","T","A",1,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Provide farmer with upcoming activities on farm plan","I","C",6,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical advice on upcoming farm plan operations", "I", "C",2,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Share Radio and TV schedules","I","A",3,replaceRadioTVSchedule()));
        }else if(6==meetingIndex){

            activities.add(new applab.client.search.model.MeetingActivity("Discuss operations undertaken and update farmer records","I","A",1,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Discuss food security", "I", "A",2,"DISCCUSSION"));
            activities.add(new applab.client.search.model.MeetingActivity("Provide farmer with upcoming activities on farm plan","I","A",3,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Provide technical advice on upcoming farm operations","I","A",4,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Share TV schedule","I","A",3,"Collect Farmer Feedback"));
            activities.add(new applab.client.search.model.MeetingActivity("Collect request for assistance","I","A",4,"Collect Agent Feedback"));
        }


        else if(7==meetingIndex){



            activities.add(new applab.client.search.model.MeetingActivity("Discuss operations undertaken and update farmer records","G","A",1,""));
            activities.add(new applab.client.search.model.MeetingActivity("Reconcile credit and repayments","G","C",6,LAUNCH_CKW));
            activities.add(new applab.client.search.model.MeetingActivity("Provide farmers with overall results", "G", "C",2,FARMER_PROFILE));
            activities.add(new applab.client.search.model.MeetingActivity("Collect farmer feedback","G","T",3,LAUNCH_TAROWORKS));
            activities.add(new applab.client.search.model.MeetingActivity("Collect agent feedback", "G", "T",2,LAUNCH_TAROWORKS));

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


    public static  String getMeetingTitle(int index)
    {
        return getMeetingTitles()[index];

    }


    public static MeetingActivity getMeetingDetails(int index)
    {
        if(index==1)
            return new MeetingActivity(getMeetingTitle(index),"Group",1);
       else if(index==2)
            return new MeetingActivity(getMeetingTitle(index),"Individual",1);
        else if(index==3)
            return new MeetingActivity(getMeetingTitle(index),"Group",2);
        else if(index==4)
            return new MeetingActivity(getMeetingTitle(index),"Individual",2);
        else if(index==5)
            return new MeetingActivity(getMeetingTitle(index),"Group",3);
        else
            return new MeetingActivity(getMeetingTitle(index),"Group",4);

    }

    public static String  [] getMeetingTitles() {
    return getMeetingTitles(false);
    }

    public static String  [] getMeetingTitles(boolean withFirst){

        //PRE-VISIT	VISIT 1	VISIT 2	VISIT 3	VISIT 4	VIS
        // IT 5		VISIT 6

        String []   titles = {
                "",
                "PRE-VISIT",
                "VISIT 1 (Registration, Profiling,etc)",
                "VISIT 2 (Farm Planning)",
                "VISIT 3 (Field Measurement, Plan Update 1)",
                "VISIT 4 (Field Assessment)",
                "VISIT 5 (Fertilizer Application)",
                "VISIT 6 (Farm Plan Update 3, 4 5) ",
                "MULTIMEDIA MEETING  1 (Preparing To Plant)",
                "MULTIMEDIA MEETING  2 (Fertilizer Application)",
                "MULTIMEDIA MEETING  3 (Fertilizer Application)",
                "MULTIMEDIA MEETING  4 (Harvest And Post Harvest)",};
        //MULTIMEDIA MEETING 1
        if(withFirst){
            titles = Arrays.copyOfRange(titles,1,titles.length);
        }


//        final String []   titles = {
//                "",
//                "Initial Group Meeting",
//                "1st Individual Meeting",
//                "2nd Group Meeting",
//                "2nd Individual Meeting",
//                "3rd Group Meeting",
//                "4th Group Meeting"};
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
