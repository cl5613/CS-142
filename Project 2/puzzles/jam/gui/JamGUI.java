package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;
import java.io.File;
import java.nio.file.Paths;

public class JamGUI extends Application  implements Observer<JamModel, JamClientData>  {
    /** model */
    private JamModel model;
    /** file name */
    private String filename;
    /** borderPane */
    BorderPane borderPane = new BorderPane();
    /** label */
    Label label = new Label();
    /** stage */
    Stage stage = new Stage();
    /** icon size for buttons */
    private final static int ICON_SIZE = 75;

    /** init */
    public void init() {
        filename = getParameters().getRaw().get(0);
        this.model = new JamModel();
        model.addObserver(this);
    }

    /** start */
    @Override
    public void start(Stage stage) throws Exception {
        model.load(filename);
        borderPane.setCenter(makeGridPane());

        HBox hbox = new HBox();
        Button load = new Button("Load");
        Button reset = new Button("Reset");
        Button hint = new Button("Hint");
        hbox.getChildren().addAll(load, reset, hint);
        hbox.setAlignment(Pos.CENTER);

        load.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                System.out.println(file);
            }
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath +=File.separator + "data" + File.separator + "jam";
            fileChooser.setInitialDirectory(new File(currentPath));
            model.load(String.valueOf(file));
        });
        reset.setOnAction((event) -> model.reset());
        hint.setOnAction((event) -> model.hint());

        borderPane.setTop(label);
        BorderPane bottom = new BorderPane();
        bottom.setCenter(hbox);
        borderPane.setBottom(bottom);

        this.stage = stage;
        stage.setTitle("Jam GUI");
        Scene scene = new Scene(borderPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A helper function that builds a grid of buttons to return.
     *
     * @return the grid pane
     */
    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();

        for (int row = 0; row < JamConfig.numRows; ++row) {
            for (int col=0; col < JamConfig.numCols; ++col) {
                Button button = new Button(model.currentConfig.field[row][col]);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                if (model.currentConfig.field[row][col].equals(".")) {
                    button.setText(" ");
                }
                int finalRow = row;
                int finalCol = col;
                button.setOnAction((ActionEvent event) -> model.select(finalRow, finalCol));
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    /**
     * update
     * @param jamModel jamModel
     * @param jamClientData jamClientData
     */
    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {
        borderPane.setCenter(makeGridPane());
        label.setText(String.valueOf(jamClientData));
        borderPane.setTop(label);
        stage.sizeToScene();
    }

    /** main function */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
