package unsw.dungeon;

public class BouldersGoal extends Goal {

    /**
     * Creates a BouldersGoal
     * 
     * @param dungeon
     */
	public BouldersGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(true);
	}

    @Override
    public void checkStatus() {
        // TODO Auto-generated method stub
        boolean checkAllTriggered = true;
        for (Switch s : getDungeon().getSwitches()) {
            if (s.getTriggered() == false) {
                checkAllTriggered = false;
            }
        }
        if (checkAllTriggered) {
            setIsComplete(true);
        }
    }

    @Override
    public String toString() {
        return "Trigger all switches";
    }
}
