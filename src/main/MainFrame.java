package main;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;
    private Game game = new Game();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel gridPanel = new JPanel(new GridLayout(game.getHeight(), game.getWidth()));
    private final Vector<Vector<JButton>> buttonGrid = new Vector<>();
    private final JLabel lblBlue = new JLabel();
    private final JLabel lblRed = new JLabel();
    private boolean killState = true;
    private final MinimaxBot minimaxBot = new MinimaxBot();

    public static MainFrame getInstance() {
        if (instance == null) instance = new MainFrame();
        return instance;
    }

    private MainFrame() {
        init();
    }

    private void init() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screensize = kit.getScreenSize();
        setSize(screensize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game of life");
        add(mainPanel);
        initTopPanel();
        initGridPanel();
        update();

        setVisible(true);
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        Font font = lblBlue.getFont().deriveFont(20.0f);
        lblBlue.setFont(font);
        lblRed.setFont(font);
        lblBlue.setForeground(Color.BLUE);
        lblRed.setForeground(Color.RED);
        topPanel.add(lblBlue, BorderLayout.WEST);
        topPanel.add(lblRed, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void initGridPanel() {
        for (int i = 0; i < game.getHeight(); i++) {
            Vector<JButton> v = new Vector<>();
            for (int j = 0; j < game.getWidth(); j++) {
                JButton btn = new JButton();
                final int finalI = i;
                final int finalJ = j;
                btn.addActionListener(e -> {
                    game.setAliveAt(finalI, finalJ, !killState);
                    killState = !killState;
                    if (killState) {
                        game.cycle();
                        game.switchPlayer();
                        minimaxBot.playTurn(game);
                    }
                    update();
                    if(game.isOver()) {
                        gameOver();
                    }
                });
                gridPanel.add(btn);
                v.add(btn);
            }
            buttonGrid.add(v);
        }
        gridPanel.setBackground(Color.DARK_GRAY);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    private void gameOver() {
        String winner = game.winner() != null ? game.winner().toString() : "no one!";
        new GameOverDialog(winner);
    }

    public void update() {
        lblBlue.setText("[" + game.getBlueCells() + "]");
        lblRed.setText("[" + game.getRedCells() + "]");
        updateGridPanel();

    }

    private void updateGridPanel() {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                updateTile(i, j);
            }
        }
    }

    public void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateTile(int i, int j) {
        boolean enabled = false;
        Tile tile = game.getTileAt(i, j);
        if(tile.isAlive() && killState && tile.getPlayer() != game.getCurrentPlayer()) enabled = true;
        else if(!tile.isAlive() && !killState) enabled = true;
        buttonGrid.elementAt(i).elementAt(j).setEnabled(enabled);
        buttonGrid.elementAt(i).elementAt(j).setBackground(game.getTileAt(i, j).getColor());
    }

    public void restartGame() {
        game = new Game();
        update();
    }
}
