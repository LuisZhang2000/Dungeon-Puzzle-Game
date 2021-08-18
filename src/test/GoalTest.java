package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import unsw.dungeon.*;

public class GoalTest {

    private Dungeon dungeon;
    private Player player;
    private Goal goal;
    private Treasure treasure;
    private Switch switch1;
    private Boulder boulder;
    private Exit exit;
    
    @BeforeEach
	public void initialise() {
		dungeon = new Dungeon(6, 6);
        player = new Player(dungeon, 1, 1);
        exit = new Exit(dungeon, 5, 5);
        
        dungeon.setPlayer(player);
		dungeon.addEntity(player);
        dungeon.addEntity(exit);   

    }

    @Test
    public void testBoulders() {
        // Boulder on the Right 
        boulder = new Boulder(dungeon, 2, 1);
        dungeon.addEntity(boulder);
        switch1 = new Switch(dungeon, 3, 1);
        dungeon.addSwitches(switch1);
        dungeon.addEntity(switch1);

        Boulder boulder1 = new Boulder(dungeon, 2, 2);
        dungeon.addEntity(boulder1);
        Switch switch2 = new Switch(dungeon, 2, 3);
        dungeon.addSwitches(switch2);
        dungeon.addEntity(switch2);

        goal = new BouldersGoal(dungeon);
        dungeon.setGoal(goal);
        // Push boulder to switch1
        player.move(Direction.RIGHT);
        // Player move to switch2
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        // check that player cannot trigger the switch
        assertFalse(dungeon.goalStatus());
        // Push boulder off switch1
        player.move(Direction.LEFT);
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // Push boulder1 to switch2
        player.move(Direction.LEFT);
        player.move(Direction.DOWN);
        // Check if switch can be untriggered
        assertFalse(dungeon.goalStatus());
        // Push boulder back to switch1
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.UP);
        player.move(Direction.LEFT);
        // both switches triggered
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }

    @Test
    public void testEnemies() {
        Enemy enemy1 = new Enemy(dungeon, 3, 1);
        Enemy enemy2 = new Enemy(dungeon, 4, 1);
        Enemy enemy3 = new Enemy(dungeon, 3, 2);
        
        dungeon.addEntity(new Sword(dungeon, 2, 1));
        dungeon.addEnemy(enemy1);
        dungeon.addEntity(enemy1);
        dungeon.addEnemy(enemy2);
        dungeon.addEntity(enemy2);
        dungeon.addEnemy(enemy3);
        dungeon.addEntity(enemy3);

        goal = new EnemiesGoal(dungeon);
        dungeon.setGoal(goal);

        // Kill all enemies with sword
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }

    @Test
    public void testExit() { 
        goal = new ExitGoal(dungeon);
        dungeon.setGoal(goal);

        // Player moving to exit
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }

    @Test
    public void testTreasure() {
        treasure = new Treasure(dungeon, 2, 1);
        Treasure treasure1 = new Treasure (dungeon, 4, 1);
        dungeon.addEntity(treasure);
        dungeon.addEntity(treasure1);

        goal = new TreasureGoal(dungeon);
        dungeon.setGoal(goal);

        // Player moving to collect the treasures
        player.move(Direction.RIGHT);
        assertFalse(dungeon.goalStatus());
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }

    @Test
    public void testORTreasure() {
        treasure = new Treasure(dungeon, 2, 1);
        Treasure treasure1 = new Treasure (dungeon, 4, 1);
        dungeon.addEntity(treasure);
        dungeon.addEntity(treasure1);

        goal = new ORGoal(dungeon);
        Goal subGoal1 = new TreasureGoal(dungeon);
        Goal subGoal2 = new ExitGoal(dungeon);
        goal.addSubGoal(subGoal1);
        goal.addSubGoal(subGoal2);
        dungeon.setGoal(goal);

        // Player moving to collect the treasures
        player.move(Direction.RIGHT);
        assertFalse(dungeon.goalStatus());
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }
    
    @Test
    public void testORExit() {
        treasure = new Treasure(dungeon, 2, 1);
        Treasure treasure1 = new Treasure (dungeon, 4, 1);
        dungeon.addEntity(treasure);
        dungeon.addEntity(treasure1);

        goal = new ORGoal(dungeon);
        Goal subGoal1 = new TreasureGoal(dungeon);
        Goal subGoal2 = new ExitGoal(dungeon);
        goal.addSubGoal(subGoal1);
        goal.addSubGoal(subGoal2);
        dungeon.setGoal(goal);

        // Player moving to the Exit
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }
    
    @Test
    public void testAND() {
        treasure = new Treasure(dungeon, 5, 1);
        Treasure treasure1 = new Treasure (dungeon, 5, 4);
        dungeon.addEntity(treasure);
        dungeon.addEntity(treasure1);

        goal = new ANDGoal(dungeon);
        Goal subGoal1 = new TreasureGoal(dungeon);
        Goal subGoal2 = new ExitGoal(dungeon);
        goal.addSubGoal(subGoal1);
        goal.addSubGoal(subGoal2);
        dungeon.setGoal(goal);

        // Player collecting the treasures and moving to the Exit
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // Check if the exit Goal must be excute last rule is valid
        assertFalse(subGoal1.getIsComplete());
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        assert(dungeon.goalStatus());
        assert(dungeon.isFinished());
    }
}