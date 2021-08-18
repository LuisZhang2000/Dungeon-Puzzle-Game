package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<Enemy> enemies;
    private List<Enemy> deadEnemies;
    private List<Switch> switches;
    private Inventory inventory;
    private Goal goal;
    private boolean isFinished;

    /**
     * Dungeon Constructor
     * @param width
     * @param height
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.enemies = new ArrayList<>();
        this.deadEnemies = new ArrayList<>();
        this.switches = new ArrayList<>();
        this.inventory = new Inventory(this);
        this.goal = null;
        this.isFinished = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public int getPlayerX() {
		return player.getX();
	}

	public int getPlayerY() {
		return player.getY();
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void addDead(Enemy enemy) {
        deadEnemies.add(enemy);
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public void removeEntity(Entity entity) {
        entity.x().set(getWidth() + 1);
        entity.y().set(getHeight() + 1);
        entities.remove(entity);
    }
    
    public List<Entity> getEntitiesPOS(int x, int y) {
        // Get all entities in position (x,y)
        List <Entity> entitiesPOS = new ArrayList<>();
        for (Entity e : entities) {
            if (e != null && e.getX() == x && e.getY() == y) {
                entitiesPOS.add(e);
            }
        }
        return entitiesPOS;
    }

    public void addSwitches(Switch s) {
        switches.add(s);
    } 

    /**
     * Called by Boulder to check if boulder is triggered
     * 
     * @param x
     * @param y
     * @return true if triggered, vice versa
     */
	public boolean isTriggered(int x, int y) {
        for (Entity e : this.getEntitiesPOS(x, y)) {
            if (e instanceof Boulder) {
                return true;
            }
        }
        return false;
    }
    
    public List<Switch> getSwitches() {
        return this.switches;
    }
    

    /**
     * Given (x,y) position in the dungeon, check if all entity in position (x,y) can be goThrough 
     * and (x,y) not out of bounds
     * 
     * @param x
     * @param y
     * @return true if all entity in position (x,y) can be goThrough 
     * and (x,y) not out of bounds
     */
    public boolean checkAvailable(int x, int y) {
        return getEntitiesPOS(x, y).stream().allMatch(Entity::getGoThrough) 
        && y >= 0 && y < this.getHeight() && x >= 0 && x < this.getWidth();
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Given door, determine whether or not open the door
     * open the door if door is not already open and player got
     * the correct key
     * 
     * @param door
     */
    public void checkDoor(Door door) {
        if (door.getGoThrough()) {
            System.out.println("door is opened");
        } else {
            //if (inventory.getKeyID() == -1) {
            if (inventory.getKey() == null) {
                System.out.println("no key");
            } else if (inventory.getKeyID() != door.getID()) {
                System.out.println("wrong key");
            } else {
                this.getInventory().useKey();
                door.openDoor();
            }
        }
    }

    /**
     * Given Portal portal and its ID, return its pair portal
     * 
     * @param portal
     * @param ID
     * @return Portal pair
     */
	public Portal findPortalPair(Portal portal, int ID) {
        Portal temp;
        Portal pair = portal;
        for (Entity entity : this.entities) {
            if (entity instanceof Portal) {
                temp = (Portal) entity;
                if (temp.getID() == ID && 
                   (temp.getX() != portal.getX() || 
                    temp.getY() != portal.getY())) {
                    pair = temp;
                }  
            }
        } 
        return pair;
    }
    
    /**
     * Given key, determine whether or not to pick up the key
     * pick up if player does not already have a key
     * 
     * @param key
     */
    public void pickUpKey(Key key) {
        if (this.getInventory().pickUpKey(key.getID())) {
            // Set the picked up key to the removed EntitiesPos instead of
            // using removeEntity method for dropped key to work
            key.x().set(getWidth() + 1);
            key.y().set(getHeight() + 1);
            System.out.println("picked up key");
        } else {
            System.out.println("Already have a key");
        }
    }

    public void pickUpTreasure(Treasure treasure) {
        removeEntity(treasure);
        this.getInventory().pickUpTreasure();
    }

    public void pickUpSword(Sword sword) {
        removeEntity(sword);
        this.getInventory().pickUpSword();
    }

    public void pickUpPotion(Potion potion) {
        removeEntity(potion);
        this.getInventory().pickUpPotion();
    }

	public boolean isInvincible() {
		return inventory.isInvincible();
	}

    public boolean hasKey() {
		return inventory.hasKey();
    }
    
	public boolean hasSword() {
		return inventory.hasSword();
	}

    public boolean hasPotion() {
		return inventory.hasPotion();
    }
    
	public void swordNext() {
        // Decrements the swords durability
        inventory.swordNext();
    }
    
    /**
     * Every time the player move, this method calls and 
     * moves the player and reduce the invincibility time if 
     * the player is invincible. This also checks if all switches is 
     * activated and the status of the overall Goal.
     */
	public void newRound() {
        // Update invincibility time
        if (inventory.getPotion() != null) {
            inventory.potionNext();
        }
        
        for (Enemy e : enemies) {
            e.move(player, isInvincible());
        }
        // Avoid concurrent modification exception
        enemies.removeAll(deadEnemies);
        
        for (Switch s : switches) {
            // check if all switches triggered each round
            s.isTriggered();
        }
        if (goalStatus() == true) {
            finishGame();
        }
    }

    public Goal getGoal() {
		return goal;
	}

    public void setGoal(Goal goal) {
        this.goal = goal;
	}

    public boolean goalStatus() {
        return goal.getIsComplete();
    }

    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Check if every other goal condition is met except the exit one
     * in an ANDGoal condition
     * @return true if every other goal condition is met except the exit one, vice versa
     */
	public boolean goalExceptExit() {
        boolean checkExceptExit = true;
        if (goal instanceof ANDGoal) {
            ANDGoal and = (ANDGoal) goal;
            ArrayList<Goal> subGoals = and.getListSubGoals();
            for (Goal g : subGoals) {
                if (!(g instanceof ExitGoal) && !g.getIsComplete()) {
                    checkExceptExit = false;
                }
            }
        }
		return checkExceptExit;
    }
    
    public void finishGame() {
        System.out.println("GAME OVER");
        System.exit(0);
    }

    public int numTreasure() {
        int TreasureCount = 0;
        for (Entity entity : entities) {
            if (entity instanceof Treasure) {
                TreasureCount++;
            }
        }
        return TreasureCount;
    }

	public int numEnemies() {
		int EnemiesCount = 0;
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                EnemiesCount++;
            }
        }
        return EnemiesCount;
	}
	
}
