package main;

import java.awt.*;
import java.util.Vector;

public class Game {
    private final int width;
    private final int height;
    private Vector<Vector<Tile>> grid = new Vector<>();
    private final Player bluePlayer = new Player(Color.BLUE);
    private final Player redPlayer = new Player(Color.RED);
    private Player currentPlayer = bluePlayer;

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
        setAliveAt(5, 5, true);
        setAliveAt(4, 4, true);
        setAliveAt(6, 5, true);
        setAliveAt(6, 4, true);
        setAliveAt(6, 3, true);
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == bluePlayer ? redPlayer : bluePlayer;
    }

    public void cycle() {
        Vector<Vector<Tile>> newGrid = new Vector<>();
        bluePlayer.setCells(0);
        redPlayer.setCells(0);
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {
                Tile currentTile = getTileAt(i, j);
                Tile newTile = new Tile();

                int blueNeighbours = 0;
                int redNeighbours = 0;
                for (Tile t : neighbours(i, j)) {
                    if (t.isAlive()) {
                        if (t.getPlayer() == bluePlayer) blueNeighbours++;
                        else redNeighbours++;
                    }
                }
                int liveNeighbours = blueNeighbours + redNeighbours;
                boolean alive;
                if (currentTile.isAlive()) {
                    alive = liveNeighbours == 2 || liveNeighbours == 3;
                    if (alive) newTile.setPlayer(currentTile.getPlayer());
                } else {
                    alive = liveNeighbours == 3;
                    if (alive) newTile.setPlayer(blueNeighbours > redNeighbours ? bluePlayer : redPlayer);
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
            neighbours.add(getTileAt(i - 1, j));
            if (j > 0) neighbours.add(getTileAt(i - 1, j - 1));
            if (j < width - 1) neighbours.add(getTileAt(i - 1, j + 1));
        }
        if (i < height - 1) {
            neighbours.add(getTileAt(i + 1, j));
            if (j > 0) neighbours.add(getTileAt(i + 1, j - 1));
            if (j < width - 1) neighbours.add(getTileAt(i + 1, j + 1));
        }
        if (j > 0) neighbours.add(getTileAt(i, j - 1));
        if (j < width - 1) neighbours.add(getTileAt(i, j + 1));

        return neighbours;
    }

    public Tile getTileAt(int i, int j) {
        return grid.elementAt(i).elementAt(j);
    }

    public void setAliveAt(int i, int j, boolean alive) {
        getTileAt(i, j).setAlive(alive);
        if (alive) getTileAt(i, j).setPlayer(currentPlayer);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }
}
