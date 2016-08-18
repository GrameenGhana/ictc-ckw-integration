package applab.client.search.model;

import applab.client.search.utils.AgentVisitUtil;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by skwakwa on 8/30/15.
 */
public class Meeting {
    private String id;
    private String type;//G/I
    private String title;
    private Date scheduledDate;
    private Date meetingDate;
    private int attended;
    private int meetingIndex;
    private String farmer;
    private String remark;
    private String crop;
    private String season;
    private String scheduledMeetingDate;

    private int month;
    int monthMod;

    private Farmer  farmerDetails;
    public Meeting(){

    }

    public Meeting(String id, String type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer, String remark,String crop,String season) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.scheduledDate = scheduledDate;
        this.meetingDate = meetingDate;
        this.attended = attended;
        this.meetingIndex = meetingIndex;
        this.farmer = farmer;
        this.remark = remark;
        this.crop = crop;
        this.setSeason(season);
        Calendar cal=  Calendar.getInstance();
        int cmonth=  cal.get(Calendar.MONTH)+1;
        cal.setTime(scheduledDate);
        this.setMonth(cal.get(Calendar.MONTH)+1);
        System.out.println("mMonth : "+this.month+" <-> "+cmonth);
        if(this.month>=cmonth)
            monthMod = getMonth() % cmonth;
        else
           this.setMonthMod(12-cmonth+month);
        System.out.println("mMonth :"+this.getMonthMod());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getMeetingIndex() {
        return meetingIndex;
    }

    public void setMeetingIndex(int meetingIndex) {
        this.meetingIndex = meetingIndex;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Farmer getFarmerDetails() {
        return farmerDetails;
    }

    public void setFarmerDetails(Farmer farmerDetails) {
        this.farmerDetails = farmerDetails;
    }

    public int getMeetingPosition(){
        return AgentVisitUtil.getMeetingPosition(meetingIndex,type);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonthMod() {
        return monthMod;
    }

    public void setMonthMod(int monthMod) {
        this.monthMod = monthMod;
    }

    public String getScheduledMeetingDate() {
        return scheduledMeetingDate;
    }

    public void setScheduledMeetingDate(String scheduledMeetingDate) {
        this.scheduledMeetingDate = scheduledMeetingDate;
    }
}