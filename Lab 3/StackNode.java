package rit.stu;

import rit.cs.Stack;
import rit.cs.Node;

/**
 * A stack implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the stack will hold
 * @author Chen Lin
 */
public class StackNode<T> implements Stack<T> {

    /** top node in the stack */
    private Node<T> top;
    /**
     * Create an empty stack.
     */
    public StackNode() {
    }

    /**
     * Check if the stack is currently empty or not.
     * @return true if empty, false otherwise
     */
    @Override
    public boolean empty() {
        if (top == null) {
            return true;
        }
        return false;
    }

    /**
     * Remove and return the top element in the stack.
     * @return the front element
     */
    @Override
    public T pop() {
        assert !empty();
        T popped = top.getData();
        top = top.getNext();
        return popped;
    }

    /**
     * Add a new element to the top of the stack.
     * @param element The new data element
     */
    @Override
    public void push(T element) {
        Node<T> node = new Node<>(element, top);
        top = node;
    }

    /**
     * Get the top element on the stack.
     * @return the top element
     */
    @Override
    public T top() {
        assert !empty();
        return top.getData();
    }
}
