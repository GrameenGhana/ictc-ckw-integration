package applab.client.search.model.wrapper;

/**
 * Created by skwakwa on 10/22/15.
 */
public class ItemWrapper {

    private String key;
    private String value;


     public ItemWrapper(String key,String value){

        this.setKey(key);
        this.setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
