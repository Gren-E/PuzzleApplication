package com.pa.controller;

import com.pa.model.game.Game;
import com.pa.model.puzzle.PuzzlePiece;
import com.pa.view.PuzzleIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.Point;
import java.util.function.Supplier;

public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private Game game;
    private Supplier<Point> offsetSupplier;
    private int puzzleToleranceForJoining;

    public GameController() {
        puzzleToleranceForJoining = 10;
    }

    public void regularizePieces() {
        if (game != null) {
            game.regularizePieces();
        }
    }

    public void handlePuzzleIconPositionChange(PuzzleIcon icon) {
        Point newPosition = new Point(icon.getX() + getOffset().x, icon.getY() + getOffset().y);
        Point expectedPosition = icon.getPiece().getNWCorner();

        if (PuzzleControllerUtil.arePointsEqual(newPosition, expectedPosition, puzzleToleranceForJoining)) {
            PuzzleControllerUtil.adjustPiecePosition(icon, expectedPosition, getOffset());
            icon.getPiece().markAsSet();

            LOG.debug("Puzzle piece {} set with position {}.", icon.getPiece(), expectedPosition);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public int getRows() {
        return game != null ? game.getRows() : 0;
    }

    public int getColumns() {
        return game != null ? game.getColumns() : 0;
    }

    public PuzzlePiece[][] getPieces() {
        return game != null ? game.getPieces() : new PuzzlePiece[0][0];
    }

    public Image getImage() {
        return game != null ? game.getImage() : null;
    }

    public void setOffsetSupplier(Supplier<Point> offsetSupplier) {
        this.offsetSupplier = offsetSupplier;
    }

    public Point getOffset() {
        return offsetSupplier == null ? new Point(0, 0) : offsetSupplier.get();
    }

}
