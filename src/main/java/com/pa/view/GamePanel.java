package com.pa.view;

import com.pa.controller.PuzzleController;
import com.pa.controller.PuzzleIconDragMouseAdapter;
import com.pa.model.puzzle.PuzzleFragment;

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

        removeAll();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        puzzleController.regularizePieces();
        regenerateIcons();
    }

    public void regenerateIcons() {
        imageBoard.setBounds(-offset.x, -offset.y, puzzleController.getImage().getWidth(null), puzzleController.getImage().getHeight(null));
        mainPanel.removeAll();
        mainPanel.add(imageBoard, 1, 0);

        icons = PuzzleIconFactory.createPuzzleIcons(puzzleController.getFragments(true), puzzleController.getImage());
        for (PuzzleIcon icon : icons) {
            PuzzleIconDragMouseAdapter adapter = new PuzzleIconDragMouseAdapter(icon, puzzleController);
            adapter.setIconRebuildingAction(this::regenerateIcons);
            icon.addMouseListener(adapter);
            icon.addMouseMotionListener(adapter);

            PuzzleFragment fragment = icon.getFragment();
            if (fragment.getPieces().length == 0) {
                continue;
            }

            Point piecePosition = puzzleController.getFragmentPosition(fragment);
            icon.setBounds(piecePosition.x - offset.x, piecePosition.y - offset.y, fragment.getShape().getBounds().width + 1, fragment.getShape().getBounds().height + 1);
            mainPanel.add(icon, fragment.isFinalized() ? 1 : Integer.MAX_VALUE - fragment.countPieces(), 0);
        }

        repaint();
    }

    public Point getOffset() {
        if (offset == null) {
            offset = new Point(0, 0);
        }

        return offset;
    }

}
