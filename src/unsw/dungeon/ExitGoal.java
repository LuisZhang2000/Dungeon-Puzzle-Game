package unsw.dungeon;

import java.util.List;

public class ExitGoal extends Goal {

    /**
     * Creates an ExitGoal
     * 
     * @param dungeon
     */
    public ExitGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(true);
    }

    @Override
    public void checkStatus() {
        Player p = getDungeon().getPlayer();
        if (p == null) {
            setIsComplete(false);
        } else {
            List<Entity> entitiesPOS = getDungeon().getEntitiesPOS(p.getX(), p.getY());
            if (entitiesPOS.stream().anyMatch(e -> e instanceof Exit) && getDungeon().goalExceptExit()) {
                setIsComplete(true);
            }
        }
    }

    @Override
    public String toString() {
        return "Get to the Exit";
    }

}