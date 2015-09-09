package applab.client.search.model;

/**
 * Created by skwakwa on 9/2/15.
 */
public class MeetingActivity {
    private String activityName;
    private int meetingIndex;
private String type;
    private String applicationToHandle;
    private String description;

    public MeetingActivity(String activityName,String type,String applicationToHandle,int meetingIndex){

        this.setActivityName(activityName);
        this.setType(type);
        this.setApplicationToHandle(applicationToHandle);
        this.setMeetingIndex(meetingIndex);
    }public MeetingActivity(String activityName,String type,String applicationToHandle,int meetingIndex,String desc){

        this.setActivityName(activityName);
        this.setType(type);
        this.setApplicationToHandle(applicationToHandle);
        this.setMeetingIndex(meetingIndex);
        this.setDescription(desc);
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getMeetingIndex() {
        return meetingIndex;
    }

    public void setMeetingIndex(int meetingIndex) {
        this.meetingIndex = meetingIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicationToHandle() {
        return applicationToHandle;
    }

    public void setApplicationToHandle(String applicationToHandle) {
        this.applicationToHandle = applicationToHandle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
