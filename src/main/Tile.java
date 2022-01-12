package main;

import java.awt.*;

public class Tile {
    private Player player = null;
    private boolean alive = false;

    public Tile() { }

    public void setAlive(boolean alive) {
        if(!alive) player = null;
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.incrementCells();
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        if(!alive) return Color.DARK_GRAY;
        return player.getColor();
    }

    @Override
    public String toString() {
        return alive ? "a" : "d";
    }
}
