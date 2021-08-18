package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return dungeon
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        JSONObject goalCondition = json.getJSONObject("goal-condition");
        dungeon.setGoal(loadGoal(dungeon, goalCondition));
        return dungeon;

    }

    /**
     * Parses the JSON to create a goal.
     * @return goal
     */
    private Goal loadGoal(Dungeon dungeon, JSONObject goalCondition) {
        String goals = goalCondition.getString("goal");
        Goal goal = null;
        
        switch (goals) {
        case "exit":
            goal = new ExitGoal(dungeon);
            break;   
        case "boulders":
            goal = new BouldersGoal(dungeon);
            break;
        case "enemies":
			goal = new EnemiesGoal(dungeon);
            break;
        case "treasure":
			goal = new TreasureGoal(dungeon);
            break;
        case "AND":
            goal = new ANDGoal(dungeon);
            break;
        case "OR":
            goal = new ORGoal(dungeon);
            break;
        }
        if (!goal.getIsLeaf()) {
            JSONArray subGoals = goalCondition.getJSONArray("subgoals");
			for (int i = 0; i < subGoals.length(); i++) {
				Goal subgoal = loadGoal(dungeon, subGoals.getJSONObject(i));
				goal.addSubGoal(subgoal);
            }
        }
        return goal;
    }

    /**
     * Parses the JSON to create a entity.
     * @return entity
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            dungeon.addEnemy(enemy);
            onLoad(enemy);
            entity = enemy;
            break;
        case "wall":
            Wall wall = new Wall(dungeon, x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "switch":
            Switch switches = new Switch(dungeon, x, y);
            dungeon.addSwitches(switches);
            onLoad(switches);
            entity = switches;
            break;
        // TODO Handle other possible entities
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "portal":
            Portal portal = new Portal(dungeon, x, y, json.getInt("id"));
            onLoad(portal);
            entity = portal;
            break;
        case "door":
            Door door = new Door(dungeon, x, y, json.getInt("id"));
            onLoad(door);
            entity = door;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "key":
            Key key = new Key(dungeon, x, y, json.getInt("id"));
            onLoad(key);
            entity = key;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "invincibility":
            Potion potion = new Potion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Switch switches);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Potion potion);
}
