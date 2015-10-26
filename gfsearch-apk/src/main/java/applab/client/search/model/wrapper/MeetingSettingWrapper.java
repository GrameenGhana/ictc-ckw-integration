package applab.client.search.model.wrapper;

import applab.client.search.model.NextMeetingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwakwa on 10/22/15.
 */


public class MeetingSettingWrapper {

    String type;
    String meetingIndex;
    String season;
    String startDate;
    String endDate;
    String crop;

    List<NextMeetingItem> meetingActivities = new ArrayList<NextMeetingItem>();

    public MeetingSettingWrapper() {
    }

    public MeetingSettingWrapper(String type, String meetingIndex, String season, String startDate, String endDate) {
        this.type = type;
        this.meetingIndex = meetingIndex;
        this.season = season;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MeetingSettingWrapper(String crop,String type, String meetingIndex, String season, String startDate, String endDate, List<NextMeetingItem> meetingActivities) {
        this.type = type;
        this.meetingIndex = meetingIndex;
        this.season = season;
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetingActivities = meetingActivities;
        this.crop = crop;
    }

    public MeetingSettingWrapper(String crop,String type, String meetingIndex, String season, String startDate, String endDate, String acts) {
        this.type = type;
        this.meetingIndex = meetingIndex;
        this.season = season;
        this.startDate = startDate;
        this.crop = crop;
        this.endDate = endDate;
        String[] strings = acts.split(",");
        int indx = 1;
        for (String string : strings) {
            NextMeetingItem ma = new NextMeetingItem(indx, string);
            indx++;
            meetingActivities.add(ma);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getCrop() {
        return crop;
    }

    public void setCrop(String type) {
        this.crop = type;
    }
    public String getMeetingIndex() {
        return meetingIndex;
    }

    public void setMeetingIndex(String meeting) {
        this.meetingIndex = meeting;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the meetingActivities
     */
    public List<NextMeetingItem> getMeetingActivities() {
        return meetingActivities;
    }

    /**
     * @param meetingActivities the meetingActivities to set
     */
    public void setMeetingActivities(List<NextMeetingItem> meetingActivities) {
        this.meetingActivities = meetingActivities;
    }

}
