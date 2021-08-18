package unsw.dungeon;

public class TreasureGoal extends Goal {

    /**
     * Creates a Treasure Goal
     *
     * @param dungeon
     */
    public TreasureGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(true);
	}

    @Override
    public void checkStatus() {
        if (getDungeon().numTreasure() == 0) {
            setIsComplete(true);
        }
    }

    @Override
    public String toString() {
        return "Get all treasures";
    }
}
