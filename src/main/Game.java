package main;

import javax.swing.text.BadLocationException;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Game {
    private final int width;
    private final int height;
    private Vector<Vector<Tile>> grid = new Vector<>();
//    private final Player bluePlayer;
//    private final Player redPlayer;
    private int blueCells;
    private int redCells;
    private Player currentPlayer;

    public Game(int width, int height) {
        blueCells = 0;
        redCells = 0;
        this.width = width;
        this.height = height;
        currentPlayer = Player.BLUE;
        initGrid();
    }

    public Game() {
        this(5, 5);
    }

    public Game(Game other) {
        blueCells = other.blueCells;
        redCells = other.redCells;
        width = other.width;
        height = other.height;
        currentPlayer = other.currentPlayer;
        grid = deepcopyGrid(other.grid);
    }

    private void initGrid() {
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {
                v.add(new Tile(new Point(i, j)));
            }
            grid.add(v);
        }
        setAliveAt(height - 1, 0, true);
        setAliveAt(height - 1, 1, true);
        setAliveAt(height - 2, 0, true);
        setAliveAt(height - 2, 1, true);
        blueCells = 4;
        switchPlayer();
        setAliveAt(0, width - 1, true);
        setAliveAt(1, width - 1, true);
        setAliveAt(0, width - 2, true);
        setAliveAt(1, width - 2, true);
        redCells = 4;
        switchPlayer();
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == Player.BLUE ? Player.RED : Player.BLUE;
    }

    public void cycle() {
        redCells = 0;
        blueCells = 0;
        Vector<Vector<Tile>> newGrid = new Vector<>();
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {
                Tile currentTile = getTileAt(i, j);
                Tile newTile = new Tile(new Point(i, j));

                int blueNeighbours = 0;
                int redNeighbours = 0;
                for (Tile t : neighbours(i, j)) {
                    if (t.isAlive()) {
                        if (t.getPlayer() == Player.BLUE) blueNeighbours++;
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
                    if (alive) {
                        newTile.setPlayer(blueNeighbours > redNeighbours ? Player.BLUE : Player.RED);
                    }
                }
                newTile.setAlive(alive);
                v.add(newTile);
                if(newTile.getPlayer() == Player.BLUE) blueCells++;
                if(newTile.getPlayer() == Player.RED) redCells++;
            }
            newGrid.add(v);
        }
        grid = newGrid;
        MainFrame.getInstance().update();
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
        Tile tile = getTileAt(i, j);
        tile.setAlive(alive);
        if (alive) tile.setPlayer(currentPlayer);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private Vector<Vector<Tile>> deepcopyGrid(Vector<Vector<Tile>> original) {
        Vector<Vector<Tile>> newGrid = new Vector<>();
        for (int i = 0; i < height; i++) {
            Vector<Tile> v = new Vector<>();
            for (int j = 0; j < width; j++) {
                Tile orgTile = original.elementAt(i).elementAt(j);
                Tile newTile = new Tile(orgTile);
                v.add(newTile);
            }
            newGrid.add(v);
        }
        return newGrid;
    }

    public Vector<Tile> getKillableTiles() {
        Player opponent = currentPlayer == Player.BLUE ? Player.RED : Player.BLUE;
        Vector<Tile> v = new Vector<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTileAt(i, j).getPlayer() == opponent) v.add(getTileAt(i, j));
            }
        }
        return v;
    }

    public Vector<Tile> getRevivableTiles() {
        Vector<Tile> v = new Vector<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!getTileAt(i, j).isAlive()) v.add(getTileAt(i, j));
            }
        }
        return v;
    }

    public boolean isOver() {
        return blueCells == 0 || redCells == 0;
    }

    public Player winner() {
        if (blueCells == redCells) return null;
        return blueCells == 0 ? Player.RED : Player.BLUE;
    }

    public int getRedCells() {
        return redCells;
    }

    public int getBlueCells() {
        return blueCells;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
