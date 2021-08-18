package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TutorialController {

    @FXML
    private Button playButton;

    private Stage primaryStage;
    private DungeonScreen dungeonScreen;

	public TutorialController(Stage stage) {
        this.primaryStage = stage;
    }
    
    @FXML
    void handlePlayButton(ActionEvent event) throws IOException {
        Scene scene = dungeonScreen.load("tutorial.json");
        dungeonScreen.start();
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }
}
