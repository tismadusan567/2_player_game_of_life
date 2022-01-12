package main;

import java.awt.*;

public class Player {
    private final Color color;
    private int cells;

    public Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void incrementCells() {
        cells++;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }

    public int getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "[" + cells + "]";
    }
}
