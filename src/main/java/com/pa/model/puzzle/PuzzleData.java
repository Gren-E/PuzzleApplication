package com.pa.model.puzzle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PuzzleData {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleData.class);

    private Image image;
    private PuzzlePiece[][] pieces;
    private PuzzleFragment finalizedPuzzle;
    private final List<PuzzleFragment> fragments;
    private final Map<Integer, Point> currentPositions;

    public PuzzleData() {
        this.currentPositions = new HashMap<>();
        this.fragments = new CopyOnWriteArrayList<>();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setPieces(PuzzlePiece[][] pieces) {
        this.pieces = pieces;
        currentPositions.clear();
        finalizedPuzzle = new PuzzleFragment(-1);
        finalizedPuzzle.markAsFinalized();

        int numberOfColumns = -1;
        for (int row = 0; row < pieces.length; ++row) {
            if (row == 0) {
                numberOfColumns = pieces[row].length;
            }

            if (pieces[row].length != numberOfColumns) {
                throw new IllegalArgumentException("PuzzleData class does not supported puzzles with inconsistent number of columns");
            }

            for (int column = 0; column < pieces[row].length; ++column) {
                PuzzlePiece piece = pieces[row][column];
                initializePieceData(piece, row, column);

                PuzzleFragment fragment = new PuzzleFragment(countOrdinal(row, column));
                fragment.addPiece(piece);
                fragments.add(fragment);
            }
        }
    }

    private void initializePieceData(PuzzlePiece piece, int row, int column) {
        int ordinal = countOrdinal(row, column);
        piece.setOrdinal(ordinal);
        currentPositions.put(ordinal, piece.getNWCorner());

        List<Integer> neighbours = new ArrayList<>();
        if (row != 0) neighbours.add(countOrdinal(row - 1, column));
        if (row != countRows() - 1) neighbours.add(countOrdinal(row + 1, column));
        if (column != 0) neighbours.add(countOrdinal(row, column - 1));
        if (column != countColumns() - 1) neighbours.add(countOrdinal(row, column + 1));
        piece.setNeighbouringOrdinals(neighbours.stream().mapToInt(n -> n).toArray());
    }

    public int countOrdinal(int row, int column) {
        return row * countColumns() + column;
    }

    public PuzzlePiece[][] getPieces() {
        return pieces;
    }

    public PuzzlePiece getPiece(int ordinal) {
        int row = ordinal / countColumns();
        int column = ordinal % countColumns();

        return pieces != null ? pieces[row][column] : null;
    }

    public void setPiecePosition(int pieceOrdinal, int x, int y) {
        currentPositions.put(pieceOrdinal, new Point(x, y));
        LOG.debug("Position of piece {} changed to {}x{}.", pieceOrdinal, x, y);
    }

    public Point getPiecePosition(int ordinal) {
        return currentPositions.get(ordinal);
    }

    public void updatePosition(PuzzleFragment fragment, Point newPosition) {
        PuzzlePiece[] pieces = fragment.getPieces();
        Point fragmentNWCorner = new Point(fragment.getShape().getBounds().x, fragment.getShape().getBounds().y);

        Point moveDiff = new Point(newPosition.x - fragmentNWCorner.x, newPosition.y - fragmentNWCorner.y);
        for (PuzzlePiece piece : pieces) {
            Point pieceNWCorner = piece.getNWCorner();
            Point innerDiff = new Point(pieceNWCorner.x - fragmentNWCorner.x, pieceNWCorner.y - fragmentNWCorner.y);

            Point updatedPosition = new Point(fragmentNWCorner.x + innerDiff.x + moveDiff.x, fragmentNWCorner.y + innerDiff.y + moveDiff.y);
            setPiecePosition(piece.getOrdinal(), updatedPosition.x , updatedPosition.y);
        }
    }

    public void mergeFragments(PuzzleFragment mainFragment, PuzzleFragment fragmentToBeMerged) {
        if (Objects.equals(mainFragment, fragmentToBeMerged)) {
            return;
        }

        for (PuzzlePiece piece : fragmentToBeMerged.getPieces()) {
            mainFragment.addPiece(piece);
        }

        LOG.debug("Fragment {} with {} piece(-s) was merged into {}.", fragmentToBeMerged, fragmentToBeMerged.countPieces(), mainFragment);
        removeFragment(fragmentToBeMerged);

        Point fragmentPosition = getFragmentPosition(mainFragment);
        updatePosition(mainFragment, fragmentPosition);
    }

    public void removeFragment(PuzzleFragment fragment) {
        fragment.removeAllPieces();
        fragments.remove(fragment);
        LOG.debug("Puzzle fragment {} removed from data.", fragment);
    }

    public PuzzleFragment[] getFragments(boolean includeFinalized) {
        PuzzleFragment[] result = Arrays.copyOf(fragments.toArray(new PuzzleFragment[0]), includeFinalized ? fragments.size() + 1 : fragments.size());
        if (includeFinalized) {
            result[result.length - 1] = finalizedPuzzle;
        }

        return result;
    }

    public PuzzleFragment getFragmentOwningPiece(PuzzlePiece piece) {
        if (finalizedPuzzle.hasPiece(piece)) {
            return finalizedPuzzle;
        }

        return fragments.stream().filter(fr -> fr.hasPiece(piece)).findAny().orElse(null);
    }

    public Point getFragmentPosition(PuzzleFragment fragment) {
        PuzzlePiece[] pieces = fragment.getPieces();
        int x = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).x).mapToInt(n -> n).min().orElse(0);
        int y = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).y).mapToInt(n -> n).min().orElse(0);
        return new Point(x, y);
    }

    public int countRows() {
        return pieces != null ? pieces.length : 0;
    }

    public int countColumns() {
        return pieces != null ? pieces[0].length : 0;
    }

    public int countPieces() {
        return countRows() * countColumns();
    }

    public int countFinalizedPieces() {
        return finalizedPuzzle.countPieces();
    }

    public void finalize(PuzzleFragment fragment) {
        for (PuzzlePiece piece : fragment.getPieces()) {
            setPiecePosition(piece.getOrdinal(), piece.getNWCorner().x, piece.getNWCorner().y);
            finalizedPuzzle.addPiece(piece);
            LOG.debug("Puzzle piece {} finalized.", piece);
        }

        removeFragment(fragment);
    }

    public boolean isFinalized(PuzzlePiece piece) {
        return finalizedPuzzle.hasPiece(piece);
    }

    public void regularizePieces(Rectangle boardArea) {
        List<PuzzlePiece> shuffledPieces = new ArrayList<>();
        for (int row = 0; row < countRows(); row++) {
            for (int column = 0; column < countColumns(); column++) {
                PuzzlePiece piece = pieces[row][column];
                if (!isFinalized(piece) && getFragmentOwningPiece(piece).countPieces() == 1) {
                    shuffledPieces.add(piece);
                }
            }
        }

        Collections.shuffle(shuffledPieces);

        Rectangle imageRect = new Rectangle(0, 0, image.getWidth(null), image.getHeight(null));

        int x = boardArea.x + 5;
        int y = boardArea.y + 5;
        for (PuzzlePiece piece : shuffledPieces) {
            Rectangle pieceRect = piece.getShape().getBounds();

            Rectangle pieceArea = new Rectangle(x, y, pieceRect.width, pieceRect.height);
            if (pieceArea.intersects(imageRect)) {
                x = imageRect.x + imageRect.width + 5;
            }

            currentPositions.put(piece.getOrdinal(), new Point(x, y));
            x += pieceRect.width + 10;
            if (x + pieceRect.width > boardArea.x + boardArea.width) {
                x = boardArea.x + 5;
                y += pieceRect.height + 10;
            }

            if (y + pieceRect.height > boardArea.y + boardArea.height) {
                y = boardArea.y + 5;
            }
        }



//        int standardGap = 8;
//        int shuffledRows = countRows();
//        int shuffledColumns = (shuffledPieces.size() - 1) / shuffledRows + 1;
//        int width = shuffledPieces.get(0).getShape().getBounds().width;
//        int height = shuffledPieces.get(0).getShape().getBounds().height;
//        int x = image.getWidth(null) + standardGap;
//        int y = 0;
//
//        for (int row = 0; row < shuffledRows; row++) {
//            for (int column = 0; column < shuffledColumns; column++) {
//                int index = row * shuffledColumns + column;
//                if (index >= shuffledPieces.size()) {
//                    break;
//                }
//                PuzzlePiece piece = shuffledPieces.get(index);
//                currentPositions.put(piece.getOrdinal(), new Point(x, y));
//                x += width + standardGap;
//            }
//            x = image.getWidth(null) + standardGap;
//            y += height + standardGap;
//        }
    }

}
