package rit.stu;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the queue will hold
 * @author RIT CS
 */
public class QueueNode<T> implements Queue<T> {
    /** front node */
    private Node<T> front;
    /** back node */
    private Node<T> back;

    /**
     * Create an empty queue.
     */
    public QueueNode() {
    }

    /**
     * Get the last element in the queue.
     * @return the back element
     */
    @Override
    public T back() {
        assert !empty();
        return back.getData();
    }

    /**
     * Remove and return the front element in the queue.
     * @return the front element
     */
    @Override
    public T dequeue() {
        assert !empty();
        T removed = front.getData();
        front = front.getNext();
        if (empty()) {
            back = null;
        }
        return removed;
    }

    /**
     * Check if the queue is currently empty or not.
     * @return true if empty, false otherwise
     */
    @Override
    public boolean empty() {
        if (front == null) {
            return true;
        }
        return false;
    }

    /**
     * Add a new element to the back of the queue.
     * @param element The new data element
     */
    @Override
    public void enqueue(T element) {
        Node<T> node = new Node<>(element, null);
        if (empty()) {
            front = node;
        }
        else {
            back.setNext(node);
        }
        back = node;
    }

    /**
     * Get the front element in the queue.
     * @return the front element
     */
    @Override
    public T front() {
        assert !empty();
        return front.getData();
    }
}
