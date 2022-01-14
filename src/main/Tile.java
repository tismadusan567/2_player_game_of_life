package main;

import java.awt.*;

public class Tile {
    private Player player = null;
    private boolean alive = false;
    private final Point pos;

    public Tile(Point pos) {
        this.pos = pos;
    }

    public Tile(Tile other) {
        this.player = other.getPlayer();
        alive = other.isAlive();
        pos = other.getPos();
    }

    public void setAlive(boolean alive) {
        if (!alive) player = null;
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        if (!alive) return Color.DARK_GRAY;
        return player == Player.BLUE ? Color.BLUE : Color.RED;
    }

    public Point getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return alive ? "a" : "d";
    }


}
