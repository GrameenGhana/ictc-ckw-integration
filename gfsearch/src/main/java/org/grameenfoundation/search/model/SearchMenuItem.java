package org.grameenfoundation.search.model;

/**
 * Represents a menu
 *
 * @author Charles Tumwebaze
 */
public class SearchMenuItem {
    private String id;
    private String label;

    /**
     * gets the id of this menu
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * sets the id of this search menu
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gets the label of this menu
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label of the search menu.
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
