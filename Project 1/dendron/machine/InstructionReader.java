package dendron.machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

/**
 * Interpret Soros assembly instructions from a file.
 *
 * @author RIT CS
 */
public class InstructionReader {

    public static String EOF = ".";

    private static Map< String, Function< String[], Soros.Instruction> > gen
            = new HashMap<>()
    {{
        put( "PUSH", in -> { int i = Integer.parseInt( in[1] );
                             return new Soros.PushConst( i ); } );
        put( "LOAD", in -> { String v = in[ 1 ];
                             return new Soros.Load( v ); } );
        put( "STORE", in -> { String v = in[ 1 ];
                              return new Soros.Store( v ); } );
        put( "ADD", in -> new Soros.Add() );
        put( "SUB", in -> new Soros.Subtract() );
        put( "MUL", in -> new Soros.Multiply() );
        put( "DIV", in -> new Soros.Divide() );
        put( "NEG", in -> new Soros.Negate() );
        put( "SQRT", in -> new Soros.SquareRoot() );
        put( "PRINT", in -> new Soros.Print() );
    }};

    /**
     * Read instructions from the named file and translate them
     * to internal form.
     * @param assyFile the text file containing the assembly code
     * @return a list of Soros.Instruction objects, ready to execute
     */
    public static List< Soros.Instruction > assemble( Scanner assyFile ) {
        List< Soros.Instruction > result = new LinkedList<>();
        while ( assyFile.hasNextLine() ) {
            String[] items = assyFile.nextLine().strip().split( "\\s+" );
            String mnemonic = items[ 0 ];
            if ( gen.containsKey( mnemonic ) ) {
                result.add( gen.get( mnemonic ).apply( items ) );
            }
            else if ( mnemonic.equals( EOF ) ) {
                break; // manual end of file (for when inside IntelliJ IDEA)
            }
            else {
                System.err.println( "Illegal assembly instr " + mnemonic );
            }
        }
        return result;
    }

    /**
     * Assemble and execute some Dendron machine code.
     * @param args the name of the assembly language source file
     */
    public static void main( String[] args ) {
        Scanner codeFile = null;
        switch ( args.length ) {
            case 0 -> { codeFile = new Scanner( System.in ); }
            case 1 -> { try {
                    codeFile = new Scanner( new File( args[ 0 ] ) );
                }
                catch( FileNotFoundException fnfe ) {
                    System.err.println( fnfe.getMessage() );
                    System.exit( 1 );
                }
            }
            default -> {
                System.err.println(
                        "Usage: java InstructionReader [assembly-code-file]" );
                System.exit( 1 );
            }
        }
        List< Soros.Instruction > code = assemble( codeFile );
        new Soros().execute( code );
        codeFile.close();
    }
}
