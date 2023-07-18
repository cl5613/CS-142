package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamModel;
import java.util.Scanner;

public class JamPTUI implements Observer<JamModel, JamClientData> {

    /** JamModel */
    private JamModel model;

    /**
     * Construct the JamPTUI
     */
    public JamPTUI() {
        this.model = new JamModel();
        model.addObserver(this);
    }

    /**
     * help messages used in game
     */
    private void displayHelp() {
        System.out.println("h(int)    -- hint next move");
        System.out.println("l(oad) filename     -- load new puzzle file");
        System.out.println("s(elect) r c     -- select cell at r, c");
        System.out.println("q(uit)    -- quit the game");
        System.out.println("r(eset)    -- reset the current game");
    }

    /**
     * Read a command and execute loop.
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        displayHelp();
        System.out.println();
        for ( ; ; ) {
            System.out.print("Jam game command: ");
            String[] words = in.nextLine().split(" ");
            if (words.length > 0) {
                if (words[0].startsWith("h") || words[0].startsWith("H")) {
                    this.model.hint();
                }
                else if (words[0].startsWith("l") || words[0].startsWith("L")) {
                    this.model.load(words[1]);
                }
                else if (words[0].startsWith("s") || words[0].startsWith("S")) {
                    int i = Integer.parseInt(words[1]);
                    int j = Integer.parseInt(words[2]);
                    this.model.select(i, j);
                }
                else if (words[0].startsWith("q") || words[0].startsWith("Q")) {
                    this.model.quit();
                }
                else if (words[0].startsWith("r") || words[0].startsWith("R")) {
                    this.model.reset();
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    /**
     * update method
     *
     * @param jamModel jamModel
     * @param jamClientData jamClientData
     */
    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {
        System.out.println(jamClientData);
        System.out.println(jamModel);
    }

    /**
     * Main method use to play the game
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        }
        else {
            JamPTUI ptui = new JamPTUI();
            ptui.model.load(args[0]);
            ptui.run();
        }
    }
}
