package com.pa.view.game;

import com.pa.controller.PuzzleController;
import com.pa.controller.PuzzleIconDragMouseAdapter;
import com.pa.model.puzzle.PuzzleFragment;
import com.pa.view.AppWindow;
import com.pa.view.icon.PuzzleIcon;
import com.pa.view.icon.PuzzleIconFactory;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {

    private final AppWindow parent;

    private final PuzzleController puzzleController;

    private List<PuzzleIcon> icons;

    private Point offset;
    private JPanel imageBoard;
    private GamePanelConsole console;

    private JLayeredPane mainPanel;
    private boolean gameFinished;
    private Timer flashingTimer;
    private int flashingDirection;

    public GamePanel(AppWindow parent) {
        setBackground(Color.BLACK);

        offset = new Point(0, 0);

        this.parent = parent;
        puzzleController = parent.getPuzzleController();
        puzzleController.setGamePanel(this);
        puzzleController.setOffsetSupplier(this::getOffset);

        mainPanel = new JLayeredPane();
        mainPanel.setLayout(null);

        imageBoard = new JPanel();
        imageBoard.setBackground(new Color(60, 60, 60));

        console = new GamePanelConsole(this);
        console.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(console, BorderLayout.SOUTH);
    }

    public PuzzleController getPuzzleController() {
        return puzzleController;
    }

    public void regularize() {
        puzzleController.regularizePieces(getBounds(), getOffset());
        reload();
    }

    public void reset() {
        mainPanel.removeAll();
        gameFinished = false;
        flashingDirection = -2;
        setBackground(Color.BLACK);

        if (flashingTimer != null) {
            flashingTimer.cancel();
            flashingTimer = null;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = puzzleController.getImage().getWidth(null);
        int imageHeight = puzzleController.getImage().getHeight(null);
        offset = new Point(-(panelWidth - imageWidth) / 2, -(panelHeight - imageHeight) / 2);
        reload();
    }

    public void reload() {
        mainPanel.removeAll();
        int imageWidth = puzzleController.getImage().getWidth(null);
        int imageHeight = puzzleController.getImage().getHeight(null);
        imageBoard.setBounds(-offset.x, -offset.y, imageWidth, imageHeight);
        mainPanel.add(imageBoard, 1, 0);
        reloadIcons();
    }

    private void reloadIcons() {
        icons = PuzzleIconFactory.createPuzzleIcons(puzzleController.getFragments(true), puzzleController.getImage());
        for (PuzzleIcon icon : icons) {
            PuzzleIconDragMouseAdapter adapter = new PuzzleIconDragMouseAdapter(icon, puzzleController);
            adapter.setIconRebuildingAction(this::reload);
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

    public AppWindow getWindow() {
        return parent;
    }

    public void checkWinCondition() {
        if (!gameFinished && puzzleController.isPuzzleFinished()) {
            gameFinished = true;
            puzzleController.stopCountdown();
            flashingTimer = new Timer();
            flashingTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Color background = getBackground();
                    int r = background.getRed();
                    int g = background.getGreen();
                    int b = background.getBlue();

                    if (r < 2 || g < 2 || b < 2) {
                        flashingDirection = 2;
                    } else if (r >= 253 || g >= 253 || b >= 253) {
                        flashingDirection = -2;
                    }

                    r += flashingDirection;
                    g += flashingDirection;
                    b += flashingDirection;
                    setBackground(new Color(r, g, b));
                }
            }, 0, 20);
        }
    }

}
