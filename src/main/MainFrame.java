package main;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;
    private final Game game = new Game();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel gridPanel = new JPanel(new GridLayout(game.getHeight(), game.getWidth()));
    private final Vector<Vector<JButton>> buttonGrid = new Vector<>();

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

        setVisible(true);
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel();
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(e -> {
            game.cycle();
            updateGridPanel();
        });
        topPanel.add(btnNext);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void initGridPanel() {
        for (int i = 0; i < game.getHeight(); i++) {
            Vector<JButton> v = new Vector<>();
            for (int j = 0; j < game.getWidth(); j++) {
                JButton btn = new JButton("a");
                gridPanel.add(btn);
                v.add(btn);
            }
            buttonGrid.add(v);
        }
        game.setAliveAt(5, 5, true);
        game.setAliveAt(4, 4, true);
        game.setAliveAt(6, 5, true);
        game.setAliveAt(6, 4, true);
        game.setAliveAt(6, 3, true);

        updateGridPanel();
        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    private void updateGridPanel() {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
//                buttonGrid.elementAt(i).elementAt(j).setEnabled(game.getTileAt(i, j).isAlive());
                buttonGrid.elementAt(i).elementAt(j).setBackground(game.getTileAt(i, j).isAlive() ? Color.BLACK : Color.WHITE);
            }
        }
    }
}
