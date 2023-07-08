package edu.rit.cs.grocerystore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creating Customers and starting each one on a separate thread.
 *
 * @author Chen Lin
 */
public class CustomerPool {

    /** the number of customers */
    private int numCustomers;
    /** the average load */
    private int avgLoad;
    /** the average delay */
    private double AvgDelay;
    /** checkout line */
    private TSQueue<Cart> checkoutLine;

    /**
     * Store all the parameter values for later use. Also, initialize a Random number generator
     * @param checkoutLine the provided queue of carts
     * @param numCustomers the number of customers to create
     * @param avgLoad the mean number of groceries in each Cart
     * @param AvgDelay the mean amount of time between Customer arrivals at checkout
     */
    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers, int avgLoad, double AvgDelay) {
        this.numCustomers = numCustomers;
        this.avgLoad = avgLoad;
        this.AvgDelay = AvgDelay;
        this.checkoutLine = checkoutLine;
    }

    /**
     * Create the given number of customers, start them all up on separate threads,
     * and wait for them to finish. In the process, a Cart will be created for each Customer.
     * Each cart has a random number of groceries placed in it (avgLoad from constructor is the mean).
     * Each customer is told to wait a random amount of time more than the previous customer.
     * The additional time to wait is a random value based on the avgDelay from the constructor.
     * Note that the time for each customer to wait is measured from roughly the start of this method.
     * Therefore, times assigned to each successive customer go steadily upwards.
     *
     * @return the list of Cart objects made for all the Customers.
     * These get returned because they have timing information in them.
     */
    public List<Cart> simulateCustomers() {
        Random rand = new Random();
        ArrayList<Cart> list =  new ArrayList<>();
        ArrayList<Thread> threadList = new ArrayList<>();
        double preDelay = 0;
        for (int i = 0; i < numCustomers; i++) {
            int randInt = (int) Utilities.sinePDFDelay(rand, avgLoad);
            Cart cart = new Cart(randInt);
            preDelay += Utilities.sinePDFDelay(rand, AvgDelay);
            Customer customer = new Customer(preDelay, cart, checkoutLine);
            Thread customerThread = new Thread(customer);
            list.add(cart);
            threadList.add(customerThread);
        }

        for (Thread thread: threadList) {
            thread.start();
        }

        for (Thread thread: threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
