package unsw.dungeon;

public class Boulder extends Entity {

    private Movement movement;
    private Dungeon dungeon;

    /**
     * Create a boulder positioned in square (x,y)
     * 
     * @param dungeon
     * @param x
     * @param y
     */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.setGoThrough(false);
        this.movement = new Movement(dungeon);
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            if (entity.getX() > this.getX()) {
                // Player is on the right, boulder pushed left
                movement.move(Direction.LEFT, this);
            } else if (entity.getX() < this.getX()) {
                // Player is on the left, boulder pushed right
                movement.move(Direction.RIGHT, this);
            } else if (entity.getY() > this.getY()) {
                // Player is below, boulder pushed up
                movement.move(Direction.UP, this);
            } else if (entity.getY() < this.getY()){
                // Player is on top, boulder pushed down
                movement.move(Direction.DOWN, this);
            }
        }
    }
}