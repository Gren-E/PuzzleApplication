package com.pa.view;

import com.pa.controller.PuzzleController;
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

    private final PuzzleController puzzleController;

    private final CardLayout cardLayout;

    public AppWindow() {
        setTitle("PuzzleApplication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        puzzleController = new PuzzleController();

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
        puzzleController.setPuzzleData(game.getPuzzleData());
        gamePanel.reload();
        cardLayout.show(mainPanel, GAME_PANEL);
    }

    public PuzzleController getPuzzleController() {
        return puzzleController;
    }

}
