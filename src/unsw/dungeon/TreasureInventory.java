package unsw.dungeon;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class TreasureInventory {

    private IntegerProperty treasureCount;
    
    /**
     * Creates a TreasureInventory
     */
    public TreasureInventory() {
        this.treasureCount = new SimpleIntegerProperty(0);
    }

    public void pickUpTreasure() {
        this.treasureCount.set(getTreasureCount() + 1);
        System.out.println("treasures: " + getTreasureCount());
    }

    public int getTreasureCount() {
        return this.treasureCount.get();
    }

    public IntegerProperty getTreasureIP() {
        return this.treasureCount;
    }
}