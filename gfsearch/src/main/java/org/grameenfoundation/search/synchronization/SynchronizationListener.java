package org.grameenfoundation.search.synchronization;

/**
 * Interface implemented by classes that would like to listen in to synchronization events.
 */
public interface SynchronizationListener {

    /**
     * called when synchronization has started.
     */
    void synchronizationStart();

    /**
     * called during the synchronization process to indicate process.
     *
     * @param step
     * @param max
     * @param message
     * @param reset   value indicating whether to reset the progress update.
     */
    void synchronizationUpdate(Integer step, Integer max, String message, Boolean reset);

    /**
     * called when the synchronization process has completed.
     */
    void synchronizationComplete();

    /**
     * called when an error occurs during the synchronization process.
     *
     * @param throwable
     */
    void onSynchronizationError(Throwable throwable);

}
