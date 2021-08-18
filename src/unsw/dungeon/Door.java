package unsw.dungeon;

public class Door extends Entity {

    private int ID;
    private Dungeon dungeon;
    private DoorState doorOpened;
    private DoorState doorClosed;

    private DoorState doorState;

    /**
     * Create a door positioned in square (x,y) with door id ID
     * 
     * @param dungeon
     * @param x
     * @param y
     * @param ID
     */
    public Door(Dungeon dungeon, int x, int y, int ID) {
        super(x, y);
        this.ID = ID;
        this.dungeon = dungeon;
        this.setGoThrough(false);
        
        doorOpened = new DoorOpenedState(this);
        doorClosed = new DoorClosedState(this);

        doorState = doorClosed;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
        this.setGoThrough(doorState.getGoThrough());
    }

    public int getID() {
		return ID;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }

    public void openDoor() {
        doorState.nextState();
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            this.dungeon.checkDoor(this);
        }
    }

}
