package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Main Menu");

        //DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("tutorial.json");

        MainMenuController controller = new MainMenuController(primaryStage);
        controller.setDungeonScreen(new DungeonScreen(primaryStage));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

        //MainMenuScreen mainMenuScreen = new MainMenuScreen(primaryStage);
        //DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);

        // Both controllers need to know about the other screen.

        //mainMenuScreen.getController().setDungeonScreen(dungeonScreen);
        //dungeonScreen.getController().setMainMenuScreen(mainMenuScreen);

        //mainMenuScreen.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
