package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import model.Card;
import model.ConcentrationModel;
import model.Observer;
import java.util.*;

/**
 * The ConcentrationGUI application is the UI for Concentration.
 *
 * @author Chen Lin
 */
public class ConcentrationGUI extends Application
        implements Observer< ConcentrationModel, Object > {

    /** number of columns */
    private final static int Cols = 4;
    /** number of rows */
    private final static int Rows = 4;
    /** types of Pokemon */
    private enum Pokemon {
        ABRA,
        BULBASAUR,
        CHARMANDER,
        JIGGLYPUFF,
        MEOWTH,
        PIKACHU,
        SQUIRTLE,
        VENOMOTH,
    }
    /** Pokeball image */
    private Image PokeBall = new Image(getClass().getResourceAsStream("resources/pokeball.png"));
    /** Abra image */
    private Image Abra = new Image(getClass().getResourceAsStream("resources/abra.png"));
    /** Bulbasaur image */
    private Image Bulbasaur = new Image(getClass().getResourceAsStream("resources/bulbasaur.png"));
    /** CharmAnder image */
    private Image CharmAnder = new Image(getClass().getResourceAsStream("resources/charmander.png"));
    /** JigglyPuff image */
    private Image JigglyPuff = new Image(getClass().getResourceAsStream("resources/jigglypuff.png"));
    /** Meowth image */
    private Image Meowth = new Image(getClass().getResourceAsStream("resources/meowth.png"));
    /** Pikachu image */
    private Image Pikachu = new Image(getClass().getResourceAsStream("resources/pikachu.png"));
    /** Squirtle image */
    private Image Squirtle = new Image(getClass().getResourceAsStream("resources/squirtle.png"));
    /** Venomoth image */
    private Image Venomoth = new Image(getClass().getResourceAsStream("resources/venomoth.png"));

    /** the label with the name of the last pokemon selected */
    private Label label;
    /** a definition of white for the button background */
    private static final Background WHITE =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    /** the font size of the label */
    private final static int LABEL_FONT_SIZE = 25;
    /** the ConcentrationModel */
    private ConcentrationModel model;
    /** pokemon Buttons */
    private ArrayList<PokemonButton> pokemonButtons;

    /** ConcentrationGUI constructor */
    public ConcentrationGUI(){

    }

    /**
     * initial
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        this.model = new ConcentrationModel();
        model.addObserver(this);
    }

    private class PokemonButton extends Button {
        /**
         * the type of this pokemon
         */
        private Pokemon pokemon;
        /**
         * ID for the cards
         */
        private final int id;

        /**
         * Create the button with the image based on the pokemon.
         *
         * @param pokemon the pokemon
         */
        public PokemonButton(Pokemon pokemon, int id) {
            this.pokemon = pokemon;
            this.id = id;
            Image image;
            switch (pokemon) {
                case ABRA:
                    image = Abra;
                    break;
                case BULBASAUR:
                    image = Bulbasaur;
                    break;
                case CHARMANDER:
                    image = CharmAnder;
                    break;
                case JIGGLYPUFF:
                    image = JigglyPuff;
                    break;
                case MEOWTH:
                    image = Meowth;
                    break;
                case PIKACHU:
                    image = Pikachu;
                    break;
                case SQUIRTLE:
                    image = Squirtle;
                    break;
                case VENOMOTH:
                    image = Venomoth;
                    break;
                default:
                    image = PokeBall;
            }
            this.setGraphic(new ImageView(image));
            this.setBackground(WHITE);
        }
        /** get id for the cards */
        public int getMyId() {
            return this.id;
        }
    }

    /**
     * A helper function that builds a grid of buttons to return.
     *
     * @return the grid pane
     */
    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();

        Pokemon pokemon[] = Pokemon.values();
        int i = 0;
        pokemonButtons = new ArrayList<>();
        for (int row = 0; row < Rows; ++row) {
            for (int col=0; col < Cols; ++col) {
                PokemonButton button = new PokemonButton(pokemon[0], i);
                button.setGraphic(new ImageView(PokeBall));
                button.setOnAction((ActionEvent event) -> this.model.selectCard(button.getMyId()));
                gridPane.add(button, col, row);
                i++;
                pokemonButtons.add(button);
            }
        }
        return gridPane;
    }

    /**
     * start constructs the layout for the game
     *
     * @param stage container (window) in which to render the UI.
     * @throws Exception
     */
    @Override
    public void start( Stage stage ) throws Exception {
        BorderPane borderPane = new BorderPane();

        this.label = new Label("Select a card");
        this.label.setStyle("-fx-font: " + LABEL_FONT_SIZE + " arial;");
        borderPane.setTop(this.label);

        GridPane gridPane = makeGridPane();
        borderPane.setCenter(gridPane);

        HBox hbox = new HBox();
        Button reset = new Button("Reset");
        Button undo = new Button("Undo");
        Button cheat = new Button("Cheat");
        hbox.getChildren().addAll(reset, undo, cheat);
        hbox.setAlignment(Pos.CENTER);

        reset.setOnAction((event) -> model.reset());
        undo.setOnAction((event) -> model.undo());
        cheat.setOnAction((ActionEvent event) -> model.cheat());

        BorderPane bottom = new BorderPane();
        int moves = 0;
        Label label1 = new Label(moves + " " + "moves");
        bottom.setCenter(hbox);
        bottom.setRight(label1);
        borderPane.setBottom(bottom);

        Scene scene = new Scene(borderPane);
        stage.setTitle("Concentration");
        stage.setScene(scene);
        stage.setResizable(false);
        System.out.println("init: Initialize and connect to model!");
        stage.show();
    }

    /**
     * Update the UI. This method is called by an object in the game model.
     * The contents of the buttons are changed based on the card faces in the model.
     * Changes in the the text in the labels may also occur based on the changed model state.
     * The update makes calls to the public interface of the model components to determine
     * the new values to display.
     *
     * @param concentrationModel  the model object that knows the current board state
     * @param o null ⇒ non-cheating mode; non-null ⇒ cheating mode
     */
    @Override
    public void update( ConcentrationModel concentrationModel, Object o ) {
        ArrayList<Card> card = concentrationModel.getCards();
        for (int i = 0; i < card.size(); i++) {
            Image image = PokeBall;
            if (card.get(i).isFaceUp()) {
                switch (card.get(i).getNumber()) {
                    case 0 -> image = Abra;
                    case 1 -> image = Bulbasaur;
                    case 2 -> image = CharmAnder;
                    case 3 -> image = JigglyPuff;
                    case 4 -> image = Meowth;
                    case 5 -> image = Pikachu;
                    case 6 -> image = Squirtle;
                    case 7 -> image = Venomoth;
                }
            }
            pokemonButtons.get(i).setGraphic(new ImageView(image));
        }
        if (concentrationModel.howManyCardsUp() % 2 == 1) {
            this.label.setText("Select the second card");
        } else if (this.model.getCards().stream().allMatch(Card::isFaceUp)) {
            this.label.setText("You Win!");
        } else if (concentrationModel.howManyCardsUp() >= 2) {
            this.label.setText("Select the first card");
        }

    }

    /**
     * main entry point launches the JavaFX GUI.
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        Application.launch( args );
    }
}
