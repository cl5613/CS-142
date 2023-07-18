import dendron.ParseTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Run a test of the dendron language system.
 *
 * @author RIT CS
 */
public class DendronTest {

    private static Map.Entry< Integer, List< String > > entry( int key, String[] value ) {
        List< String > tokens = new LinkedList<>();
        Arrays.stream( value ).forEach( tokens::add );
        return new AbstractMap.SimpleImmutableEntry<>( key, tokens  );
    }

    public static Map< Integer, List< String > > programs = Map.ofEntries(
            entry( 0, new String[]{ "#", "5" } ),
            entry( 1, new String[]{ "#", "_", "5" } ),
            entry( 2, new String[]{ "#", "%", "5" } ),
            entry( 3, new String[]{ "#", "%", "25" } ),
            entry( 4, new String[]{ "#", "+", "5", "25" } ),
            entry( 5, new String[]{ "#", "-", "5", "25" } ),
            entry( 6, new String[]{ "#", "*", "25", "5" } ),
            entry( 7, new String[]{ "#", "/", "25", "5" } ),
            entry( 8, new String[]{ ":=", "x", "55" } ),
            entry( 9, new String[]{
                    ":=", "able", "77",
                    ":=", "baker", "3",
                    ":=", "charlie", "/", "able", "baker"
            } ),
            entry( 10, new String[]{
                    ":=", "a", "3",
                    ":=", "b", "4",
                    ":=", "c", "5",
                    ":=", "result", "+", "*", "b", "b", "_", "*", "*", "4", "a",
                    "c"
            } ),
            entry( 11, new String[]{
                    ":=", "x", "1",
                    ":=", "x", "+", "x", "x",
                    ":=", "x", "*", "+", "x", "x", "x",
                    "#", "x",
                    ":=", "x", "-", "2", "_", "x",
                    ":=", "x", "/", "x", "-2",
                    ":=", "Leicester", "%", "+", "19", "x",
                    "#", "Leicester"
            } ),
            entry( 12, new String[]{
                    ":=", "a", "1",
                    ":=", "b", "_", "1",
                    ":=", "c", "_", "6",
                    ":=", "root", "/", "+", "_", "b", "%", "-", "*",
                    "b", "b", "*", "*", "4", "a", "c", "*", "2", "a",
                    ":=", "root2", "/", "-", "_", "b", "%", "-", "*",
                    "b", "b", "*", "*", "4", "a", "c", "*", "2", "a"
            } ),
            // Now for the bad ones...
            entry( -1, new String[]{ "#", "/", "5", "0" } ),
            entry( -2, new String[]{ ":=", "42", "+", "5", "0" } ),
            entry( -3, new String[]{ "#", "abracadabra" } ),
            entry( -4, new String[]{ ":=", "x", "9", "+", "7", "9" } ),
            entry( -5, new String[]{ ":=", "x", "9", ":=" } ),
            entry( -6, new String[]{ ":=", "y" } ),
            entry( -7, new String[]{ ":=", "x", "9", "%" } )
    );

    /*
    ðŸŒ³ := a 3
    ðŸŒ³ := b 77
    ðŸŒ³ # + 20 + a b
    ðŸŒ³ # / b a
     */

