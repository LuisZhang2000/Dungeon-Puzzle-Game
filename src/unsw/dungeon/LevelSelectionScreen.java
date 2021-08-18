package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelSelectionScreen {

    private Stage stage;
    private String title;
    private LevelSelectionController controller;
    private Scene scene;

    public LevelSelectionScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Level Selection";

        controller = new LevelSelectionController(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LSView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root, 500, 300);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public LevelSelectionController getController() {
        return controller;
    }
    
}