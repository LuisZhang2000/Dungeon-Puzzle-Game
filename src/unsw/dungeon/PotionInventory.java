package unsw.dungeon;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public class PotionInventory {
    
    private IntegerProperty potionTime;

    /**
     * Creates a PotionInventory
     * 
     */
    public PotionInventory() {
        this.potionTime = new SimpleIntegerProperty(0);
    }

    public boolean isInvincible() {
        return potionTime.get() > 0;
    }

    public void setPotionTime(int potionTime) {
        this.potionTime.set(potionTime);
    }

    public void drinkPotion() {
        setPotionTime(11);
    }

    public void nextState() {
        if (getPotionTime() > 0) {
            setPotionTime(getPotionTime() - 1);
            System.out.println(getPotionTime() + " invincible rounds remaining");
        }
    }

    private int getPotionTime() {
        return potionTime.get();
    }

    public IntegerProperty getPotionTimeIP() {
        return potionTime;
    }

	public boolean hasPotion() {
		return getPotionTime() > 0;
	}

}