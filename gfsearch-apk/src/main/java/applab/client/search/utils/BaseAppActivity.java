package applab.client.search.utils;

import android.content.Context;
import applab.client.search.storage.DatabaseHelper;
import org.json.JSONObject;

/**
 * Created by skwakwa on 2/8/16.
 */
public class BaseAppActivity {
    private Context context;
    private long startTime;
    private String module;
    private String data;
    private String imei;
    private String battery;
    private String version;
    private String section;

    private String page="None";
    DatabaseHelper hp;
    boolean saved = false;


   public BaseAppActivity(Context context){
        this.context = context;
       this.startTime = System.currentTimeMillis();
    }

    public void setDetails(DatabaseHelper dh, String module, String page){
        setItemValues(dh,module,page,"","");
    }

    public void setDetails(DatabaseHelper dh, String module, String data,String section){
        setItemValues(dh,module, getPage(),section,data);

    }
    public void setDetails(DatabaseHelper dh, String module, String page,String section,String data){
        setItemValues(dh,module,page,section,data);
    }

    public void setItemValues(DatabaseHelper dh, String module, String page,String section,String data){
        this.hp =dh;
        this.setModule(module);
        this.setData(data);
        this.setOtherValues();
        this.setSection(section);
        this.setPage(page);
    }

    public void setOtherValues(){
        setBattery(String.valueOf(IctcCKwUtil.getBatteryLevel(getContext())));
        setVersion(IctcCKwUtil.getAppVersion(getContext()));
        setImei(IctcCKwUtil.getImei(getContext()));
    }


    public void save(){
if(!saved) {
    JSONObject obj = null;

    try {
        if (getData().isEmpty())
            obj = new JSONObject();
        else
            obj = new JSONObject(getData());

        obj.put("page", getPage());
        obj.put("section", getSection());
        obj.put("battery", (getBattery()));
        obj.put("version", getVersion());
        obj.put("imei", getImei());
    } catch (Exception e) {

    }

    System.out.println("About to save saveBD " + getPage());
    hp.insertCCHLog(getModule(), obj.toString(), getStartTime(), System.currentTimeMillis());
    saved = true;
}
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
