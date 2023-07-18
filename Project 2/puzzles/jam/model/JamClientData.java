package puzzles.jam.model;

/** message
 * @author Chen Lin
 */
public class JamClientData {

    /** messages that used in game */
    private String message;

    /**
     * JamClientData
     * @param message message that needs to be display in the game
     */
    public JamClientData(String message) {
        this.message = message;
    }

    /**
     * to string method
     * @return the message
     */
    @Override
    public String toString() {
        return message;
    }
}
