package com.pa.view;

import com.pa.controller.GameController;
import com.pa.model.puzzle.PuzzlePiece;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class GamePanel extends JPanel {

    private final AppWindow parent;

    private final GameController gameController;

    private List<PuzzleIcon> icons;

    private Point offset;

    private JPanel imageBoard;

    private JLayeredPane mainPanel;

    public GamePanel(AppWindow parent) {
        this.parent = parent;
        gameController = parent.getGameController();
    }

    public void reload() {
        mainPanel = new JLayeredPane();
        mainPanel.setLayout(null);

        offset = new Point(-50, -50);

        imageBoard = new JPanel();
        imageBoard.setBackground(Color.GRAY);

        icons = PuzzleIconFactory.createPuzzleIcons(gameController.getPieces(), gameController.getImage());

        gameController.regularizePieces();
        regularizeIcons();
    }

    public void regularizeIcons() {
        removeAll();
        mainPanel.removeAll();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        imageBoard.setBounds(-offset.x, -offset.y, gameController.getImage().getWidth(null), gameController.getImage().getHeight(null));
        mainPanel.add(imageBoard, 1, 0);

        for (PuzzleIcon icon : icons) {
            PuzzlePiece piece = icon.getPiece();
            icon.setBounds(piece.getCurrentPosition().x - offset.x, piece.getCurrentPosition().y - offset.y, piece.getRectangle().width, piece.getRectangle().height);
            mainPanel.add(icon, 2, 0);
        }
    }

}
