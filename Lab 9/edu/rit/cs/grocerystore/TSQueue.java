package edu.rit.cs.grocerystore;

import edu.rit.cs.grocerystore.TimedObject;

/**
 * A queue interface with some special properties:
 * <ul>
 * <li>
 *     The queue is thread-safe. It blocks a call to dequeue if the queue is
 *     currently empty.
 * </li>
 * <li>
 *     There are no extra methods like is-empty or peek.
 * </li>
 * <li>
 *     The {@link #enqueue(TimedObject)} method returns the current size of the
 *     queue as a convenience for tracing.
 * </li>
 * <li>
 *     The {@link #enqueue(TimedObject)} and {@link #dequeue()} methods should
 *     call the appropriate methods in the {@link TimedObject} class to record
 *     the times of those events.
 * </li>
 * </ul>
 *
 * @param <E> the type of elements that will be in the queue
 * @author RIT CS
 */
public interface TSQueue< E extends TimedObject > {

    /**
     * Puts the value in the queue, and calls {@link TimedObject#enterQueue()}
     * on the value.
     *
     * @param value the value to be enqueued
     * @return the size of the queue, after the value was added
     */
    public abstract int enqueue( E value );

    /**
     * Removes a value from the queue and calls {@link TimedObject#exitQueue()}
     * on the value. This method is expected to block (wait) if the queue
     * is empty, rather than throwing an exception or returning a null value.
     * @return the value that was removed
     */
    public abstract E dequeue();


}
