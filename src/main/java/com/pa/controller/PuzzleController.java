package com.pa.controller;

import com.pa.model.puzzle.PuzzleData;
import com.pa.model.puzzle.PuzzleFragment;
import com.pa.model.puzzle.PuzzlePiece;
import com.pa.view.PuzzleIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class PuzzleController {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleController.class);

    private PuzzleData puzzleData;
    private Supplier<Point> offsetSupplier;
    private int puzzleToleranceForJoining;

    public PuzzleController() {
        puzzleToleranceForJoining = 10;
    }

    public void setPuzzleData(PuzzleData data) {
        this.puzzleData = data;
    }

    public void setOffsetSupplier(Supplier<Point> offsetSupplier) {
        this.offsetSupplier = offsetSupplier;
    }

    public PuzzlePiece[][] getPieces() {
        return puzzleData != null ? puzzleData.getPieces() : new PuzzlePiece[0][0];
    }

    public Point getPiecePosition(int ordinal) {
        return puzzleData != null ? puzzleData.getPiecePosition(ordinal) : null;
    }

    public PuzzleFragment[] getFragments(boolean includeFinalized) {
        return puzzleData != null ? puzzleData.getFragments(includeFinalized) : new PuzzleFragment[0];
    }

    public Point getFragmentPosition(PuzzleFragment fragment) {
        return puzzleData != null ? puzzleData.getFragmentPosition(fragment) : null;
    }

    public Image getImage() {
        return puzzleData != null ? puzzleData.getImage() : null;
    }

    public Point getOffset() {
        return offsetSupplier == null ? new Point(0, 0) : offsetSupplier.get();
    }

    public void regularizePieces() {
        if (puzzleData != null) {
            puzzleData.regularizePieces();
        }
    }

    public synchronized boolean handlePuzzleIconPositionChange(PuzzleIcon icon) {
        Point newPosition = new Point(icon.getX() + getOffset().x, icon.getY() + getOffset().y);
        PuzzleFragment fragment = icon.getFragment();
        if (fragment == null) {
            return false;
        }

        puzzleData.updatePosition(fragment, newPosition);

        if (canMovedFragmentBeFinalized(fragment)) {
            puzzleData.finalize(fragment);
            LOG.debug("Currently {} pieces are finalized.", puzzleData.countFinalizedPieces());
            return true;
        }

        boolean anyJoined = false;
        Set<PuzzleFragment> checkedFragments = new HashSet<>();

        int[] fragmentNeighboursOrdinals = fragment.getBorderingPiecesOrdinals();
        for (int neighbourOrdinal : fragmentNeighboursOrdinals) {
            PuzzlePiece neighbourPiece = puzzleData.getPiece(neighbourOrdinal);
            PuzzleFragment neighbourFragment = puzzleData.getFragmentOwningPiece(neighbourPiece);

            if (!checkedFragments.add(neighbourFragment)) {
                continue;
            }

            Point neighbourNWCorner = neighbourPiece.getNWCorner();
            Point neighbourPosition = puzzleData.getPiecePosition(neighbourOrdinal);

            int[] possibleOrdinalsForMerging = neighbourPiece.getNeighbouringOrdinals();
            for (int possibleOrdinal : possibleOrdinalsForMerging) {
                if (!fragment.hasPiece(possibleOrdinal)) {
                    continue;
                }

                PuzzlePiece possiblePieceForMerging = puzzleData.getPiece(possibleOrdinal);
                Point possiblePieceForMergingNWCorner = possiblePieceForMerging.getNWCorner();
                Point possiblePieceForMergingPosition = puzzleData.getPiecePosition(possibleOrdinal);

                Point expectedDiff = new Point(neighbourNWCorner.x - possiblePieceForMergingNWCorner.x, neighbourNWCorner.y - possiblePieceForMergingNWCorner.y);
                Point actualDiff = new Point(neighbourPosition.x - possiblePieceForMergingPosition.x, neighbourPosition.y - possiblePieceForMergingPosition.y);

                if (PuzzleControllerUtil.arePointsEqual(expectedDiff, actualDiff, puzzleToleranceForJoining)) {
                    puzzleData.mergeFragments(fragment, neighbourFragment);
                    anyJoined = true;
                }
            }
        }

        return anyJoined;
    }

    private boolean canMovedFragmentBeFinalized(PuzzleFragment fragment) {
        return Arrays.stream(fragment.getPieces()).anyMatch(piece -> {
            Point actualPosition = puzzleData.getPiecePosition(piece.getOrdinal());
            Point expectedPosition = piece.getNWCorner();
            return PuzzleControllerUtil.arePointsEqual(actualPosition, expectedPosition, puzzleToleranceForJoining);
        });
    }

}
