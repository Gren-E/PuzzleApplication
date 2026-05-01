package com.pa.view;

import com.gutil.gui.adapters.DragMouseAdapter;
import com.pa.controller.PuzzleController;
import com.pa.controller.PuzzleIconDragMouseAdapter;
import com.pa.model.puzzle.PuzzlePiece;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class GamePanel extends JPanel {

    private final AppWindow parent;

    private final PuzzleController puzzleController;

    private List<PuzzleIcon> icons;

    private Point offset;

    private JPanel imageBoard;

    private JLayeredPane mainPanel;

    public GamePanel(AppWindow parent) {
        setBackground(Color.BLACK);

        this.parent = parent;
        puzzleController = parent.getPuzzleController();
    }

    public void reload() {
        mainPanel = new JLayeredPane();
        mainPanel.setLayout(null);

        offset = new Point(-50, -50);
        puzzleController.setOffsetSupplier(this::getOffset);

        imageBoard = new JPanel();
        imageBoard.setBackground(Color.GRAY);

        icons = PuzzleIconFactory.createPuzzleIcons(puzzleController.getPieces(), puzzleController.getImage());
        for (PuzzleIcon icon : icons) {
            DragMouseAdapter adapter = new PuzzleIconDragMouseAdapter(icon, puzzleController);
            icon.addMouseListener(adapter);
            icon.addMouseMotionListener(adapter);
        }

        puzzleController.regularizePieces();
        regularizeIcons();
    }

    public void regularizeIcons() {
        removeAll();
        mainPanel.removeAll();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        imageBoard.setBounds(-offset.x, -offset.y, puzzleController.getImage().getWidth(null), puzzleController.getImage().getHeight(null));
        mainPanel.add(imageBoard, 1, 0);

        for (PuzzleIcon icon : icons) {
            PuzzlePiece piece = icon.getPiece();
            Point piecePosition = puzzleController.getPiecePosition(piece.getOrdinal());
            icon.setBounds(piecePosition.x - offset.x, piecePosition.y - offset.y, piece.getShape().getBounds().width + 1, piece.getShape().getBounds().height + 1);
            mainPanel.add(icon, icon.getPiece().isSet() ? 2 : 3, 0);
        }
    }

    public Point getOffset() {
        if (offset == null) {
            offset = new Point(0, 0);
        }

        return offset;
    }

}
