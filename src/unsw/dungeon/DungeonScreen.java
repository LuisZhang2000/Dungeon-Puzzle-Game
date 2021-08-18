package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen {
    private Stage stage;
    private String title;
    private DungeonController controller;
    private String map;

    private Scene scene;

    public DungeonScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Dungeon Screen";

    }

    public Scene load(String filename) throws IOException {

        this.map = filename;
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(filename, stage);
        controller = dungeonLoader.loadController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);

        //setDungeonController(controller)

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);

        return scene;
        
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        controller.startDungeon();
        stage.show();
    }

    public DungeonController getController() {
        return controller;
    }

    public void setDungeonController(DungeonController controller) {
		this.controller = controller;
    }

    public Stage getStage() {
        return this.stage;
    }

    // public void restart() {
	// 	try {
	// 		this.load(map);
	// 		this.start();
	// 	} catch (Exception e) {
	// 		System.out.println("restart failed");
	// 	}
	// }

}
