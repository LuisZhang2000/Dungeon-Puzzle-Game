package unsw.dungeon;

public class Exit extends Entity {

    /**
     * Create an exit positioned in square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.setGoThrough(true);
    }

    @Override
    public void collide(Entity entity) {
        
    }
    
}
