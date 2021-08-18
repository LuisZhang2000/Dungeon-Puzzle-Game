package unsw.dungeon;

public class Enemy extends Entity {

    private Dungeon dungeon;
    private Movement movement;
    private Player player;

    /**
     * Create a enemy positioned in square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.movement = new Movement(dungeon);
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player) {
            System.out.println("Fight");
            player = (Player) entity;
            fight(player);
        }
    }

    /**
     * Determine the result of the fight between the enemy and the player
     * if player is invincible or have a sword, the enemy is destroyed, else 
     * the player is destroyed and the game is over
     * 
     * @param player
     */
    private void fight(Player player) {
        if (player.isInvincible() == true) {
            System.out.println("You are invincible, you killed an enemy");
            dungeon.removeEntity(this);
            dungeon.addDead(this);
        } else if (player.hasSword() == true) {
            System.out.println("You killed an enemy by sword");
            dungeon.removeEntity(this);
            dungeon.addDead(this);
            dungeon.swordNext();
        } else {
            dungeon.setPlayer(null);
            dungeon.finishGame();
        }
    }

    public void move(Player player, boolean isInvincible) {
        Direction direction = findDirection(player, isInvincible);
        movement.move(direction, this);
    }

    /**
     * Determine the direction the enemy will move
     * if player is invincible, the enemy will move away from the player, else 
     * the enemy will move towards the player. 
     * This will also decide whether the enemy move vertically or 
     * horizontally depending on the horizontal and vertical distance 
     * between the player and the enemy
     *  
 
     * @param player
     * @param isInvincible
     */
    private Direction findDirection(Player player, boolean isInvincible) {
        Direction direction;
        int dx = abs(player.getX() - this.getX());
        int dy = abs(player.getY() - this.getY());

        if (dx >= dy) {
            if (player.getX() > this.getX() && isInvincible == false) {
                direction = Direction.RIGHT;
            } else if (player.getX() < this.getX() && isInvincible == false) {
                direction = Direction.LEFT;
            } else if (player.getY() > this.getY() && isInvincible == true) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN; 
            }
        } else {
            if (player.getY() > this.getY() && isInvincible == false) {
                direction = Direction.DOWN;
            } else if (player.getY() < this.getY() && isInvincible == false) {
                direction = Direction.UP;
            } else if (player.getX() > this.getX() && isInvincible == true) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }    
        }
        return direction;
    }

    private int abs(int i) {
        if (i < 0) {
            return i * -1;
        }
        return i;
    }
    
}