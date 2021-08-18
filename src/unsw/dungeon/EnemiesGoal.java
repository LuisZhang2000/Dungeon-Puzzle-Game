package unsw.dungeon;

public class EnemiesGoal extends Goal {

    /**
     * Creates an EnemiesGoal
     * 
     * @param dungeon
     */
	public EnemiesGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(true);
	}

    @Override
    public void checkStatus() {
        if (getDungeon().numEnemies() == 0) {
            setIsComplete(true);
        }
    }

    @Override
    public String toString() {
        return "Kill all enemies";
    }
}
