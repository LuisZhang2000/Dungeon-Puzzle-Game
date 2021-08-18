package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {
    
    private Dungeon dungeon;
    private Movement movement;

    /**
     * Create a player positioned in square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(false);
        this.movement = new Movement(dungeon);
    }

    public void move(Direction direction) {
        movement.move(direction, this);
        dungeon.newRound();
    }

    @Override
    public void collide(Entity e) {

    }

    public boolean isInvincible() {
        return dungeon.isInvincible();
    }

	public boolean hasSword() {
		return dungeon.hasSword();
	}     
}
