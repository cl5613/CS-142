package rit.stu;

import rit.cs.Queue;
import rit.cs.QueueList;

/**
 * A test program for QueueList and QueueNode.<br>
 * <br>
 * Usage: java QueueTester [list | node]<br>
 * <br>
 * @author Sean Strout @ RIT CS
 */
public class QueueTester {

    /**
     * The types of Queues to test (list or node based)
     */
    public enum QueueType {
        LIST,   /* list based Queue */
        NODE    /* node based Queue */
    }

    /** A Queue that holds strings */
    private Queue<String> strQueue;
    /** A Queue the holds integers */
    private Queue<Integer> intQueue;

    /**
     * Construct a QueueTester object.
     * @param type The type of Queue to test
     */
    public QueueTester(QueueType type) {
        switch (type) {
            case LIST:
                strQueue = new QueueList<>();
                intQueue = new QueueList<>();
                break;
             case NODE:
                strQueue = new QueueNode<>();
                intQueue = new QueueNode<>();
        }
    }

    /**
     * Test the string based Queue.
     */
    public void testString() {
        System.out.println("Testing string Queue...");
        System.out.println("\tempty? " +
                (strQueue.empty() ? "OK" : "FAIL!"));

        System.out.println("\tenqueueing 'A'...");
        strQueue.enqueue("A");
        System.out.println("\t\tnot empty? " +
                (!strQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tfront is 'A'? " +
                (strQueue.front().equals("A") ? "OK" : "FAIL!"));
        System.out.println("\t\tback is 'A'? " +
                (strQueue.back().equals("A") ? "OK" : "FAIL!"));

        System.out.println("\tdequeueing...");
        String element = strQueue.dequeue();
        System.out.println("\t\tempty? " +
                (strQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious front was 'A'? " + (element.equals("A") ? "OK" : "FAIL!"));

        System.out.print("\tdequeueing empty Queue (expect AssertionError)... ");
        System.out.flush();
        try {
            strQueue.dequeue();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.print("\tfront on empty Queue (expect AssertionError)... ");
        System.out.flush();
        try {
            strQueue.front();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.println("\tenqueueing 'A'...");
        strQueue.enqueue("A");
        System.out.println("\tenqueueing 'B'...");
        strQueue.enqueue("B");
        System.out.println("\tenqueueing 'C'...");
        strQueue.enqueue("C");
        System.out.println("\t\tnot empty? " +
                (!strQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tfront is 'A'? " +
                (strQueue.front().equals("A") ? "OK" : "FAIL!"));
        System.out.println("\t\tback is 'C'? " +
                (strQueue.back().equals("C") ? "OK" : "FAIL!"));

        System.out.println("\tdequeueing...");
        element = strQueue.dequeue();
        System.out.println("\t\tnot empty? " +
                (!strQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious front was 'A'? " + (element.equals("A") ? "OK" : "FAIL!"));

        System.out.println("\tenqueueing 'D'...");
        strQueue.enqueue("D");
        System.out.println("\t\tfront is 'B'? " +
                (strQueue.front().equals("B") ? "OK" : "FAIL!"));
        System.out.println("\t\tfront is 'D'? " +
                (strQueue.back().equals("D") ? "OK" : "FAIL!"));

        System.out.println("\temptying Queue...");
        while (!strQueue.empty()) {
            element = strQueue.dequeue();
            System.out.println("\t\tgot: " + element);
        }
    }

    /**
     * Test the integer based Queue.
     */
    public void testInt() {
        System.out.println("Testing integer Queue...");
        System.out.println("\tempty? " +
                (intQueue.empty() ? "OK" : "FAIL!"));

        System.out.println("\tenqueueing 1...");
        intQueue.enqueue(1);
        System.out.println("\t\tnot empty? " +
                (!intQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tfront is 1? " +
                (intQueue.front() == 1 ? "OK" : "FAIL!"));
        System.out.println("\t\tback is 1? " +
                (intQueue.back() == 1 ? "OK" : "FAIL!"));

        System.out.println("\tdequeueing...");
        Integer element = intQueue.dequeue();
        System.out.println("\t\tempty? " +
                (intQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious front was 1? " + (element == 1 ? "OK" : "FAIL!"));

        System.out.print("\tdequeueing empty Queue (expect AssertionError)... ");
        System.out.flush();
        try {
            intQueue.dequeue();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.print("\tfront on empty Queue (expect AssertionError)... ");
        System.out.flush();
        try {
            intQueue.front();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.println("\tenqueueing 1...");
        intQueue.enqueue(1);
        System.out.println("\tenqueueing 2...");
        intQueue.enqueue(2);
        System.out.println("\tenqueueing 3...");
        intQueue.enqueue(3);
        System.out.println("\t\tnot empty? " +
                (!intQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tfront is 1? " +
                (intQueue.front() == 1 ? "OK" : "FAIL!"));
        System.out.println("\t\tback is 3? " +
                (intQueue.back() == 3 ? "OK" : "FAIL!"));

        System.out.println("\tdequeueing...");
        element = intQueue.dequeue();
        System.out.println("\t\tnot empty? " +
                (!intQueue.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious front was 1? " + (element == 1 ? "OK" : "FAIL!"));

        System.out.println("\tenqueueing 4...");
        intQueue.enqueue(4);
        System.out.println("\t\tfront is 2? " +
                (intQueue.front() == 2 ? "OK" : "FAIL!"));
        System.out.println("\t\tback is 4? " +
                (intQueue.back() == 4 ? "OK" : "FAIL!"));

        System.out.println("\temptying Queue...");
        while (!intQueue.empty()) {
            element = intQueue.dequeue();
            System.out.println("\t\tgot: " + element);
        }
    }

    /**
     * The main program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java QueueTester [list | node]");
            return;
        }

        QueueTester tester;
        switch (args[0]) {
            case "node":
                tester = new QueueTester(QueueType.NODE);
                break;
            case "list":
                tester = new QueueTester(QueueType.LIST);
                break;
            default:
                System.out.println("Unrecognized option: " + args[0] +
                    ".  Expected 'list' or 'node'");
                return;
        }

        tester.testString();
        tester.testInt();
    }
}
