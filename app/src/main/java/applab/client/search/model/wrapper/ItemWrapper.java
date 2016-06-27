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
private boolean header=false;

    public ItemWrapper(String key,String value,boolean header){
        this.setKey(key);
        this.setValue(value);
        this.secValue="-";
        this.header = header;
    }public ItemWrapper(String key,String value){
        this.setKey(key);
        this.setValue(value);
        this.secValue="-";
        this.header=false;
    }

    public ItemWrapper(String key,String value,String sec){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);

        this.header=false;
    }

    public ItemWrapper(String key,String value,String sec,boolean showIfEmpty){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setShowIfEmpty(showIfEmpty);

        this.header=false;
    }

    public ItemWrapper(String key,String value,String sec,String tertiary){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setTerValue(tertiary);

        this.header=false;
    }


    public ItemWrapper(String key,String value,String sec,String tertiary,boolean showIfEmpty){
        this.setKey(key);
        this.setValue(value);
        this.setSecValue(sec);
        this.setTerValue(tertiary);
        this.setShowIfEmpty(showIfEmpty);

        this.header=false;
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

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
