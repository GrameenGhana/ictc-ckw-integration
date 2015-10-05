package applab.client.search.model.wrapper;

/**
 * Created by skwakwa on 10/2/15.
 */
public class CommunicationSchedule {
    private int icon;
    private String title;
    private String nextDate;
    private String time;
    private String language;

    public CommunicationSchedule(int icon,String title,String nextDate,String time,String language){
        this.icon= icon;
        this.title=title;
        this.nextDate=nextDate;
        this.time=time;
        this.language = language;

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
