package com.pa.controller;

import com.pa.model.puzzle.PuzzleData;
import com.pa.model.puzzle.PuzzlePiece;
import com.pa.view.PuzzleIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.Point;
import java.util.function.Supplier;

public class PuzzleController {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleController.class);

    private PuzzleData puzzleData;
    private Supplier<Point> offsetSupplier;
    private int puzzleToleranceForJoining;

    public PuzzleController() {
        puzzleToleranceForJoining = 10;
    }

    public void regularizePieces() {
        if (puzzleData != null) {
            puzzleData.regularizePieces();
        }
    }

    public void handlePuzzleIconPositionChange(PuzzleIcon icon) {
        Point newPosition = new Point(icon.getX() + getOffset().x, icon.getY() + getOffset().y);
        Point expectedPosition = icon.getPiece().getNWCorner();

        if (PuzzleControllerUtil.arePointsEqual(newPosition, expectedPosition, puzzleToleranceForJoining)) {
            PuzzleControllerUtil.adjustPiecePosition(puzzleData, icon, expectedPosition, getOffset());
            icon.getPiece().markAsSet();

            LOG.debug("Puzzle piece {} set with position {}.", icon.getPiece(), expectedPosition);
        }
    }

    public void setPuzzleData(PuzzleData data) {
        this.puzzleData = data;
    }

    public PuzzlePiece[][] getPieces() {
        return puzzleData != null ? puzzleData.getPieces() : new PuzzlePiece[0][0];
    }

    public Point getPiecePosition(int ordinal) {
        return puzzleData.getPiecePosition(ordinal);
    }

    public Image getImage() {
        return puzzleData != null ? puzzleData.getImage() : null;
    }

    public void setOffsetSupplier(Supplier<Point> offsetSupplier) {
        this.offsetSupplier = offsetSupplier;
    }

    public Point getOffset() {
        return offsetSupplier == null ? new Point(0, 0) : offsetSupplier.get();
    }

}
