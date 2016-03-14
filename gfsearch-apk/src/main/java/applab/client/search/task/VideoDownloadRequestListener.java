package applab.client.search.task;

import applab.client.search.model.Payload;

public interface VideoDownloadRequestListener {
    void VideoDownloadRequestComplete(Payload response);
}