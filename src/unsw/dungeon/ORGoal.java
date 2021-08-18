package unsw.dungeon;

import java.util.ArrayList;

public class ORGoal extends Goal {

    private ArrayList<Goal> listSubGoals;

    /**
     * Creates an ORGoal
     * 
     * @param dungeon
     */
    public ORGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(false);
        listSubGoals = new ArrayList<>(); 
    }

    public ArrayList<Goal> getListSubGoals() {
        return listSubGoals;
    }
    
    /**
     * Determine if ORGoal satisfied by checking 
     * if one of the subgoals is satisfied
     */
    @Override
    public void checkStatus() {
        boolean checkOneGoal = false;
        for (Goal g : listSubGoals) {
            if (g.getIsComplete()) {
                checkOneGoal = true;
            }
        }
        if (checkOneGoal) {
            setIsComplete(true);
        }

    }

    @Override
    public void addSubGoal(Goal goal) {
        listSubGoals.add(goal);
    }
    
    @Override
    public void removeSubGoal(Goal goal) {
        listSubGoals.remove(goal);
    }

    @Override
    public String toString() {
        return "OR Goal";
    }
}
