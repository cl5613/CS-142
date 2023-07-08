package edu.rit.cs.grocerystore;

/**
 * In the simulation, the person who checks out customers' groceries.
 * In terms of Producer/Consumer architectures, this is the consumer.
 * In terms of queuing theory, this is the service.
 *
 * @author Chen Lin
 */
public class Clerk implements Runnable{

    /** checkoutLine */
    private final TSQueue<Cart> checkoutLine;

    /**
     * Create a Clerk and connect it to its checkout line.
     * @param checkoutLine the queue from which the clerk gets carts to checkout
     */
    public Clerk(TSQueue<Cart> checkoutLine) {
        this.checkoutLine = checkoutLine;
    }

    /**
     * This method, running on a separate thread, continuously removes carts
     * from the checkout line and sleeps for a time period to simulate the
     * checkout process. When the sleep time for a cart is complete, it then
     * "tells" the cart that it has finished servicing the cart so that times
     * can be saved. See TimedObject.servicingDone(). The sleep time is the
     * number of items in the cart, multiplied by Utilities.TIME_PER_CART_ITEM.
     * The method exits when it removes the special cart Utilities.NO_MORE_CARTS,
     * which is not included in any timing measurements.
     */
    public synchronized void run() {
        Cart cart = checkoutLine.dequeue();
        while (cart != Utilities.NO_MORE_CARTS) {
            Utilities.println("Clerk got " + cart );
            try {
                Thread.sleep(cart.getCartSize() * Utilities.TIME_PER_CART_ITEM);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cart.servicingDone();
            cart = checkoutLine.dequeue();
        }
    }
}
