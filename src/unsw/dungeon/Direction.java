package unsw.dungeon;

public enum Direction {

    // Define directions
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    
    private int dx; 
    private int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // calculate the new X and Y
    public int nextX(int x) {
        return x + dx;
    }

    public int nextY(int y) {
        return y + dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }    
}
