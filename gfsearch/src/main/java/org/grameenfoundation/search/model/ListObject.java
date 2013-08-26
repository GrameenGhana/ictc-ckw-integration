package org.grameenfoundation.search.model;

/**
 * an object that is tagged as a list item tag.
 */
public class ListObject {
    private String id;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
