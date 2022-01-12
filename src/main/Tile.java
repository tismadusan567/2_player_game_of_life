package main;

public class Tile {
    //private player player
    private boolean alive = false;
//    private final int x;
//    private final int y;
//
//    public Tile(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public String toString() {
        return alive ? "a" : "d";
    }
}
