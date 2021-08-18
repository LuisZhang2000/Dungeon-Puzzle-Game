package unsw.dungeon;

import java.util.ArrayList;

public class ANDGoal extends Goal {

    private ArrayList<Goal> listSubGoals;

    /**
     * Creates an ANDGoal
     * 
     * @param dungeon
     */
    public ANDGoal(Dungeon dungeon) {
        super(dungeon);
        setIsLeaf(false);
        listSubGoals = new ArrayList<>(); 
    }

    /**
     * Determine if ANDGoal satisfied by checking 
     * if all subgoals are satisfied
     */
    @Override
    public void checkStatus() {
        boolean checkAllGoal = true;
        for (Goal g : listSubGoals) {
            if (!g.getIsComplete()) {
                checkAllGoal = false;
            }
        }
        if (checkAllGoal) {
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

    public ArrayList<Goal> getListSubGoals() {
        return listSubGoals;
    }
    
    @Override
    public String toString() {
        return "AND Goal";
    }
}
