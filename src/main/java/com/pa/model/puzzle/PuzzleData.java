package com.pa.model.puzzle;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleData {

    private Image image;
    private PuzzlePiece[][] pieces;
    private final Map<Integer, Point> currentPositions;

    public PuzzleData() {
        this.currentPositions = new HashMap<>();
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

        int ordinal = 0;
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
                piece.setOrdinal(ordinal);
                currentPositions.put(ordinal, piece.getNWCorner());

                ordinal++;
            }
        }
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
    }

    public Point getPiecePosition(int ordinal) {
        return currentPositions.get(ordinal);
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

    public void regularizePieces() {
        List<PuzzlePiece> shuffledPieces = new ArrayList<>();
        for (int row = 0; row < countRows(); row++) {
            for (int column = 0; column < countColumns(); column++) {
                PuzzlePiece piece = pieces[row][column];
                if (!piece.isSet()) {
                    shuffledPieces.add(piece);
                }
            }
        }

        Collections.shuffle(shuffledPieces);

        int standardGap = 8;
        int shuffledRows = countRows();
        int shuffledColumns = (shuffledPieces.size() - 1) / shuffledRows + 1;
        int width = shuffledPieces.get(0).getShape().getBounds().width;
        int height = shuffledPieces.get(0).getShape().getBounds().height;
        int x = image.getWidth(null) + standardGap;
        int y = 0;

        for (int row = 0; row < shuffledRows; row++) {
            for (int column = 0; column < shuffledColumns; column++) {
                int index = row * shuffledColumns + column;
                if (index >= shuffledPieces.size()) {
                    break;
                }
                PuzzlePiece piece = shuffledPieces.get(index);
                currentPositions.put(piece.getOrdinal(), new Point(x, y));
                x += width + standardGap;
            }
            x = image.getWidth(null) + standardGap;
            y += height + standardGap;
        }
    }

}
