package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Provides common methods shared by different types of goals
 */
public abstract class Goal implements GoalComposite {
    
    private Dungeon dungeon;
    private boolean isComplete;
    private BooleanProperty isCompleteBP;
    private boolean isLeaf;

    public Goal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.isComplete = false;
        this.isCompleteBP = new SimpleBooleanProperty();
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public boolean getIsComplete() {
        checkStatus();
        return isComplete;
    }

    public BooleanProperty getIsCompleteBP() {
        return this.isCompleteBP;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
        this.isCompleteBP.set(true);
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void addSubGoal(Goal goal) {}

    public void removeSubGoal(Goal goal) {}

}