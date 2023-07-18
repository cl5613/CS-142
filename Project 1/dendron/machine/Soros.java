package dendron.machine;

import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import dendron.Errors;

/**
 * An abstraction of a computing machine that reads instructions
 * and executes them. It has an instruction set, a symbol table
 * for variables (instead of general-purpose memory), and a
 * value stack on which calculations are performed.
 *
 * (Everything is static to avoid the need to master the subtleties
 * of nested class instantiation or to pass the symbol table and
 * stack into every instruction when it executes.)
 *
 * @author RIT CS
 */
public class Soros {

    public final static String PUSH = "PUSH";

    public final static String LOAD = "LOAD";

    public final static String STORE = "STORE";

    public final static String ADD = "ADD";

    public final static String SUBTRACT = "SUB";

    public final static String MULTIPLY = "MUL";

    public final static String DIVIDE = "DIV";

    public final static String NEGATE = "NEG";

    public final static String SQUARE_ROOT = "SQRT";

    public final static String PRINT = "PRINT";

    public static interface Instruction {
        /**
         * Run this instruction on the Machine, using the Machine's
         * value stack and symbol table.
         */
        void execute( Soros m );

        /**
         * Show the instruction using text so it can be understood
         * by a person.
         * @return a short string describing what this instruction will do
         */
        @Override
        String toString();
    }

    /**
     * This is the equivalent of a Python dict. It will be covered in week 5.
     */
    private Map< String, Integer > table = null;

    public void setVar( String var, int value ) {
        this.table.put( var, value );
    }

    public boolean existsVar( String var ) {
        return this.table.containsKey( var );
    }

    public int getVar( String var ) {
        return this.table.get( var );
    }

    Stack< Integer > stack = null;

    public void push( int value ) {
        this.stack.push( value );
    }

    public int pop() {
        return this.stack.pop();
    }

    /**
     * Reset the Machine to a pristine state.
     * @see Soros#execute
     */
    private void reset() {
        this.stack = new Stack<>();
        this.table = new HashMap<>();
    }

    /**
     * Run a "compiled" program by executing, in order, each instruction
     * contained therein.
     * Report on the final size of the stack (should normally be empty)
     * and the contents of the symbol table.
     * @param program a list of Soros instructions
     */
    public void execute( List< Instruction > program ) {
        this.reset();
        System.out.println("Executing compiled code...");
        for ( Instruction instr: program ) {
            instr.execute( this );
        }
        System.out.println( "Soros: execution ended with " +
                            stack.size() + " items left on the stack." );
        System.out.println();
        Errors.dump( table );
        System.out.println();
    }

// ===========================================================================

/*
 * Nested classes for each Soros instruction
 */

    public static class PushConst implements Instruction {
        private int value;

        public PushConst( int constant ) {
            this.value = constant;
        }

        @Override
        public void execute( Soros m ) {
            m.push( this.value );
        }

        @Override
        public String toString() {
            return "PUSH  " + this.value;
        }
    }

    public static class Print implements Instruction {
        public Print() {}

        /**
         * Output "*** " followed by the value popped from the stack.
         */
        @Override
        public void execute( Soros m ) {
            System.out.println( "=== " + m.pop() );
        }

        @Override
        public String toString() { return "PRINT"; }
    }

    public static class Negate implements Instruction {
        @Override
        public void execute( Soros m ) {
            int op = m.pop();
            m.push( -op );
        }

        @Override
        public String toString() {
            return "NEG";
        }
    }

    public static class Divide implements Instruction {
        @Override
        public void execute( Soros m ) {
            int op2 = m.pop();
            int op1 = m.pop();
            m.push( op1 / op2 );
        }

        @Override
        public String toString() {
            return "DIV";
        }
    }

    public static class Load implements Instruction {
        private String name;

        public Load( String ident ) {
            this.name = ident;
        }

        @Override
        public void execute( Soros m ) {
            if ( !m.existsVar( this.name ) ) {
                Errors.report( Errors.Type.UNINITIALIZED, this.name );
            }
            m.push( m.getVar( this.name ) );
        }

        @Override
        public String toString() {
            return "LOAD  " + this.name;
        }
    }

    public static class Multiply implements Instruction {
        @Override
        public void execute( Soros m ) {
            int op2 = m.pop();
            int op1 = m.pop();
            m.push( op1 * op2 );
        }

        @Override
        public String toString() {
            return "MUL";
        }
    }

    public static class Add implements Instruction {
        /**
         * Run the microsteps for the ADD instruction.
         */
        @Override
        public void execute( Soros m ) {
            int op2 = m.pop();
            int op1 = m.pop();
            m.push( op1 + op2 );
        }

        /**
         * Show the ADD instruction as plain text.
         *
         * @return "ADD"
         */
        @Override
        public String toString() {
            return "ADD";
        }
    }

    public static class SquareRoot implements Instruction {
        @Override
        public void execute( Soros m ) {
            double op = (double)m.pop();
            m.push( (int)Math.sqrt( op ) );
        }

        @Override
        public String toString() {
            return "SQRT";
        }
    }

    public static class Store implements Instruction {
        private String name;

        public Store( String ident ) {
            this.name = ident;
        }

        @Override
        public void execute( Soros m ) {
            m.setVar( this.name, m.pop() );
        }

        @Override
        public String toString() {
            return "STORE " + this.name;
        }
    }

    public static class Subtract implements Instruction {
        @Override
        public void execute( Soros m ) {
            int op2 = m.pop();
            int op1 = m.pop();
            m.push( op1 - op2 );
        }

        @Override
        public String toString() {
            return "SUB";
        }
    }
}
