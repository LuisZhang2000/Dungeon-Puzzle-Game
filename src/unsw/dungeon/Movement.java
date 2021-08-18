package unsw.dungeon;

import java.util.List;

public class Movement implements Movable {

    private Dungeon dungeon;

    public Movement(Dungeon dungeon) {
        this.dungeon = dungeon;
	}

    /**
     * Given Entity movable, move the entity to Direction direction
     * and check collision in the new postion(move success or not)
     * 
     * @param direction
     * @param movable
     */
	@Override
    public void move(Direction direction, Entity movable) {
        // Get next position of the movable entity
        int nextX = direction.nextX(movable.getX());
        int nextY = direction.nextY(movable.getY());
        
        // if next position is walkable, set it as new positon of movable
        if (dungeon.checkAvailable(nextX, nextY)) {
            movable.x().set(nextX);
            movable.y().set(nextY);
        }
        // check collision in next position
        notifyCollide(movable, nextX, nextY);
    }

    /**
     * Get all entities in position (x,y) and notify collision between them
     * 
     * @param movable
     * @param x
     * @param y
     */
    private void notifyCollide(Entity movable, int x, int y) {
        // Get all entities in position (x,y)
        List<Entity> entitiesPOS = dungeon.getEntitiesPOS(x, y);
        for (Entity e : entitiesPOS) {
            movable.collide(e);
            e.collide(movable);
        }
    }

}