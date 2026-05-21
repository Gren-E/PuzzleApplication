package com.pa.view.game;

import javax.swing.JPanel;
import java.awt.Color;

public class GamePanelConsole extends JPanel {

    private final GamePanel gamePanel;

    public GamePanelConsole(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(new Color(50, 50, 50));
    }

}
