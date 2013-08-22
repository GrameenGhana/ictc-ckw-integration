package org.grameenfoundation.search.synchronization;

import android.content.Context;
import android.util.Log;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.grameenfoundation.search.ApplicationRegistry;
import org.grameenfoundation.search.R;
import org.grameenfoundation.search.model.SearchMenu;
import org.grameenfoundation.search.model.SearchMenuItem;
import org.grameenfoundation.search.services.MenuItemService;
import org.grameenfoundation.search.settings.SettingsConstants;
import org.grameenfoundation.search.settings.SettingsManager;
import org.grameenfoundation.search.utils.HttpHelpers;
import org.grameenfoundation.search.utils.JsonSimpleBaseParser;
import org.grameenfoundation.search.utils.XmlEntityBuilder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Facade that handles synchronization of search menus and menu items.
 * It abstracts the underlying synchronization protocol from the callers
 * and provides methods to initiate the synchronization.
 */
public class SynchronizationManager {
    private final static String XML_NAME_SPACE = "http://schemas.applab.org/2010/07/search";
    private final static String REQUEST_ELEMENT_NAME = "GetKeywordsRequest";
    private final static String VERSION_ELEMENT_NAME = "localKeywordsVersion";
    private final static String CURRENT_FARMER_ID_COUNT = "currentFarmerIdCount";
    private final static String IMAGES_VERSION_ELEMENT_NAME = "localImagesVersion";
    private final static String CURRENT_MENU_IDS = "menuIds";
    private final static String DEFAULT_KEYWORDS_VERSION = "2010-04-04 00:00:00";
    private final static String DEFAULT_IMAGES_VERSION = "2010-04-04 00:00:00";

    private boolean synchronizing = false;
    private static final SynchronizationManager INSTANCE = new SynchronizationManager();
    private Context applicationContext;
    private Map<String, SynchronizationListener> synchronizationListenerList =
            new HashMap<String, SynchronizationListener>();

    private static final int DEFAULT_NETWORK_TIMEOUT = 3 * 60 * 1000;

    private SynchronizationManager() {
        applicationContext = ApplicationRegistry.getApplicationContext();

    }

    public static SynchronizationManager getInstance() {
        return INSTANCE;
    }

    /**
     * called to initialize the synchronization manager.
     */
    public synchronized void initialize() {
    }

