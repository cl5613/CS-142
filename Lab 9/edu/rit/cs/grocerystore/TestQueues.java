package edu.rit.cs.grocerystore;

import java.util.Arrays;
import java.util.Random;

/**
 * Do a test of the thread-safe queue implementations.
 * @author RIT CS
 */
public class TestQueues {

    /** Producer tells consumers that it is finished. */
    private static final int TERMINATOR = 100;

    /** Highest non-terminating value put in queue */
    private static final int MAX = TERMINATOR / 2;

    private static Random rand = new Random();

    public static void main( String[] args ) {
        fifo();
        prio();
    }

    /**
     * For this test, the numbers should come out of the queue in roughly
     * the same order they came in. You can never be completely certain
     * because the printing occurs outside of the protected critical region
     * of the queue methods.
     */
    private static void fifo() {
        System.out.println(
                System.lineSeparator() + "FIFO" + System.lineSeparator() );
        go( new FIFOTSQueue<>() );
    }

    /**
     * For this test, you should see the smallest number in the queue
     * always being picked to come out. Variation will exist because the
     * print statements are not in a protected critical region. However
     * once the producers are done what is left should come out in
     * increasing numerical order.
     */
    private static void prio() {
        System.out.println(
                System.lineSeparator() + "Priority" + System.lineSeparator() );
        go( new PriorityTSQueue<>() );
    }

    /**
     * Start up 2 consumer threads and 5 producer threads.
     * @param queue the queue that they put values to and get values from
     */
    private static void go( final TSQueue< TimedInteger > queue ) {
        // Create two consumer threads that try to dequeue numbers.
        Thread[] threads = new Thread[ 7 ];
        for ( int i=0; i < 2; ++i ) {
            threads[ i ] = new Thread( () -> consumeLoop( queue ) );
        }

        // Create five producer threads that place numbers in the queue.
        for ( int i=2; i < 7; ++i ) {
            threads[ i ] = new Thread( () -> produceLoop( queue ) );
        }

        Arrays.stream( threads ).forEach( Thread::start );

        Arrays.stream( threads ).forEach( t -> {
            try {
                t.join();
            }
            catch( InterruptedException ie ) {}
        });
    }

    private static void consumeLoop( TSQueue< TimedInteger > queue ) {
        int item;
        do {
            sleep( 200L );
            item = get( queue );
        } while ( item != TERMINATOR );
    }

    private static void produceLoop( TSQueue< TimedInteger > queue ) {
        sleep( 2000L );
        for ( int j=0; j < 4; ++j ) {
            int item = rand.nextInt( MAX );
            put( queue, item );
        }
        sleep( 5000L );
        put( queue, TERMINATOR );
    }

    private static int get( TSQueue< TimedInteger > q ) {
        int item;
        item = q.dequeue().get();
        System.out.println(
                "                          " +
                "Consumer " +
                Thread.currentThread().getId() +
                " got " + item + '.'
        );
        return item;
    }

    private static Object LOCK = new Object();

    private static void put( TSQueue< TimedInteger > q, int item ) {
        synchronized( LOCK ) {
            System.out.println(
                    "Producer " +
                    Thread.currentThread().getId() +
                    " is putting " + item + '.'
            );
            q.enqueue( new TimedInteger( item ) );
            sleep( 50L );
        }
    }

    private static void sleep( long millis ) {
        try {
            Thread.sleep( millis );
        }
        catch( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}

class TimedInteger extends TimedObject implements Comparable< TimedInteger > {
    private int d;
    public TimedInteger( int d ) { this.d = d; }
    public int get() { return this.d; }
    @Override
    public int compareTo( TimedInteger other ) {
        return Integer.compare( this.d, other.d );
    }
}
