package com.pa.view;

import com.gutil.gui.GBC;
import com.gutil.gui.component.button.RoundRectButton;
import com.pa.controller.PuzzleController;
import com.pa.model.game.Game;
import com.pa.view.game.CatalogPanel;
import com.pa.view.game.GameCreatorPanel;
import com.pa.view.game.GamePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.GridBagLayout;

public class AppWindow extends JFrame {

    public static final String CREATOR_PANEL = "CREATOR_PANEL";
    public static final String GAME_PANEL = "GAME_PANEL";
    public static final String CATALOG_PANEL = "CATALOG_PANEL";

    private final GamePanel gamePanel;
    private final GameCreatorPanel gameCreatorPanel;
    private final CatalogPanel catalogPanel;
    private final JPanel mainPanel;
    private final JPanel menuPanel;

    private final PuzzleController puzzleController;

    private final CardLayout cardLayout;

    public AppWindow() {
        setTitle("PuzzleApplication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);

        puzzleController = new PuzzleController();

        gameCreatorPanel = new GameCreatorPanel(this);
        catalogPanel = new CatalogPanel(this);
        gamePanel = new GamePanel(this);

        RoundRectButton exitButton = ComponentFactory.createStandardAppButton("Exit");
        exitButton.setActionListener(event -> System.exit(0));

        RoundRectButton settingsButton = ComponentFactory.createStandardAppButton("Settings");

        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.add(new JPanel(), new GBC(0, 0).setWeight(1,1).setFill(GBC.BOTH));
        menuPanel.add(settingsButton, new GBC(1,0).setAnchor(GBC.EAST).setInsets(10));
        menuPanel.add(exitButton, new GBC(2,0).setAnchor(GBC.EAST).setInsets(10, 0, 10, 10));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(CREATOR_PANEL, gameCreatorPanel);
        mainPanel.add(CATALOG_PANEL, catalogPanel);
        mainPanel.add(GAME_PANEL, gamePanel);

        setLayout(new GridBagLayout());
        add(menuPanel, new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.EAST).setInsets(0,0,10,0));
        add(mainPanel, new GBC(0,1).setWeight(1,1).setFill(GBC.BOTH).setInsets(50));

        setVisible(true);
    }

    public void loadGame(Game game) {
        puzzleController.setPuzzleData(game.getPuzzleData());
        gamePanel.regularize();
        gamePanel.reset();
        cardLayout.show(mainPanel, GAME_PANEL);
    }

    public void show(String element) {
        switch (element) {
            case GAME_PANEL -> cardLayout.show(mainPanel, GAME_PANEL);
            case CATALOG_PANEL -> cardLayout.show(mainPanel, CATALOG_PANEL);
            case CREATOR_PANEL -> cardLayout.show(mainPanel, CREATOR_PANEL);
        }
    }

    public PuzzleController getPuzzleController() {
        return puzzleController;
    }

    public GameCreatorPanel getGameCreatorPanel() {
        return gameCreatorPanel;
    }

}
