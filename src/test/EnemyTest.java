package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import unsw.dungeon.*;

public class EnemyTest {

    private Dungeon dungeon;
    private Player player;
    private Enemy enemy;
	private Goal goal;
    
    @BeforeEach
	public void initialise() {
		dungeon = new Dungeon(5, 5);
        player = new Player(dungeon, 1, 1);
        enemy = new Enemy(dungeon, 4, 2);
        goal = new ExitGoal(dungeon);
        
        dungeon.setGoal(goal);
        dungeon.setPlayer(player);
        dungeon.addEnemy(enemy);
		dungeon.addEntity(player);
        dungeon.addEntity(enemy);
    }
    
    @Test
	public void testNormalMovement() {
        Enemy enemy1 = new Enemy(dungeon, 0, 0);
		dungeon.addEnemy(enemy1);
        dungeon.addEntity(enemy1);

        Enemy enemy2 = new Enemy(dungeon, 1, 3);
		dungeon.addEnemy(enemy2);
        dungeon.addEntity(enemy2);

        Enemy enemy3 = new Enemy(dungeon, 2, 0);
		dungeon.addEnemy(enemy3);
        dungeon.addEntity(enemy3);
        
        player.move(Direction.RIGHT);
        // Players position (2,1), dx = 2, dy = 1, enemy will move left
        assertEquals(enemy.getX(), 3);
        assertEquals(enemy.getY(), 2);
        // Players position (2,1), dx = 2, dy = 1, enemy1 will move right
        assertEquals(enemy1.getX(), 1);
        assertEquals(enemy1.getY(), 0);
        // Players position (2,1), dx = 1, dy = 2, enemy2 will move up
        assertEquals(enemy2.getX(), 1);
        assertEquals(enemy2.getY(), 2);
        // Players position (2,1), dx = 0, dy = 1, enemy3 will move down
        // which will collide with player and thus ending the game, enemy stayed
        // at original position
        assertEquals(enemy3.getX(), 2);
        assertEquals(enemy3.getY(), 0);
        assert(dungeon.isFinished());

    }

    @Test
    public void testInvincibleMovement() {
        dungeon.addEntity(new Potion(dungeon, 2, 1));
        
        Enemy enemy1 = new Enemy(dungeon, 1, 0);
		dungeon.addEnemy(enemy1);
        dungeon.addEntity(enemy1);

        player.move(Direction.RIGHT);
        // Players position (2,1), obtain potion
        // dx = 2, dy = 1, enemy will move down
        assertEquals(enemy.getX(), 4);
        assertEquals(enemy.getY(), 3);
        // Players position (2,1), obtain potion
        // dx = 1, dy = 1, enemy1 will move up, but stuck on the corner
        assertEquals(enemy1.getX(), 1);
        assertEquals(enemy1.getY(), 0);
        player.move(Direction.UP);
        // Players position (2,0), dx = 2, dy = 3, enemy will move right
        // but since it has already reach the limits of the dungeon, it cannot move
        assertEquals(enemy.getX(), 4);
        assertEquals(enemy.getY(), 3);
        // Players position (2,0), dx = 1, dy = 0, enemy1 will move down
        assertEquals(enemy1.getX(), 1);
        assertEquals(enemy1.getY(), 1);
        
    }

    @Test
    public void fightPotion() {
        dungeon.addEntity(new Potion(dungeon, 2, 1));
        // Player chasing the enemy while the enemy is stuck
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // check if enemy's dead
        assert(dungeon.numEnemies() == 0);
    }

    @Test
    public void fightSword() {
        dungeon.addEntity(new Sword(dungeon, 2, 1));
        // Below are the same movement as testInvincibleMovement
        player.move(Direction.RIGHT);
        player.move(Direction.UP);
        assert(dungeon.numEnemies() == 1);
        player.move(Direction.DOWN);
        // check if enemy's dead
        assert(dungeon.numEnemies() == 0);
    }

    @Test
    public void fightSwordMultiple() {
        dungeon.addEntity(new Sword(dungeon, 2, 1));
        Enemy enemy1 = new Enemy(dungeon, 3, 1);
        Enemy enemy2 = new Enemy(dungeon, 4, 1);
        Enemy enemy3 = new Enemy(dungeon, 3, 2);
        Enemy enemy4 = new Enemy(dungeon, 4, 3);
        Enemy enemy5 = new Enemy(dungeon, 4, 4);
        dungeon.addEnemy(enemy1);
        dungeon.addEntity(enemy1);
        dungeon.addEnemy(enemy2);
        dungeon.addEntity(enemy2);
        dungeon.addEnemy(enemy3);
        dungeon.addEntity(enemy3);
        dungeon.addEnemy(enemy4);
        dungeon.addEntity(enemy4);
        dungeon.addEnemy(enemy5);
        dungeon.addEntity(enemy5);
        // Moving to kill enemies
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        // Killing 5 enemies with sword, sword durability 0
        assertFalse(dungeon.hasSword());

    }

    @Test
	public void testPortalEnemy() {

		dungeon.addEntity(new Portal(dungeon, 3, 3, 0)); 
		dungeon.addEntity(new Portal(dungeon, 1, 3, 0));
		Enemy enemy1 = new Enemy(dungeon, 1, 4);
		dungeon.addEnemy(enemy1);
		dungeon.addEntity(enemy1);
		
		player.move(Direction.UP);
		// Players position (1,0), dx = 0, dy = 4, Enemy will move up
		// to (1,3), since (1,3) is a portal, enemy is teleported to (3,3)
		assertEquals(enemy1.getX(), 3);
		assertEquals(enemy1.getY(), 3);
	}
}