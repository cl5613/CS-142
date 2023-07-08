package edu.rit.cs.grocerystore;

import java.util.PriorityQueue;

/**
 * A TSQueue with priority queue extraction order
 * @param <E> the type of elements that will be in the queue (must be Comparable)
 * @author Chen Lin
 */
public class PriorityTSQueue< E extends TimedObject & Comparable< E > >
        implements TSQueue< E > {

    /** a list */
    private final PriorityQueue<E> list;

    /**
     * Initialize the underlying data structure used for the queue
     */
    public PriorityTSQueue() {
        this.list = new PriorityQueue<>();
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on the value.
     * This method is synchronized because its body is a critical region.
     *
     * @param value the value to be enqueued
     * @return the size of the queue after the value was added
     */
    @Override
    public synchronized int enqueue( E value ) {
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
     * @return the minimum value in the queue, according to E's natural ordering
     */
    @Override
    public synchronized E dequeue() {
        E min;
        while(list.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        min = list.poll();
        min.exitQueue();
        return min;
    }
}
