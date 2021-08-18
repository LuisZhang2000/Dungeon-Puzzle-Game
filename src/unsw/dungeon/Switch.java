package unsw.dungeon;

public class Switch extends Entity {

    private boolean triggered;
    private Dungeon dungeon;

    /**
     * Creates a switch positioned at square (x,y)
     * 
     * @param dungeon
     * @param x
     * @param y
     */
    public Switch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(true);
        this.triggered = false;
    }

    @Override
    public void collide(Entity entity) {

    }


    public void isTriggered() {
        this.triggered = dungeon.isTriggered(getX(), getY());
    }

    public boolean getTriggered() {
        return triggered;
    }
 
    
}