package applab.client.search.task;

import applab.client.search.model.Payload;

public interface DownloadFileRequestListener {
    void VideoDownloadRequestComplete(Payload response);
}