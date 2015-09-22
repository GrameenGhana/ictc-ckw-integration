package applab.client.search.model;

import applab.client.search.utils.IctcCKwUtil;
import org.joda.time.DateTime;

/**
 * Created by skwakwa on 9/22/15.
 */
public class TrackerLog {

    private long id;
    private String content;
    private DateTime datetime;
    private boolean submitted;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public DateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(DateTime datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean isSubmitted() {
        return submitted;
    }
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public String getDateTimeString() {
        return IctcCKwUtil.DATETIME_FORMAT.print(datetime);
    }
}
