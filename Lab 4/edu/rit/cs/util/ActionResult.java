package edu.rit.cs.util;

/**
 * These objects hold information about the result of
 * executing a method that could potentially fail.
 * @author RIT CS
 */
public class ActionResult {

    /**
     * Only if things went well will this flag be true.
     */
    public final boolean ok;

    /**
     * If things did not go well, an explanation will be stored here.
     */
    private final String errorMessage;

    /**
     * A private constructor that initializes the instance
     * @param ok true iff all is well
     * @param errorMessage a message to print if not ok
     */
    private ActionResult( boolean ok, String errorMessage ) {
        this.ok = ok;
        this.errorMessage = errorMessage;
    }

    /**
     * Create an instance of ActionResult for a method that failed.
     * @param errorMessage a string describing what happened
     * @rit.post the ok flag is set to false.
     */
    public ActionResult( String errorMessage ) {
        this( false, errorMessage );
    }

    /**
     * What happened?
     * @return the explanation string ("" if ok is true)
     */
    public String message() { return this.errorMessage; }

    /**
     * A pre-initialized instance of ActionResult where ok is true.
     * This is used because they are all the same; you don't need
     * more than one instance where ok is true.
     */
    public static final ActionResult OK = new ActionResult( true, "" );
}
