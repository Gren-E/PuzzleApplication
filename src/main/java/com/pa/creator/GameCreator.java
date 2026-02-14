package com.pa.creator;

import com.pa.game.Game;
import com.pa.puzzle.PuzzlePiece;

import java.awt.Image;
import java.awt.Rectangle;

public class GameCreator {

    private Image image;

    private int rows;
    private int columns;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public Game buildGame() {
        if (validateGameParameters()) {
            return new Game(image, generatePieces());
        }
        throw new IllegalStateException("Cannot create the game - incorrect parameters.");
    }

    private boolean validateGameParameters() {
        return image != null && rows >= 1 && columns >= 1 && rows * columns > 1;
    }

    public PuzzlePiece[][] generatePieces() {
        PuzzlePiece[][] pieces = new PuzzlePiece[rows][columns];

        int width = image.getWidth(null) / columns;
        int height = image.getHeight(null) / rows;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Rectangle rectangle = new Rectangle(column * width, row * height, width, height);
                PuzzlePiece piece = new PuzzlePiece(row * columns + column, rectangle);
                pieces[row][column] = piece;
            }
        }

        return pieces;
    }

}
