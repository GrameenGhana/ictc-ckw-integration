package applab.client.search.synchronization;

import applab.client.search.model.Payload;

/**
 * Created by skwakwa on 8/4/15.
 */
public interface SubmitListener {
    void submitComplete(Payload response);

}
