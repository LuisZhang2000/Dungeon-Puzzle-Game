package unsw.dungeon;

public class Wall extends Entity {

    /**
     * Creates a wall positioned at (x,y)
     * @param duneon
     * @param x
     * @param y
     */
    public Wall(Dungeon duneon, int x, int y) {
        super(x, y);
        this.setGoThrough(false);
    }

    @Override
    public void collide(Entity entity) {
        // TODO Auto-generated method stub

    }
}
