package org.grameenfoundation.search.synchronization;

import android.content.Context;
import android.util.Log;
import org.grameenfoundation.search.ApplicationRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * A Facade that handles synchronization of search menus and menu items.
 * It abstracts the underlying synchronization protocol from the callers
 * and provides methods to initiate the synchronization.
 */
public class SynchronizationManager {
    private boolean synchronizing = false;
    private static final SynchronizationManager INSTANCE = new SynchronizationManager();
    private Context applicationContext;
    private Map<String, SynchronizationListener> synchronizationListenerList =
            new HashMap<String, SynchronizationListener>();

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
     * called to start the synchronization process.
     */
    public void start() {
        try {
            if (this.isSynchronizing())
                return;

            notifySynchronizationListeners("synchronizationStart");
            this.synchronizing = true;

            downloadCountryCode();
            uploadSearchLogs();
            downloadMenus();

            notifySynchronizationListeners("synchronizationComplete");
        } finally {
            this.synchronizing = false;
        }
    }

    private void uploadSearchLogs() {

    }

    private void downloadMenus() {

    }

    private void notifySynchronizationListeners(String methodName, Object... args) {
        for (SynchronizationListener listener : synchronizationListenerList.values()) {
            try {
                SynchronizationListener.class.
                        getMethod(methodName, null).invoke(listener, args);
            } catch (Exception ex) {
                Log.e(SynchronizationManager.class.getName(),
                        "Error executing listener method", ex);
            }
        }
    }

    private void downloadCountryCode() {

    }

    /**
     * called to stop an on going synchronization process.
     */
    public synchronized void stop() {
        //TODO stop the synchronization process here.
    }

    /**
     * registers the given synchronization listener and it the listener already exists, it
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
