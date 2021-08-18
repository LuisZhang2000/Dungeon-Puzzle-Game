package unsw.dungeon;

public class Potion extends Entity {
    
    private Dungeon dungeon;

    /**
     * Create a invincibility potion positioned in square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Potion(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(true);
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            if (!dungeon.hasPotion()) {
                dungeon.pickUpPotion(this);
                System.out.println("picked up Invincibility potion");
            }
        }
    }
}