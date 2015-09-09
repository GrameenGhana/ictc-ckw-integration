package applab.client.search.model;

/**
 * Created by skwakwa on 8/30/15.
 */
public class MeetingProcedure {
    private int id;
    private int meetingIndex;
    private String type;
    private String title;
    private String crop;
    private String procedure;
    private int season;



    public MeetingProcedure(int id, int meetingIndex, String type, String title, String crop, String procedure, int season) {
        this.id = id;
        this.meetingIndex = meetingIndex;
        this.type = type;
        this.title = title;
        this.crop = crop;
        this.procedure = procedure;
        this.season = season;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
