package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LevelSelectionController {

    @FXML
    private Button l1Button;
    @FXML
    private Button l2Button;
    @FXML
    private Button l3Button;

    private Stage primaryStage;
    private DungeonScreen dungeonScreen;

	public LevelSelectionController(Stage stage) {
        this.primaryStage = stage;
    }
    
    @FXML
    void handleL1Button(ActionEvent event) throws IOException {
        Scene scene = dungeonScreen.load("stage1.json");
        dungeonScreen.start();
    }
    @FXML
    void handleL2Button(ActionEvent event) throws IOException {
        Scene scene = dungeonScreen.load("stage2.json");
        dungeonScreen.start();
    }
    @FXML
    void handleL3Button(ActionEvent event) throws IOException {
        Scene scene = dungeonScreen.load("stage3.json");
        dungeonScreen.start();
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }
}
