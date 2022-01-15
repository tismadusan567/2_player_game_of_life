package main;

import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {
    public GameOverDialog(String winner) {
        super(MainFrame.getInstance(), "Game over", true);
        setLayout(new BorderLayout());
        setSize(300, 300);
        setLocationRelativeTo(MainFrame.getInstance());
        JLabel label = new JLabel("Game over.Winner is " + winner);
        add(label, BorderLayout.CENTER);
        JButton btn = new JButton("Play again");
        btn.addActionListener(e -> {
            MainFrame.getInstance().restartGame();
            dispose();
        });
        add(btn, BorderLayout.SOUTH);
        setVisible(true);
    }
}
