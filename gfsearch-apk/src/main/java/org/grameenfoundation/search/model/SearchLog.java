package org.grameenfoundation.search.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class SearchLog implements Serializable {
    private Integer id;
    private String menuItemId;
    private Date dateCreated;
    private String clientId;
    private String gpsLocation;
    private String content;

    public SearchLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
