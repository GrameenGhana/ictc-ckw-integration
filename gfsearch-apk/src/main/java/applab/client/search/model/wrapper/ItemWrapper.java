package applab.client.search.model.wrapper;

/**
 * Created by skwakwa on 10/22/15.
 */
public class ItemWrapper {

    private String key;
    private String value;
    private String secValue;
    private String terValue;
    private boolean showIfEmpty = true;


    public ItemWrapper(String key,String value){
        this.setKey(key);
        this.setValue(value);
        this.secValue="-";
    }

    public ItemWrapper(String key,String value,String sec){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
    }

    public ItemWrapper(String key,String value,String sec,boolean showIfEmpty){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setShowIfEmpty(showIfEmpty);
    }

    public ItemWrapper(String key,String value,String sec,String tertiary){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setTerValue(tertiary);
    }


    public ItemWrapper(String key,String value,String sec,String tertiary,boolean showIfEmpty){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setTerValue(tertiary);
        this.setShowIfEmpty(showIfEmpty);
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

    public String getSecValue() {
        return secValue;
    }

    public void setSecValue(String secValue) {
        this.secValue = secValue;
    }

    public String getTerValue() {
        return terValue;
    }

    public void setTerValue(String terValue) {
        this.terValue = terValue;
    }

    public boolean isShowIfEmpty() {
        return showIfEmpty;
    }

    public void setShowIfEmpty(boolean showIfEmpty) {
        this.showIfEmpty = showIfEmpty;
    }
}
