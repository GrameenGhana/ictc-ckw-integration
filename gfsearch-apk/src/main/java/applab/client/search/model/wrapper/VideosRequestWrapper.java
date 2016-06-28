package applab.client.search.model.wrapper;

import java.util.List;

public class VideosRequestWrapper {
    private String request;
    private String imei;
    private List<String> videoIds;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<String> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<String> videoIds) {
        this.videoIds = videoIds;
    }
}


