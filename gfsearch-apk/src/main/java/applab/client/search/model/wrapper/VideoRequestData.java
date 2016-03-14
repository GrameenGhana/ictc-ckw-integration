package applab.client.search.model.wrapper;

import java.util.List;

public class VideoRequestData {
    private String videosVersion;
    private List<String> videoIds;

    public VideoRequestData() {}

    public VideoRequestData(List<String> videoIds, String version)
    {
        this.videoIds = videoIds;
        this.videosVersion = version;
    }

    public String getVideosVersion() {
        return videosVersion;
    }

    public void setVideosVersion(String version) {
        this.videosVersion = version;
    }

    public List<String> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<String> videoIds) {
        this.videoIds = videoIds;
    }
}
