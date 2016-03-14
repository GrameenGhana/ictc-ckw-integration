package applab.client.search.task;

import applab.client.search.model.Payload;

/**
 * Created by skwakwa on 10/15/15.
 */
public interface VideoDownloadRequestListener {
    void apiRequestComplete(Payload response);
}