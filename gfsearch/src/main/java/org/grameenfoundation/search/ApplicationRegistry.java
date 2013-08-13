package org.grameenfoundation.search;

import android.content.Context;

/**
 * Utility class that shares data across the entire application
 */
public final class ApplicationRegistry {
    private ApplicationRegistry() {
    }

    private static Context applicationContext;

    /**
     * gets the application context
     *
     * @return
     */
    public static Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * sets the application context
     *
     * @param applicationContext
     */
    public static void setApplicationContext(Context applicationContext) {
        ApplicationRegistry.applicationContext = applicationContext;
    }
}
