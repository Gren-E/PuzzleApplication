package com.pa.gui;

import com.pa.game.Game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class AppWindow extends JFrame {

    public static final String CREATOR_PANEL = "CREATOR_PANEL";
    public static final String GAME_PANEL = "GAME_PANEL";

    private final GamePanel gamePanel;
    private final CreatorPanel creatorPanel;
    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public AppWindow() {
        setTitle("PuzzleApplication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        creatorPanel = new CreatorPanel(this);
        gamePanel = new GamePanel();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(CREATOR_PANEL, creatorPanel);
        mainPanel.add(GAME_PANEL, gamePanel);

        add(mainPanel);

        setVisible(true);
    }

    public void loadGame(Game game) {
        gamePanel.loadGame(game);
        cardLayout.show(mainPanel, GAME_PANEL);
    }

}
