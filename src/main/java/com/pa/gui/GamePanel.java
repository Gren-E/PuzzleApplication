package com.pa.gui;

import com.pa.game.Game;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private Game game;

    public void loadGame(Game game) {
        this.game = game;
    }

}
