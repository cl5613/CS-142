package edu.rit.cs.grocerystore;

/**
 * This abstract class was created to allow anything put in a
 * {@link TSQueue} to store timing information. It follows the standard
 * queuing system model:
 * <ul>
 *     <li>when did the item arrive at the queue for service</li>
 *     <li>when did the item come out of the queue and start being serviced</li>
 *     <li>when was the item's servicing complete</li>
 * </ul>
 *
 * @author RIT CS
 */
public abstract class TimedObject {

    private long enterTime;
    private long exitTime;
    private long finishedTime;

    /**
     * Indicates that this item has arrived at the queue for service.
     * Record the current time.
     */
    public void enterQueue() {
        this.enterTime = System.currentTimeMillis();
    }

    /**
     * Indicates that this item has been pulled out of the queue for service.
     * Record the current time.
     */
    public void exitQueue() {
        this.exitTime = System.currentTimeMillis();
    }

    /**
     * Indicates that this item's servicing is complete.
     * Record the current time.
     */
    public void servicingDone() {
        this.finishedTime = System.currentTimeMillis();
    }

    /**
     * How long did this item have to wait?
     * @return the exit-queue time minus the enter-queue time
     */
    public double getInQueueTime() {
        return ( this.exitTime - this.enterTime );
    }

    /**
     * How long did it take for this item to be serviced?
     * @return the servicing-done time minus the exit-queue time
     */
    public double getServiceTime() {
        return ( this.finishedTime - this.exitTime );
    }

    /**
     * How much time overall was this item in the system?
     * @return the servicing-done time minus the enter-queue time
     */
    public double getTotalWaitTime() {
        return ( this.finishedTime - this.enterTime );
    }
}
