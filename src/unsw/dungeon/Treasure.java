package unsw.dungeon;

public class Treasure extends Entity {
    
    private Dungeon dungeon;

    /**
     * Creates a treasure positioned at (x,y)
     * 
     * @param dungeon
     * @param x
     * @param y
     */
    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(true);
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            this.dungeon.pickUpTreasure(this);
        }
    }
}