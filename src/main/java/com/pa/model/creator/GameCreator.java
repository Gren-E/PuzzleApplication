package com.pa.model.creator;

import com.pa.model.creator.factory.PiecesFactory;
import com.pa.model.game.Game;
import com.pa.model.puzzle.PuzzlePiece;

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
        PiecesFactory factory = PiecesFactory.getFactory();
        return factory.generatePieces(rows, columns, image.getWidth(null), image.getHeight(null));
    }

}
