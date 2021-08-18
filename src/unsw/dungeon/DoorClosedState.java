package unsw.dungeon;

public class DoorClosedState implements DoorState {
    
    private Door door;

    public DoorClosedState(Door door) {
        this.door = door;
    }

    @Override
    public boolean getGoThrough() {
        return false;
    }

    @Override
    public void nextState() {
        door.setDoorState(new DoorOpenedState(door));
    }
}
