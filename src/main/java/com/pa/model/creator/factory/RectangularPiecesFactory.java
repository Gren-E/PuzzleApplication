package com.pa.model.creator.factory;

import com.pa.model.puzzle.PuzzlePiece;

import java.awt.Rectangle;

public class RectangularPiecesFactory extends PiecesFactory {

    @Override
    public PuzzlePiece[][] generatePieces(int rows, int columns, int width, int height) {
        PuzzlePiece[][] pieces = new PuzzlePiece[rows][columns];

        int pieceWidth = width / columns;
        int pieceHeight = height / rows;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Rectangle rectangle = new Rectangle(column * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                PuzzlePiece piece = new PuzzlePiece(row * columns + column, rectangle);
                pieces[row][column] = piece;
            }
        }

        return pieces;
    }

}
