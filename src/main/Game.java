package main;

import java.util.Vector;

public class Game {
    private final int width;
    private final int height;
    private Vector<Vector<Tile>> grid = new Vector<>();

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        initGrid();
    }

    public Game() {
        this(10, 10);
    }

    private void initGrid() {
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {
                v.add(new Tile());
            }
            grid.add(v);
        }
    }

    public void cycle() {
        Vector<Vector<Tile>> newGrid = new Vector<>();
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {

                Tile currentTile = grid.elementAt(i).elementAt(j);
                Tile newTile = new Tile();

                int liveNeighbours = 0;
                for (Tile t : neighbours(i, j)) if (t.isAlive()) liveNeighbours++;

                boolean alive;
                if (currentTile.isAlive()) {
                    alive = liveNeighbours == 2 || liveNeighbours == 3;
                } else {
                    alive = liveNeighbours == 3;
                }
                newTile.setAlive(alive);
                v.add(newTile);
            }
            newGrid.add(v);
        }
        grid = newGrid;
    }

    private Vector<Tile> neighbours(int i, int j) {
        Vector<Tile> neighbours = new Vector<>();
        if (i > 0) {
            neighbours.add(grid.elementAt(i - 1).elementAt(j));
            if (j > 0) neighbours.add(grid.elementAt(i - 1).elementAt(j - 1));
            if (j < width - 1) neighbours.add(grid.elementAt(i - 1).elementAt(j + 1));
        }
        if (i < height - 1) {
            neighbours.add(grid.elementAt(i + 1).elementAt(j));
            if (j > 0) neighbours.add(grid.elementAt(i + 1).elementAt(j - 1));
            if (j < width - 1) neighbours.add(grid.elementAt(i + 1).elementAt(j + 1));
        }
        if (j > 0) neighbours.add(grid.elementAt(i).elementAt(j - 1));
        if (j < width - 1) neighbours.add(grid.elementAt(i).elementAt(j + 1));

        return neighbours;
    }

    public Tile getTileAt(int i, int j) {
        return grid.elementAt(i).elementAt(j);
    }

    public void setAliveAt(int i, int j, boolean alive) {
        grid.elementAt(i).elementAt(j).setAlive(alive);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector<Vector<Tile>> getGrid() {
        return grid;
    }
}
