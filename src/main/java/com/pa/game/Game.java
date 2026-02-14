package com.pa.game;

import com.pa.puzzle.PuzzlePiece;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Image image;

    private final PuzzlePiece[][] pieces;

    public Game(Image image, PuzzlePiece[][] pieces) {
        this.image = image;
        this.pieces = pieces;
    }

    public Image getImage() {
        return image;
    }

    public PuzzlePiece[][] getPieces() {
        return pieces;
    }

    public int getRows() {
        return pieces.length;
    }

    public int getColumns() {
        return pieces[0].length;
    }

    public int getNumberOfPieces() {
        return getRows() * getColumns();
    }

    public void regularizePieces() {
        List<PuzzlePiece> shuffledPieces = new ArrayList<>();
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                PuzzlePiece piece = pieces[row][column];
                if (!piece.isMatchedOnBoard()) {
                    shuffledPieces.add(piece);
                }
            }
        }

        Collections.shuffle(shuffledPieces);

        int standardGap = 8;
        int shuffledRows = getRows();
        int shuffledColumns = (shuffledPieces.size() - 1) / shuffledRows + 1;
        int width = shuffledPieces.get(0).getRectangle().width;
        int height = shuffledPieces.get(0).getRectangle().height;
        int x = image.getWidth(null) + standardGap;
        int y = 0;

        for (int row = 0; row < shuffledRows; row++) {
            for (int column = 0; column < shuffledColumns; column++) {
                int index = row * shuffledColumns + column;
                if (index >= shuffledPieces.size()) {
                    break;
                }
                PuzzlePiece piece = shuffledPieces.get(index);
                piece.setCurrentPosition(x, y);
                x += width + standardGap;
            }
            x = image.getWidth(null) + standardGap;
            y += height + standardGap;
        }
    }

}
