package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button tutorialButton;
    @FXML
    private Button lsButton;

    private Stage primaryStage;
    private DungeonScreen dungeonScreen;

    private TutorialScreen tutorialScreen;

    public MainMenuController(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    void handleLsButton(ActionEvent event) throws IOException {
        primaryStage.setTitle("Level Selection");

        //DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("tutorial.json");

        LevelSelectionController controller = new LevelSelectionController(primaryStage);
        controller.setDungeonScreen(new DungeonScreen(primaryStage));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LSView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void handleTutButton(ActionEvent event) throws IOException {
        // Scene scene = dungeonScreen.load("tutorial.json");
        // dungeonScreen.start();
        
        TutorialController controller = new TutorialController(primaryStage);
        controller.setDungeonScreen(new DungeonScreen(primaryStage));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    void handleTutorialButton(ActionEvent event) throws IOException {

        // tutorialScreen = new TutorialScreen(primaryStage);
        // tutorialScreen.load("tutorial.json");
        // tutorialScreen.start();
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

    public void setTutorialScreen(TutorialScreen tutorialScreen) {
        this.tutorialScreen = tutorialScreen;
    }

}
