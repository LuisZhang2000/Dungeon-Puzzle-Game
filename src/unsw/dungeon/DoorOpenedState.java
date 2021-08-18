package unsw.dungeon;

public class DoorOpenedState implements DoorState {

    private Door door;

    public DoorOpenedState(Door door) {
        this.door = door;
    }
    
    @Override
    public boolean getGoThrough() {
        return true;
    }

    @Override
    public void nextState() {
        System.out.println("can not change to next state");
    }

}