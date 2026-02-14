package com.pa.gui;

import com.gutil.adapters.DragMouseAdapter;
import com.pa.game.Game;
import com.pa.puzzle.PuzzlePiece;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private Game game;

    private List<PuzzleIcon> icons;

    private Point offset;

    private JPanel imageBoard;

    private JLayeredPane mainPanel;

    public void loadGame(Game game) {
        this.game = game;

        mainPanel = new JLayeredPane();
        mainPanel.setLayout(null);

        icons = new ArrayList<>();
        offset = new Point(-50, -50);

        imageBoard = new JPanel();
        imageBoard.setBackground(Color.GRAY);

        Image image = game.getImage();

        PuzzlePiece[][] pieces = game.getPieces();
        for (int row = 0; row < game.getRows(); row++) {
            for (int column = 0; column < game.getColumns(); column++) {
                PuzzlePiece piece = pieces[row][column];
                PuzzleIcon icon = new PuzzleIcon(image, piece);
                DragMouseAdapter adapter = new DragMouseAdapter(icon);
                icon.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
                icon.addMouseListener(adapter);
                icon.addMouseMotionListener(adapter);
                icons.add(icon);
            }
        }

        game.regularizePieces();
        regularizeIcons();
    }

    public void regularizeIcons() {
        removeAll();
        mainPanel.removeAll();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        imageBoard.setBounds(-offset.x, -offset.y, game.getImage().getWidth(null), game.getImage().getHeight(null));
        mainPanel.add(imageBoard, 1, 0);

        for (PuzzleIcon icon : icons) {
            PuzzlePiece piece = icon.getPiece();
            icon.setBounds(piece.getCurrentPosition().x - offset.x, piece.getCurrentPosition().y - offset.y, piece.getRectangle().width, piece.getRectangle().height);
            mainPanel.add(icon, 2, 0);
        }
    }

}
