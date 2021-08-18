package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import unsw.dungeon.*;

public class MovementTest {

    private Dungeon dungeon;
	private Player player;
	private Goal goal;
	private Treasure treasure;
	
    @BeforeEach
	public void initialise() {
		dungeon = new Dungeon(5, 5);
		player = new Player(dungeon, 1, 1);
		goal = new TreasureGoal(dungeon);
		treasure = new Treasure(dungeon, 3, 3);
		dungeon.setGoal(goal);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(treasure);
	}

	@Test
	public void testMovement() {
		
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.LEFT);
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 1);

		player.move(Direction.RIGHT);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.UP);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 0);

		player.move(Direction.DOWN);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
	}

	@Test
	public void testObstacleMovement() {

		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
		
		// Wall above player
		dungeon.addEntity(new Wall(dungeon, 1, 0));
		// Wall below player
		dungeon.addEntity(new Wall(dungeon, 1, 2));
		// Boulder on the left 
		dungeon.addEntity(new Boulder(dungeon, 0, 1));
		// Door on the right
		dungeon.addEntity(new Door(dungeon, 2, 1, 1));

		player.move(Direction.UP);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.DOWN);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.RIGHT);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.LEFT);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
	}

	@Test
	public void testOutOfBoundsMovement() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.move(Direction.LEFT);
		player.move(Direction.UP);

		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 0);
		
		player.move(Direction.UP);
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 0);

		player.move(Direction.LEFT);
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 0);
	}
	
	@Test
	public void testPortalPlayer() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		dungeon.addEntity(new Portal(dungeon, 2, 1, 0)); 
		dungeon.addEntity(new Portal(dungeon, 4, 4, 0));
		dungeon.addEntity(new Portal(dungeon, 4, 5, 1)); 
		dungeon.addEntity(new Portal(dungeon, 2, 2, 1));
		
		// Player move to the portal with id 0
		player.move(Direction.RIGHT);
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 4);
		// Player move to the portal with id 1
		player.move(Direction.DOWN);
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
	}

}