    /**
     * Run a test on the Dendron programming system
     * @param args if a single number and the number is a legitimate test
     *             number (see {@link DendronTest#programs}), run the private
     *             test corresponding to that number;<br>
     *             if a single argument that is not a number, assume it is
     *             the name of a directory whose contents are Dendron
     *             programs to be read in and processed;<br>
     *             if two numbers and they are both in the range of the number
     *             of private stored tests, run the private tests corresponding
     *             to that range;<br>
     *             if other args, consider them tokens
     *             of a Dendron program and run tests on that program;<br>
     *             if no arguments, read the source program from standard
     *             input.
     */
    public static void main( String... args ) {
        try ( PrintWriter console = new PrintWriter( System.out ) ){
            switch ( args.length ) {
                case 0 -> {
                    List< String > tokenList = new LinkedList<>();
                    try ( Scanner text = new Scanner( System.in ) ) {
                        System.out.print( "ðŸŒ³ " );
                        while ( text.hasNextLine() ) {
                            String line = text.nextLine();
                            if ( line.equals( "." ) ) break; // For IntelliJ console
                            tokenList.addAll(
                                    Arrays.asList( line.split( "\\s+" ) ) );
                            System.out.print( "ðŸŒ³ " );
                        }
                    }
                    runOneTest( tokenList, console );
                }
                case 1 -> {
                    if ( args[ 0 ].matches( "-?\\d+" ) ) {
                        int testNum = getTestNumber( args[ 0 ] );
                        System.out.println(
                                "TEST #" + testNum + System.lineSeparator()
                        );
                        List< String> tokenList = programs.get( testNum );
                        runOneTest( tokenList, console );
                    }
                    else {
                        //
                        // Assume argument is a directory of tests.
                        //
                        if ( runDirOfTests( args[ 0 ], console ) ) {
                            System.exit( 0 );
                        }
                        else {
                            showHelp();
                            System.exit( 1 );
                        }
                    }
                }
                case 2 -> {
                    if ( args[ 0 ].matches( "-?\\d+" ) &&
                            args[ 1 ].matches( "-?\\d+" ) ) {
                        int startNum = getTestNumber( args[ 0 ] );
                        int endNum = getTestNumber( args[ 1 ] );
                        List< String > tokenList;
                        System.out.println( "\n_________________________" +
                                "_________________________" +
                                "_________________________" );
                        for ( int testNum = startNum;
                              testNum <= endNum; ++testNum ) {
                            System.out.println(
                                    "TEST #" + testNum + System.lineSeparator()
                            );
                            tokenList = programs.get( testNum );
                            runOneTest( tokenList, console );
                            System.out.println( "\n_________________________" +
                                    "_________________________" +
                                    "_________________________" );
                        }
                    }
                }
                default -> {
                    List< String > tokenList =
                            new LinkedList<>( Arrays.asList( args ) );
                    runOneTest( tokenList, console );
                }
            }
        }
    }
    private static void showHelp() {
        String prog = DendronTest.class.getName();
        System.out.println( "Command line options:" );
        System.out.println( "java " + prog );
        System.out.println( "\t Process program from console input." );
        System.out.println( "java " + prog + " <tnum>" );
        System.out.println( "\t Process test program #<tnum>." );
        System.out.println( "java " + prog + " <first> <last>" );
        System.out.println( "\t Process test programs from #<first> to #<last>." );
        System.out.println( "java " + prog + " <dir>" );
        System.out.println( "\t Process all files in directory <dir> as test " + "programs." );
        System.out.println( "java " + prog + " <anything else>" );
        System.out.println( "\t Process the command line arguments as tokens in a program." );
        printTestNums();
        System.out.println();
    }

    /**
     * Run a single Dendron program. There are three phases.
     * <ol>
     *     <li>Redisplay the program in infix notation.</li>
     *     <li>Interpret the program.</li>
     *     <li>Show the Soros assembly code for this program.</li>
     * </ol>
     * <p>
     * Note that either of the first two phases could fail,
     * which will mean later phases will not run.
     * @param tokenList the string tokens that make up the program
     * @param out where output should go from the interpreting phase
     */
    private static void runOneTest( List< String > tokenList, PrintWriter out )
    {
        ParseTree tree = new ParseTree( tokenList );

        tree.displayProgram();

        tree.interpret();

        tree.compileTo( out );

        out.flush();
    }

    /**
     * Run a whole set of tests from files containing Dendron code.
     * Abort the program if the directory doesn't work out.
     * @param dirName the name of the directory containing the files
     */
    private static boolean runDirOfTests( String dirName, PrintWriter out ) {
        List< String > tokenList = null;
        String[] dummy = new String[ 0 ];
        File dir = new File( dirName );
        File[] files = dir.listFiles();
        if ( files == null ) {
            System.err.println( "Provided directory " +
                    dirName +
                    " does not exist." );
            return false;
        }
        else {
            for ( File file : files ) {
                System.out.println( "\nTest File " +
                        file.getName() + ":\n" );
                tokenList = new ArrayList<>();
                try ( Scanner fileIn = new Scanner( file ) ) {
                    fileIn.forEachRemaining( tokenList::add );
                }
                catch( FileNotFoundException fnfe ) {
                    System.err.println( fnfe );
                    continue;
                }
                runOneTest( tokenList, out );
                System.out.println( "\n_________________________" +
                        "_________________________" +
                        "_________________________" );
            }
            return true;
        }
    }

    /**
     * Convert string to test number. If out of bounds, abort.
     * @param testNumStr the test number from the command line
     * @return the test number as an integer
     */
    private static int getTestNumber( String testNumStr ) {
        int testNum;
        testNum = Integer.parseInt( testNumStr );
        if ( !programs.containsKey( testNum ) ) {
            System.err.println( "Test number out of range: " + testNumStr );
            printTestNums();
            System.exit( 2 );
        }
        return testNum;
    }

    private static void printTestNums() {
        System.err.println( "Valid test numbers:" );
        System.err.println( new TreeSet<>( programs.keySet() ) );
    }
}