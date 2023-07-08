package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of TSQueue that follows first-in-first-out behavior.
 *
 * @param <E> the type of elements that will be in the queue
 * @author Chen Lin
 */

public class FIFOTSQueue< E extends TimedObject > implements TSQueue< E > {

    /** a list */
    private final List<E> list;

    /**
     * Initialize the underlying data structure used for the queue.
     */
    public FIFOTSQueue() {
        this.list = new LinkedList<>();
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on the value.
     * This method is synchronized because its body is a critical region.
     *
     * @param value the value to be enqueued
     * @return the size of the queue after the value was added
     */
    @Override
    public synchronized int enqueue(E value) {
        value.enterQueue();
        list.add(value);
        this.notifyAll();
        return list.size();
    }

    /**
     * Removes a value from the queue and calls TimedObject.exitQueue() on the value.
     * This method is expected to block (wait) if the queue is empty, rather than
     * throwing an exception or returning a null value. This method is synchronized
     * because its body is a critical region.
     *
     * @return the value that has been in the queue for the longest period of time
     */
    @Override
    public synchronized E dequeue() {
        E var;
        while (list.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var = list.remove(0);
        var.exitQueue();
        return var;
    }
}

