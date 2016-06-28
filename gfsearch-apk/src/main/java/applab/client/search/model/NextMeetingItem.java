package applab.client.search.model;

/**
 * Created by skwakwa on 10/22/15.
 */

    public class NextMeetingItem {

        private int index;
        private String name;
    private String crop;
    private String itemIndex;
    private String type;

        public NextMeetingItem() {
        }


    public NextMeetingItem(int index, String name) {
        this.index = index;
        this.name = name;
    }

        public NextMeetingItem(int index, String name,String crop,String itemIndex,String type) {
            this.index = index;
            this.name = name;
            this.crop = crop;
            this.itemIndex = itemIndex;
            this.type = type;
        }

        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }

        /**
         * @param index the index to set
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }


    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}