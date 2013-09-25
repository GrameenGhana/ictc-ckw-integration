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
import org.grameenfoundation.search.utils.ImageUtils;
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
    private MenuItemService menuItemService = new MenuItemService();

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
                } catch (IOException e) {
                    Log.e(SynchronizationManager.class.getName(), "IOException", e);
                    notifySynchronizationListeners("onSynchronizationError",
                            new Throwable(applicationContext.getString(R.string.error_connecting_to_server)));
                } finally {
                    synchronizing = false;
                }
            }
        }).start();
    }

    protected void uploadSearchLogs() {

    }

    protected void downloadSearchMenus() throws IOException {
        try {
            String url = SettingsManager.getInstance().getValue(SettingsConstants.KEY_SERVER) +
                    ApplicationRegistry.getApplicationContext().getString(R.string.keyword_server_url_path);

            String keywordVersion =
                    SettingsManager.getInstance().getValue(SettingsConstants.KEY_KEYWORDS_VERSION,
                            DEFAULT_KEYWORDS_VERSION);

            int networkTimeout = 10 * 60 * 1000;
            InputStream inputStream = HttpHelpers.postJsonRequestAndGetStream(url,
                    (StringEntity) getRequestEntity(ApplicationRegistry.getApplicationContext()), networkTimeout);

            String searchCacheFile = ApplicationRegistry.getApplicationContext().getCacheDir() + "/keywords.cache";
            File cacheFile = new File(searchCacheFile);
            if (cacheFile.exists()) {
                boolean deleted = cacheFile.delete();
                if (deleted) {
                    Log.i(SynchronizationManager.class.getName(), "Cache File Deleted.");
                }
            }

            boolean downloadComplete = HttpHelpers.writeStreamToTempFile(inputStream, searchCacheFile);
            FileInputStream fileInputStream = new FileInputStream(cacheFile);
            try {
                if (downloadComplete && fileInputStream != null) {
                    processKeywords(fileInputStream);
                }
            } finally {
                fileInputStream.close();
                inputStream.close();
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            Log.e(SynchronizationManager.class.getName(), "Error downloading keywords", ex);
            notifySynchronizationListeners("onSynchronizationError",
                    new Throwable(applicationContext.getString(R.string.error_downloading_keywords)));
        }
    }

    private void processKeywords(InputStream inputStream) throws IOException, ParseException {
        final List<SearchMenu> searchMenus = new ArrayList<SearchMenu>();
        List<SearchMenu> oldSearchMenus = menuItemService.getAllSearchMenus();
        final List<SearchMenuItem> searchMenuItems = new ArrayList<SearchMenuItem>();
        final List<SearchMenuItem> deletedSearchMenuItems = new ArrayList<SearchMenuItem>();
        final List<String> imageIdz = new ArrayList<String>();
        final List<String> deleteImageIz = new ArrayList<String>();
        final String[] keywordVersion = new String[1];
        final String[] imagesVersion = new String[1];
        final int[] keywordCount = new int[1];

        try {
            new JSONParser().parse(new InputStreamReader(inputStream), new JsonSimpleBaseParser() {
                private Object keywordObject = null;
                private String keywordType = "";
                private int keywordCounter = 0;

                @Override
                public boolean primitive(Object value) throws ParseException, IOException {
                    if (null != key && value != null) {
                        if (key.equals("Version")) {
                            keywordVersion[0] = value.toString();
                            imagesVersion[0] = value.toString();
                        } else if (key.equals("Total")) {
                            keywordCount[0] = Integer.parseInt(value.toString());

                            notifySynchronizationListeners("synchronizationUpdate", keywordCounter++, keywordCount[0],
                                    ApplicationRegistry.getApplicationContext().
                                            getResources().getString(R.string.processing_keywords_msg), true);
                        } else {
                            if (keywordObject instanceof SearchMenu) {
                                populateSearchMenu((SearchMenu) keywordObject, key, value.toString());
                            } else if (keywordObject instanceof SearchMenuItem) {
                                populateSearchMenuItem((SearchMenuItem) keywordObject, key, value.toString());
                            } else if ("id".equalsIgnoreCase(key) && keywordObject instanceof String
                                    && keywordType.equalsIgnoreCase("Images")) {
                                keywordObject = value;
                            } else if ("id".equalsIgnoreCase(key) && keywordObject instanceof String
                                    && keywordType.equalsIgnoreCase("DeletedImages")) {
                                keywordObject = value;
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
                    if ("Menus".equalsIgnoreCase(keywordType)) {
                        keywordObject = new SearchMenu();
                    } else if ("MenuItems".equalsIgnoreCase(keywordType)
                            || "DeletedMenuItems".equalsIgnoreCase(keywordType)) {
                        keywordObject = new SearchMenuItem();
                    } else if ("Images".equalsIgnoreCase(keywordType)) {
                        keywordObject = new String();
                    } else if ("DeletedImages".equalsIgnoreCase(keywordType)) {
                        keywordObject = new String();
                    }

                    return true;
                }

                @Override
                public boolean endObject() throws ParseException, IOException {
                    if (keywordObject != null) {
                        if (keywordObject instanceof SearchMenu) {
                            searchMenus.add((SearchMenu) keywordObject);

                            menuItemService.save((SearchMenu) keywordObject);
                        } else if (keywordObject instanceof SearchMenuItem &&
                                keywordType.equalsIgnoreCase("MenuItems")) {
                            //searchMenuItems.add((SearchMenuItem) keywordObject);
                            menuItemService.save((SearchMenuItem) keywordObject);

                            notifySynchronizationListeners("synchronizationUpdate", keywordCounter++, keywordCount[0],
                                    ApplicationRegistry.getApplicationContext().
                                            getResources().getString(R.string.processing_keywords_msg), true);

                        } else if (keywordObject instanceof SearchMenuItem &&
                                keywordType.equalsIgnoreCase("DeletedMenuItems")) {
                            //deletedSearchMenuItems.add((SearchMenuItem) keywordObject);
                            notifySynchronizationListeners("synchronizationUpdate", 1, 1,
                                    ApplicationRegistry.getApplicationContext().
                                            getResources().getString(R.string.removing_keywords_msg), true);

                            menuItemService.deleteSearchMenuItems((SearchMenuItem) keywordObject);
                        } else if (keywordObject instanceof String &&
                                keywordType.equalsIgnoreCase("Images")) {
                            imageIdz.add((String) keywordObject);
                        } else if (keywordObject instanceof String &&
                                keywordType.equalsIgnoreCase("DeletedImages")) {
                            deleteImageIz.add((String) keywordObject);
                        }
                    }

                    keywordObject = null;
                    return true;
                }
            });
        } catch (Exception ex) {
            Log.e(SynchronizationManager.class.getName(), "Parsing Error", ex);
        }

        deleteOldMenus(oldSearchMenus, searchMenus);
        downloadImages(imageIdz);
        deleteUnusedImages(deleteImageIz);

        SettingsManager.getInstance().setValue(SettingsConstants.KEY_KEYWORDS_VERSION, keywordVersion[0]);
        SettingsManager.getInstance().setValue(SettingsConstants.KEY_IMAGES_VERSION, imagesVersion[0]);
    }

    private void deleteUnusedImages(List<String> deleteImageIz) {
        if (deleteImageIz != null) {
            for (String imageId : deleteImageIz) {
                if (imageId != null && imageId.trim().length() > 0) {
                    File file = new File(ImageUtils.IMAGE_ROOT, imageId + ".jpg");
                    ImageUtils.deleteFile(file);
                }
            }
        }
    }

    private void downloadImages(List<String> imageIds) throws IOException {
        if (imageIds != null) {
            int count = imageIds.size(), counter = 0;

            for (String imageId : imageIds) {
                notifySynchronizationListeners("synchronizationUpdate", counter++, count,
                        ApplicationRegistry.getApplicationContext().
                                getResources().getString(R.string.downloading_images_msg), true);

                if (imageId != null || imageId.trim().length() > 0) {
                    // Only download image if image does not already exist!
                    if (!ImageUtils.imageExists(imageId.toLowerCase(), false)) {
                        Log.d("Image Download", "Getting " + imageId);
                        String url = SettingsManager.getInstance().getValue(SettingsConstants.KEY_SERVER) +
                                ApplicationRegistry.getApplicationContext().getString(R.string.image_server_url_path) +
                                imageId;

                        InputStream image = HttpHelpers.getResource(url);
                        ImageUtils.writeFile(imageId + ".jpg", image);
                    }
                }

            }
        }
    }

    private void deleteOldMenus(List<SearchMenu> oldSearchMenus, List<SearchMenu> searchMenus) {
        for (SearchMenu searchMenu : oldSearchMenus) {
            boolean exists = false;
            for (SearchMenu newSearchMenu : searchMenus) {
                if (newSearchMenu.getId().equalsIgnoreCase(searchMenu.getId())) {
                    exists = true;
                }
            }

            if (!exists) {
                menuItemService.deleteSearchMenus(searchMenu);
                menuItemService.deleteSearchMenuItems(searchMenu);
            }
        }
    }

    private void populateSearchMenuItem(SearchMenuItem searchMenuItem, String property, String value) {
        if ("id".equalsIgnoreCase(property)) {
            searchMenuItem.setId(value);
        } else if ("position".equalsIgnoreCase(property)) {
            searchMenuItem.setPosition(Integer.parseInt(value));
        } else if ("parent_id".equalsIgnoreCase(property)) {
            searchMenuItem.setParentId(value);
        } else if ("menu_id".equalsIgnoreCase(property)) {
            searchMenuItem.setMenuId(value);
        } else if ("label".equalsIgnoreCase(property)) {
            searchMenuItem.setLabel(value);
        } else if ("content".equalsIgnoreCase(property)) {
            searchMenuItem.setContent(value);
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
                notifySynchronizationListeners("onSynchronizationError", new Throwable(ex));
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