    /**
     * called to start the synchronization process in a new
     * thread only if it's not running. The synchronization manager gives feedback through the
     * synchronization listener events.
     * <p/>
     * This method is non-blocking and therefore returns immediately.
     *
     * @see #registerListener(SynchronizationListener)
     */
    public synchronized void start() {
        if (this.isSynchronizing())
            return;

        /*
        starts a new thread to begin the synchronization. The synchronization manager
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    notifySynchronizationListeners("synchronizationStart");
                    synchronizing = true;

                    int maxSynchronizationSteps = 4;
                    notifySynchronizationListeners("synchronizationUpdate", 1, maxSynchronizationSteps,
                            ApplicationRegistry.getApplicationContext().
                                    getResources().getString(R.string.country_code_download_msg), false);
                    downloadCountryCode();

                    notifySynchronizationListeners("synchronizationUpdate", 2, maxSynchronizationSteps,
                            ApplicationRegistry.getApplicationContext().
                                    getResources().getString(R.string.upload_search_logs_download_msg), false);
                    uploadSearchLogs();

                    notifySynchronizationListeners("synchronizationUpdate", 3, maxSynchronizationSteps,
                            ApplicationRegistry.getApplicationContext().
                                    getResources().getString(R.string.keyword_download_msg), false);
                    downloadSearchMenus();

                    notifySynchronizationListeners("synchronizationUpdate", maxSynchronizationSteps,
                            maxSynchronizationSteps,
                            ApplicationRegistry.getApplicationContext().
                                    getResources().getString(R.string.synchronization_complete_msg), true);

                    notifySynchronizationListeners("synchronizationComplete");
                } finally {
                    synchronizing = false;
                }
            }
        }).start();
    }

    protected void uploadSearchLogs() {

    }

    protected void downloadSearchMenus() {
        try {
            String url = SettingsManager.getInstance().getValue(SettingsConstants.KEY_SERVER) +
                    ApplicationRegistry.getApplicationContext().getString(R.string.keyword_server_url_path);

            String keywordVersion =
                    SettingsManager.getInstance().getValue(SettingsConstants.KEY_KEYWORDS_VERSION,
                            DEFAULT_KEYWORDS_VERSION);

            int networkTimeout = 10 * 60 * 1000;
            InputStream inputStream = HttpHelpers.postJsonRequestAndGetStream(url,
                    (StringEntity) getRequestEntity(ApplicationRegistry.getApplicationContext()), networkTimeout);

            String searchCacheFile = ApplicationRegistry.getApplicationContext().getCacheDir() +
                    "/keywords.cache";

            boolean downloadComplete = HttpHelpers.writeStreamToTempFile(inputStream, searchCacheFile);
            inputStream.close();

            File cacheFile = new File(searchCacheFile);
            FileInputStream fileInputStream = new FileInputStream(cacheFile);

            if (downloadComplete && fileInputStream != null) {
                parseSearchMenus(fileInputStream);
            }

        } catch (Exception ex) {
            Log.e(SynchronizationManager.class.getName(), "Error downloading keywords", ex);
        }
    }

    private void parseSearchMenus(FileInputStream fileInputStream) throws IOException, ParseException {
        final List<SearchMenu> searchMenus = new ArrayList<SearchMenu>();
        final List<SearchMenuItem> searchMenuItems = new ArrayList<SearchMenuItem>();
        final List<SearchMenuItem> deletedSearchMenuItems = new ArrayList<SearchMenuItem>();
        final String[] keywordVersion = new String[1];
        final String[] imagesVersion = new String[1];
        final int[] keywordCount = new int[1];

        new JSONParser().parse(new InputStreamReader(fileInputStream), new JsonSimpleBaseParser() {
            private Object keywordObject = null;
            private String keywordType = "";

            @Override
            public boolean primitive(Object value) throws ParseException, IOException {
                if (null != key && value != null) {
                    if (key.equals("Version")) {
                        keywordVersion[0] = value.toString();
                        imagesVersion[0] = value.toString();
                    } else if (key.equals("Total")) {
                        keywordCount[0] = Integer.parseInt(value.toString());
                    } else {
                        if (keywordObject instanceof SearchMenu) {
                            populateSearchMenu((SearchMenu) keywordObject, key, value.toString());
                        } else if (keywordObject instanceof SearchMenuItem) {
                            populateSearchMenuItem((SearchMenuItem) keywordObject, key, value.toString());
                        }
                    }
                }

                key = null;
                return true;
            }

            @Override
            public boolean startArray() throws ParseException, IOException {
                keywordType = key;
                return true;
            }

            @Override
            public boolean startObject() throws ParseException, IOException {
                if ("Menus".equalsIgnoreCase(key)) {
                    keywordObject = new SearchMenu();
                } else if ("MenuItems".equalsIgnoreCase(key) || "DeletedMenuItems".equalsIgnoreCase(key)) {
                    keywordObject = new SearchMenuItem();
                } else if ("Images".equalsIgnoreCase(key)) {

                } else if ("DeletedImages".equalsIgnoreCase(key)) {

                }

                return true;
            }

            @Override
            public boolean endObject() throws ParseException, IOException {
                if (keywordObject != null) {
                    if (keywordObject instanceof SearchMenu) {
                        searchMenus.add((SearchMenu) keywordObject);
                    } else if (keywordObject instanceof SearchMenuItem &&
                            keywordType.equalsIgnoreCase("MenuItems")) {
                        searchMenuItems.add((SearchMenuItem) keywordObject);
                    } else if (keywordObject instanceof SearchMenuItem &&
                            keywordType.equalsIgnoreCase("DeletedMenuItems")) {
                        deletedSearchMenuItems.add((SearchMenuItem) keywordObject);
                    }
                }

                keywordObject = null;
                return true;
            }
        });
    }

    private void populateSearchMenuItem(SearchMenuItem searchMenuItem, String property, String value) {
        if ("id".equalsIgnoreCase(property)) {
            searchMenuItem.setId(value);
        }
    }

    private void populateSearchMenu(SearchMenu searchMenu, String property, String value) {
        if ("id".equalsIgnoreCase(property)) {
            searchMenu.setId(value);
        } else if ("label".equalsIgnoreCase(property)) {
            searchMenu.setLabel(value);
        }
    }


    /**
     * Sets the version in the update request entity Passes the keywords version, images version and current MenuIds
     *
     * @return XML request entity
     * @throws UnsupportedEncodingException
     */
    static AbstractHttpEntity getRequestEntity(Context context) throws UnsupportedEncodingException {
        String keywordsVersion = SettingsManager.getInstance().getValue(SettingsConstants.KEY_KEYWORDS_VERSION,
                DEFAULT_KEYWORDS_VERSION);

        String imagesVersion = SettingsManager.getInstance().getValue(SettingsConstants.KEY_IMAGES_VERSION,
                DEFAULT_IMAGES_VERSION);

        XmlEntityBuilder xmlRequest = new XmlEntityBuilder();
        xmlRequest.writeStartElement(REQUEST_ELEMENT_NAME, XML_NAME_SPACE);
        xmlRequest.writeStartElement(VERSION_ELEMENT_NAME);
        xmlRequest.writeText(keywordsVersion);
        xmlRequest.writeEndElement();
        xmlRequest.writeStartElement(IMAGES_VERSION_ELEMENT_NAME);
        xmlRequest.writeText(imagesVersion);
        xmlRequest.writeEndElement();
        xmlRequest.writeStartElement(CURRENT_MENU_IDS);
        xmlRequest.writeText(getMenuIds());
        xmlRequest.writeEndElement();
        xmlRequest.writeEndElement();
        return xmlRequest.getEntity();
    }

    private static String getMenuIds() {
        MenuItemService menuItemService = new MenuItemService();
        List<SearchMenu> searchMenuList = menuItemService.getAllSearchMenus();

        boolean first = true;
        StringBuilder stringBuilder = new StringBuilder("");

        for (SearchMenu searchMenu : searchMenuList) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(",");
            }

            stringBuilder.append(searchMenu.getId());
        }

