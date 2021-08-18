package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import unsw.dungeon.*;

public class interactionTest {

    private Dungeon dungeon;
	private Player player;
	private Goal goal;
	private Inventory inventory;
    
    @BeforeEach
	public void initialise() {
		dungeon = new Dungeon(5, 5);
		player = new Player(dungeon, 1, 1);
		goal = new ExitGoal(dungeon);
		inventory = new Inventory(dungeon);
		dungeon.setGoal(goal);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
	}

	@Test
	public void testPickUp() {
        dungeon.addEntity(new Sword(dungeon, 2, 1));
        dungeon.addEntity(new Sword(dungeon, 2, 2));
		assertFalse(dungeon.hasSword());
		player.move(Direction.RIGHT);
		// Player move to Sword1, Sword1 picked up
		assert(dungeon.hasSword());
		player.move(Direction.DOWN);
		// Sword2 not picked up, not removed from dungeon
		assert(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Sword));

		// Similarly for potion
		dungeon.addEntity(new Potion(dungeon, 3, 2));
        dungeon.addEntity(new Potion(dungeon, 3, 3));
		assertFalse(dungeon.hasPotion());
		player.move(Direction.RIGHT);
		assert(dungeon.hasPotion());
		player.move(Direction.DOWN);
		assert(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Potion));
	
		// Similarly for Key
		dungeon.addEntity(new Key(dungeon, 4, 3, 1));
        dungeon.addEntity(new Key(dungeon, 4, 4, 2));
		assertFalse(dungeon.hasKey());
		player.move(Direction.RIGHT);
		assert(dungeon.hasKey());
		player.move(Direction.DOWN);
		assert(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Key));

		// Similarly for Treasure
		dungeon.addEntity(new Treasure(dungeon, 3, 4));
		dungeon.addEntity(new Treasure(dungeon, 2, 4));
		assert(dungeon.getEntitiesPOS(3,4).stream().anyMatch(e -> e instanceof Treasure));
		assert(dungeon.getEntitiesPOS(2,4).stream().anyMatch(e -> e instanceof Treasure));
		// Player picked up Treasure1 on (3,4)
		player.move(Direction.LEFT);
		assert(dungeon.numTreasure() == 1);
		assertFalse(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Treasure));
		// Player picked up Treasure2 on (2,3)
		player.move(Direction.LEFT);
		assert(dungeon.numTreasure() == 0);
		assertFalse(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Treasure));
	}

	@Test
	public void testDropKey() {
		dungeon.addEntity(new Key(dungeon, 2, 1, 1));
		assertFalse(dungeon.hasKey());
		// No keys to be dropped
		inventory.dropKey();
		
		player.move(Direction.RIGHT);
		assert(dungeon.hasKey());
		// key dropped
		inventory.dropKey();
		// check if dropped key on the player's current tile
		assert(dungeon.getEntitiesPOS(player.getX(), player.getY()).stream().anyMatch(e -> e instanceof Key));
	}

	@Test
	public void testIncorrectKey() {
		dungeon.addEntity(new Key(dungeon, 2, 1, 2));
		dungeon.addEntity(new Door(dungeon, 4, 1, 1));

		// Obtain key with id 2
		player.move(Direction.RIGHT);
		player.move(Direction.RIGHT);
		// Try to open the door but fail
		player.move(Direction.RIGHT);
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
	}

	@Test
	public void testCorrectKey() {
		dungeon.addEntity(new Key(dungeon, 2, 1, 1));
		dungeon.addEntity(new Door(dungeon, 4, 1, 1));

		// Obtain key with id 1
		player.move(Direction.RIGHT);
		player.move(Direction.RIGHT);
		// Open the door with key
		player.move(Direction.RIGHT);
		player.move(Direction.RIGHT);
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 1);
	}

	@Test
	public void testPushBoulder() {
		// Boulder on the Right 
		Boulder b = new Boulder(dungeon, 2, 1);
		dungeon.addEntity(b);
		dungeon.addEntity(new Wall(dungeon, 4, 1));
		// Player push the boulder
		player.move(Direction.RIGHT);
		player.move(Direction.RIGHT);
		assertEquals(b.getX(), 3);
		assertEquals(b.getY(), 1);

		player.move(Direction.RIGHT);
		// Player push the boulder again but fail due to the wall
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		assertEquals(b.getX(), 3);
		assertEquals(b.getY(), 1);
	}

}
		