package applab.client.search.model.wrapper;

import java.util.List;

public class VideosResponseWrapper {
    private String resultCode;
    private String resultMassage;
    private List<VideoData> videoResults;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMassage() {
        return resultMassage;
    }

    public void setResultMassage(String resultMassage) {
        this.resultMassage = resultMassage;
    }

    public List<VideoData> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoData> videoResults) {
        this.videoResults = videoResults;
    }
}
