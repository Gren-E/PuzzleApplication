package com.pa.view;

import com.pa.controller.GameController;
import com.pa.model.game.Game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class AppWindow extends JFrame {

    public static final String CREATOR_PANEL = "CREATOR_PANEL";
    public static final String GAME_PANEL = "GAME_PANEL";

    private final GamePanel gamePanel;
    private final GameCreatorPanel gameCreatorPanel;
    private final JPanel mainPanel;

    private final GameController gameController;

    private final CardLayout cardLayout;

    public AppWindow() {
        setTitle("PuzzleApplication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        gameController = new GameController();

        gameCreatorPanel = new GameCreatorPanel(this);
        gamePanel = new GamePanel(this);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(CREATOR_PANEL, gameCreatorPanel);
        mainPanel.add(GAME_PANEL, gamePanel);

        add(mainPanel);

        setVisible(true);
    }

    public void loadGame(Game game) {
        gameController.setGame(game);
        gamePanel.reload();
        cardLayout.show(mainPanel, GAME_PANEL);
    }

    public GameController getGameController() {
        return gameController;
    }

}
