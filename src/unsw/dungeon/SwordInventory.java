package unsw.dungeon;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class SwordInventory {

    private IntegerProperty durability;

    /**
     * Creates a SwordInventory
     */
    public SwordInventory() {
        durability = new SimpleIntegerProperty(0);
    }

    public int getDurability() {
        return durability.get();
    }

    public IntegerProperty getDurabilityIP() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability.set(durability);
    }

    public void pickUpSword() {
        setDurability(5);
    }
    
    public void nextState() {
        if (getDurability() > 0) {
            setDurability(getDurability() - 1);
            System.out.println("The durability of the sword is " + getDurability());
        }
    }

	public boolean hasSword() {
		return getDurability() > 0;
	}
}