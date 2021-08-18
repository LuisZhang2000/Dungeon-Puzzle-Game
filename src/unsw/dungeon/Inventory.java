package unsw.dungeon;

import javafx.beans.property.IntegerProperty;

public class Inventory {

    private KeyInventory keyInv;
    private TreasureInventory treasureInv;
    private SwordInventory swordInv;
    private PotionInventory potionInv;
    private Dungeon dungeon;

    /**
     * Create the Inventory
     * 
     * @param dungeon
     */
    public Inventory(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.keyInv = new KeyInventory(dungeon);
        this.treasureInv = new TreasureInventory();
        this.swordInv = new SwordInventory();
        this.potionInv = new PotionInventory();
    }

    public KeyInventory getKey() {
        return this.keyInv;
    }

    public int getKeyID() {
        return this.keyInv.getKeyID();
    }

    public boolean pickUpKey(int ID) {
        return this.keyInv.pickUpKey(ID);
    }

    public void useKey() {
        this.keyInv.useKey();
    }
    
    public IntegerProperty keyIDProperty() {
		return keyInv.keyIDProperty();
	}
	
    public void pickUpTreasure() {
		this.treasureInv.pickUpTreasure();
    }
    
    public TreasureInventory getTreasureInventory() {
		return this.treasureInv;
	}

    public void pickUpPotion() {
        this.potionInv.drinkPotion();
    }
    
    public void potionNext() {
        this.potionInv.nextState();
    }
    
    public boolean hasPotion() {
		return this.potionInv.hasPotion();
	}
	
    public PotionInventory getPotion() {
        return this.potionInv;
    }

    public boolean isInvincible() {
        return this.potionInv.isInvincible();
    }
    
    public SwordInventory getSwordInventory() {
        return this.swordInv;
    }

    public void pickUpSword() {
        this.swordInv.pickUpSword();
    }

    public boolean hasSword() {
        return this.swordInv.hasSword();
    }
    
    public boolean hasKey() {
		return (this.keyInv.getKeyID() != -1);
	}
    
    public void dropKey() {
        this.keyInv.dropKey();
	}
	public void swordNext() {
        this.swordInv.nextState();
	}

	

	

	

	
}