        return stringBuilder.toString();
    }

    protected void downloadCountryCode() {
        String countryCode = SettingsManager.getInstance().getValue(SettingsConstants.KEY_COUNTRY_CODE, "NONE");
        if ("NONE".equalsIgnoreCase(countryCode)) {
            String url = SettingsManager.getInstance().getValue(SettingsConstants.KEY_SERVER) +
                    ApplicationRegistry.getApplicationContext().getString(R.string.country_code_server_url_path);

            try {
                InputStream inputStream =
                        HttpHelpers.postJsonRequestAndGetStream(url,
                                (StringEntity) buildCountryCodeRequestEntity(), DEFAULT_NETWORK_TIMEOUT);
                if (inputStream != null) {
                    countryCode = parseCountryCode(inputStream);
                    SettingsManager.getInstance().setValue(SettingsConstants.KEY_COUNTRY_CODE, countryCode);

                    inputStream.close();
                }
            } catch (Exception ex) {
                Log.e(SynchronizationManager.class.getName(), "Error downloading country code", ex);
                notifySynchronizationListeners("onSynchronizationError", ex);
            }
        }
    }

    /**
     * parses the given json input stream and returns the country code.
     *
     * @param inputStream
     * @return country code
     * @throws Exception
     */
    private String parseCountryCode(InputStream inputStream) throws Exception {
        final String[] countryCodeHolder = new String[1];

        new JSONParser().parse(new InputStreamReader(inputStream), new JsonSimpleBaseParser() {
            @Override
            public boolean primitive(Object value) throws ParseException, IOException {
                if (null != key) {
                    if ("countryCode".equals(key)) {
                        if (value != null) {
                            Log.d(SynchronizationManager.class.getName(),
                                    "The country code is: " + String.valueOf(value));

                            countryCodeHolder[0] = String.valueOf(value);
                        }
                    }
                }
                return true;
            }
        });

        return countryCodeHolder[0];
    }

    private AbstractHttpEntity buildCountryCodeRequestEntity() throws UnsupportedEncodingException {
        XmlEntityBuilder xmlRequest = new XmlEntityBuilder();
        xmlRequest.writeStartElement(REQUEST_ELEMENT_NAME, XML_NAME_SPACE);
        xmlRequest.writeEndElement();
        return xmlRequest.getEntity();
    }

    protected void notifySynchronizationListeners(String methodName, Object... args) {
        for (SynchronizationListener listener : synchronizationListenerList.values()) {
            try {
                Class[] argTypes = null;
                if (args != null) {
                    argTypes = new Class[args.length];
                    for (int index = 0; index < args.length; index++) {
                        argTypes[index] = args[index].getClass();
                    }
                }

                SynchronizationListener.class.
                        getMethod(methodName, argTypes).invoke(listener, args);
            } catch (Exception ex) {
                Log.e(SynchronizationManager.class.getName(),
                        "Error executing listener method", ex);
            }
        }
    }

    /**
     * called to stop an on going synchronization process.
     */
    public synchronized void stop() {
        //TODO stop the synchronization process here.
    }

    /**
     * registers the given synchronization listener and if the listener already exists, it
     * will be replaced.
     *
     * @param listener
     */
    public synchronized void registerListener(SynchronizationListener listener) {
        synchronizationListenerList.put(listener.getClass().getName(), listener);
    }

    /**
     * un registers the given synchronization listener
     *
     * @param listener
     */
    public synchronized void unRegisterListener(SynchronizationListener listener) {
        synchronizationListenerList.remove(listener.getClass().getName());
    }

    /**
     * get a value to determine whether the synchronization manager is in the process of
     * performing a synchronization manager
     *
     * @return
     */
    public boolean isSynchronizing() {
        return synchronizing;
    }
}
