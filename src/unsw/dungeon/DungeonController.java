package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private Pane background;
    @FXML
    private GridPane squares;
    @FXML
    private VBox infoPage;
    @FXML
    private VBox goalConditions;
    @FXML
    private VBox inventoryStatus;
    @FXML
    private Label swordStatus;
    @FXML
    private Label treasureStatus;
    @FXML
    private Label keyStatus;
    @FXML
    private Label potionStatus;

    private List<ImageView> initialEntities;

    private Player player;

    private Inventory inventory;

    private Dungeon dungeon;

    @FXML
    private Button pauseButton;
    
    private MainMenuScreen mainMenuScreen;
    private Timeline timeline;
    private boolean isPaused = false;

    private Stage stage;

    @FXML
    private Button nextLevel;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Stage stage) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.inventory= dungeon.getInventory();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        infoPage.setLayoutX(dungeon.getWidth() * 33);

        updateGoals(dungeon.getGoal(), goalConditions);
        updateInventory(inventory, inventoryStatus);    
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (isPaused) {
			if (event.getCode() == KeyCode.ESCAPE) {
                pause();
            }
			return;
		}
        switch (event.getCode()) {
        case UP:
            player.move(Direction.UP);
            break;
        case DOWN:
            player.move(Direction.DOWN);
            break;
        case LEFT:
            player.move(Direction.LEFT);
            break;
        case RIGHT:
            player.move(Direction.RIGHT);
            event.consume();
            break;
        case G:
            inventory.dropKey();
            break;
        case ESCAPE:
            pause();
            break;
        case TAB:
            try {
                restart();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("restart fail");
            }
            break;
        default:
            break;
        }
    }

    /**
     * Helper function for adding goalstatus to goalConditions
     * @param goals
     * @param goalConditions
     */
    public void updateGoals(Goal goals, VBox goalConditions) {
        CheckBox goalCheck = new CheckBox(goals.toString());
        goalCheck.setTextFill(Color.WHITE);
        // Bind checkBox to goal.getIsComplete
        goalCheck.selectedProperty().bindBidirectional(goals.getIsCompleteBP());
		
        VBox subGoals = new VBox();
        subGoals.setPadding(new Insets(0, 0, 0, 20));
        subGoals.getChildren().add(goalCheck);
        goalConditions.getChildren().add(subGoals);
        if (goals instanceof ANDGoal) {
            ANDGoal andGoal = (ANDGoal) goals;
            for (Goal subGoal : andGoal.getListSubGoals()) {
                updateGoals(subGoal, subGoals);
            }
        }
        if (goals instanceof ORGoal) {
            ORGoal orGoal = (ORGoal) goals;
            for (Goal subGoal : orGoal.getListSubGoals()) {
                updateGoals(subGoal, subGoals);
            }
        }
    }

    public void updateInventory(Inventory inventory, VBox inventoryStatus) {
        SwordInventory si = inventory.getSwordInventory();
        si.getDurabilityIP().addListener((observable, oldValue, newValue) -> {
            if ((int)newValue > 0) {
                // updating sword durability to UI
                swordStatus.setVisible(true);
                swordStatus.setText("Sword : Durability " + newValue);
            } else {
                // no sword in inventory
                swordStatus.setVisible(false);
            }  
        });
        
        // Simlarly for treasure
        TreasureInventory ti = inventory.getTreasureInventory();
        int totalTreausre = dungeon.numTreasure();
        ti.getTreasureIP().addListener((observable, oldValue, newValue) -> {
            if ((int)newValue > 0) {
                treasureStatus.setVisible(true);
                treasureStatus.setText("Treasure: " + newValue + "/" + totalTreausre);
            } else {
                treasureStatus.setVisible(false);
            }  
        });

        // Simlarly for Potion
        PotionInventory pi = inventory.getPotion();
        pi.getPotionTimeIP().addListener((observable, oldValue, newValue) -> {
            if ((int)newValue > 0) {
                potionStatus.setVisible(true);
                potionStatus.setText("Invincibility Potion: rounds remaining " + newValue);
            } else {
                potionStatus.setVisible(false);
            }  
        });

        // Simlarly for Key
        KeyInventory ki = inventory.getKey();
        ki.keyIDProperty().addListener((observable, oldValue, newValue) -> {
            if ((int)newValue == -1) {
                potionStatus.setVisible(false);
            } else {
                potionStatus.setVisible(true);
                potionStatus.setText("key: keyID " + ki.getKeyID());
            }  
        });
    }

    public void startDungeon() {
        if (dungeon != null) {
            //updateInventory();
            squares.requestFocus();
            this.timeline = new Timeline();
            KeyFrame keyFrame  = new KeyFrame(Duration.millis(1000));
            timeline.getKeyFrames().addAll(keyFrame); 
            this.timeline.setCycleCount(Timeline.INDEFINITE);
            this.timeline.play();
        } else {
            System.out.println("dungeon not set");
        }
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }

    private void pause() {
		if (!isPaused) {
            pauseGame();
        } else {
            resumeGame();
        }
	}

    private void pauseGame() {
		timeline.stop();
        this.isPaused = true;
        ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.7);
		squares.setEffect(colorAdjust);
        System.out.println("Game paused");
    }
    
    private void resumeGame() {
		timeline.play();
        this.isPaused = false;
        ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(0);
		squares.setEffect(colorAdjust);
        System.out.println("Game resumed");
    }

    public void restart() throws IOException {
        
        DungeonScreen dungeonScreen = new DungeonScreen(stage);
        dungeonScreen.load("tutorial.json");
		dungeonScreen.start();
	}

    @FXML
    void handlePauseButton(ActionEvent event) {
        pause();
    }

}

