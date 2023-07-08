package rit.stu;

import rit.cs.Stack;
import rit.cs.StackList;

/**
 * A test program for StackList and StackNode.<br>
 * <br>
 *  Usage: java StackTester [list | node]<br>
 * <br>
 * @author RIT CS
 */
public class StackTester {

    /**
     * The types of stacks to test (list or node based)
     */
    public enum StackType {
        LIST,   /* list based stack */
        NODE    /* node based stack */
    }

    /** A stack that holds strings */
    private Stack<String> strStack;
    /** A stack the holds integers */
    private Stack<Integer> intStack;

    /**
     * Construct a StackTester object.
     * @param type The type of stack to test
     */
    public StackTester(StackType type) {
        switch (type) {
            case LIST:
                strStack = new StackList<>();
                intStack = new StackList<>();
                break;
            case NODE:
                strStack = new StackNode<>();
                intStack = new StackNode<>();
        }
    }

    /**
     * Test the string based stack.
     */
    public void testString() {
        System.out.println("Testing string stack...");
        System.out.println("\tempty? " +
                (strStack.empty() ? "OK" : "FAIL!"));

        System.out.println("\tpushing 'A'...");
        strStack.push("A");
        System.out.println("\t\tnot empty? " +
                (!strStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\ttop is 'A'? " +
                (strStack.top().equals("A") ? "OK" : "FAIL!"));

        System.out.println("\tpopping...");
        String element = strStack.pop();
        System.out.println("\t\tempty? " +
                (strStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious top was 'A'? " + (element.equals("A") ? "OK" : "FAIL!"));

        System.out.print("\tpopping empty stack (expect AssertionError)... ");
        System.out.flush();
        try {
            strStack.pop();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.print("\ttop on empty stack (expect AssertionError)... ");
        System.out.flush();
        try {
            strStack.top();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.println("\tpushing 'A'...");
        strStack.push("A");
        System.out.println("\tpushing 'B'...");
        strStack.push("B");
        System.out.println("\tpushing 'C'...");
        strStack.push("C");
        System.out.println("\t\tnot empty? " +
                (!strStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\ttop is 'C'? " +
                (strStack.top().equals("C") ? "OK" : "FAIL!"));

        System.out.println("\tpopping...");
        element = strStack.pop();
        System.out.println("\t\tnot empty? " +
                (!strStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious top was 'C'? " + (element.equals("C") ? "OK" : "FAIL!"));

        System.out.println("\tpushing 'D'...");
        strStack.push("D");
        System.out.println("\t\ttop is 'D'? " +
                (strStack.top().equals("D") ? "OK" : "FAIL!"));

        System.out.println("\temptying stack...");
        while (!strStack.empty()) {
            element = strStack.pop();
            System.out.println("\t\tgot: " + element);
        }
    }

    /**
     * Test the integer based stack.
     */
    public void testInt() {
        System.out.println("Testing integer stack...");
        System.out.println("\tempty? " +
                (intStack.empty() ? "OK" : "FAIL!"));

        System.out.println("\tpushing 1...");
        intStack.push(1);
        System.out.println("\t\tnot empty? " +
                (!intStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\ttop is 1? " +
                (intStack.top() == 1 ? "OK" : "FAIL!"));

        System.out.println("\tpopping...");
        Integer element = intStack.pop();
        System.out.println("\t\tempty? " +
                (intStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious top was 1? " + (element == 1 ? "OK" : "FAIL!"));

        System.out.print("\tpopping empty stack (expect AssertionError)... ");
        System.out.flush();
        try {
            intStack.pop();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.print("\ttop on empty stack (expect AssertionError)... ");
        System.out.flush();
        try {
            intStack.top();
        } catch (AssertionError ae) {
            System.out.println("OK");
        } catch (NullPointerException npe) {
            System.out.println("FAIL!  Got a null pointer exception");
            npe.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("FAIL!  Got: " + e);
        }

        System.out.println("\tpushing 1...");
        intStack.push(1);
        System.out.println("\tpushing 2...");
        intStack.push(2);
        System.out.println("\tpushing 3...");
        intStack.push(3);
        System.out.println("\t\tnot empty? " +
                (!intStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\ttop is 3? " +
                (intStack.top() == 3 ? "OK" : "FAIL!"));

        System.out.println("\tpopping...");
        element = intStack.pop();
        System.out.println("\t\tnot empty? " +
                (!intStack.empty() ? "OK" : "FAIL!"));
        System.out.println("\t\tprevious top was 3? " + (element == 3 ? "OK" : "FAIL!"));

        System.out.println("\tpushing 4...");
        intStack.push(4);
        System.out.println("\t\ttop is 4? " +
                (intStack.top() == 4 ? "OK" : "FAIL!"));

        System.out.println("\temptying stack...");
        while (!intStack.empty()) {
            element = intStack.pop();
            System.out.println("\t\tgot: " + element);
        }
    }

    /**
     * The main program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java StackTester [list | node]");
            return;
        }

        StackTester tester;
        switch (args[0]) {
            case "node":
                tester = new StackTester(StackType.NODE);
                break;
            case "list":
                tester = new StackTester(StackType.LIST);
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
