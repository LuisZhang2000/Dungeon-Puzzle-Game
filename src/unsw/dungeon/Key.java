package unsw.dungeon;

public class Key extends Entity {

    private int ID;
    private Dungeon dungeon;

    public Key(Dungeon dungeon, int x, int y, int ID) {
        super(x, y);
        this.dungeon = dungeon;
        this.ID = ID;
        this.setGoThrough(true);
    }

	public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            System.out.println("Found a key");
            this.dungeon.pickUpKey(this);
        }

    }

}