package unsw.dungeon;

public class Sword extends Entity {

    private Dungeon dungeon;

    /**
     * Create a sword positioned at (x,y)
     * 
     * @param dungeon
     * @param x
     * @param y
     */
    public Sword(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(true);
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            if (!dungeon.hasSword()) {
                dungeon.pickUpSword(this);
                System.out.println("picked up sword");
            } else {
                System.out.println("Already have sword");
            }
        }
    }
}