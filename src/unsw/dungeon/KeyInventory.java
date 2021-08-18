package unsw.dungeon;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

import javafx.beans.property.IntegerProperty;

public class KeyInventory {
    
    private int noKey = -1;
    private IntegerProperty keyID;
    private Dungeon dungeon;
    private int ID;

    /**
     * Creates a KeyInventory
     * 
     * @param dungeon
     */
    public KeyInventory(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.keyID = new SimpleIntegerProperty(noKey);
    }

    public int getKeyID() {
		return keyID.get();
    }

    public boolean pickUpKey(int ID) {
        if (getKeyID() == noKey) {
            this.keyID.set(ID);
            setID(ID);
            return true;
        }
        return false;
    }

    public void useKey() {
        this.keyID.set(noKey);
    }

    public IntegerProperty keyIDProperty() {
		return keyID;
	}

    /**
     * Call after the user press G key, drop the key player picked up(if any)
     * and re-add that key to the tile the player is on
     */
	public void dropKey() {
        if (dungeon.hasKey()) {
            System.out.println("Key dropped");
            keyID.set(noKey);
            int playerX = dungeon.getPlayerX();
            int playerY = dungeon.getPlayerY();
            int KeyPosX = dungeon.getWidth() + 1;
            int KeyPosY = dungeon.getHeight() + 1;
            List <Entity> removedEntities = dungeon.getEntitiesPOS(KeyPosX, KeyPosY);
            // Find the key in the removedEntities
            for (Entity e : removedEntities) {
                if (e instanceof Key) {
                    Key dropped = (Key)e;
                    if (dropped.getID() == getID()) {
                        dropped.x().set(playerX);
                        dropped.y().set(playerY);
                        dungeon.addEntity(dropped);
                    }
                }
            }
        } else {
            System.out.println("No key to be dropped");
        }
	}

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }
    
}
