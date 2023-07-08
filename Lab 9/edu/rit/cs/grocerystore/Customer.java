package edu.rit.cs.grocerystore;

/**
 * In this simulation, a customer's only job is to wait for the specified time,
 * then enqueue its shopping cart in the checkout line queue. Every customer has
 * an integer ID. They are automatically numbered consecutively starting at 1.
 *
 * @author Chen Lin
 */
public class Customer implements Runnable{

    /** delay */
    private double delay;
    /** cart */
    private Cart cart;
    /** queue */
    private TSQueue<Cart> queue;
    /** customers' ID */
    private int ID;
    /** counter. Used for ID */
    private static int counter = 1;

    /**
     * Create a Customer object, storing all parameters provided
     * and assigning the next consecutive integer as this object's ID.
     * First Customer is #1.
     *
     * @param delay how many msec. to wait before enqueuing the cart at the checkout line
     * @param cart the cart of groceries (already filled with goodies)
     * @param queue the checkout line
     */
    public Customer(double delay, Cart cart, TSQueue<Cart> queue) {
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;
        this.ID = counter;
        counter++;
    }

    /**
     * This method should be executed by a thread uniquely assigned to this Customer.
     * This is what the method does.
     * Sleep for the given delay time.
     * Put the given Cart in the checkout queue.
     * Print a message announcing the above has been done.
     *
     * The format of the message is
     *
     * Customer id with cart has entered the line, with N customers in front.
     */
    public synchronized void run( ) {
        try {
            Thread.sleep((long) delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int front = queue.enqueue(cart) - 1;
        System.out.println("Customer " + ID + " with " + cart + " has entered the line, with " + front + " customers in front.");
    }
}
