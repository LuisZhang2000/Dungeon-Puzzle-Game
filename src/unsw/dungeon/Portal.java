package unsw.dungeon;

public class Portal extends Entity {

    private int ID;
    private Dungeon dungeon;

    /**
     * Create a portal positioned in square (x,y) with ID
     * 
     * @param dungeon
     * @param x
     * @param y
     * @param ID
     */
    public Portal(Dungeon dungeon, int x, int y, int ID) {
        super(x, y);
        this.dungeon = dungeon;
        this.ID = ID;
        this.setGoThrough(true);
    }

	public Portal findPortalPair(Portal portal, int ID) {
        Portal pair = dungeon.findPortalPair(portal, ID);
        return pair;
    }
    
    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player || (entity instanceof Enemy)) {
            // teleport the player or Enemy
            Portal pair = findPortalPair(this, this.ID);
            entity.x().set(pair.getX());
            entity.y().set(pair.getY());
        }
    }

    public int getID() {
        return ID;
    }

}