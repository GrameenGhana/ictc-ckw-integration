package applab.client.search.task;

import applab.client.search.model.Payload;

public interface APIRequestListener {
    void apiRequestComplete(Payload response);
}