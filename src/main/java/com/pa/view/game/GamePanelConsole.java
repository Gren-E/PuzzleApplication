package com.pa.view.game;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class GamePanelConsole extends JPanel {

    private final GamePanel gamePanel;

    public GamePanelConsole(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(new Color(50, 50, 50));

        JButton regularize = new JButton("Regularize Pieces");
        regularize.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegularizeButton();
            }
        });

        add(regularize);
    }

    private void handleRegularizeButton() {
        gamePanel.regularize();
    }

}
