package com.pa.model.creator;

import com.pa.model.creator.factory.PieceShape;
import com.pa.model.creator.factory.PuzzleFactory;
import com.pa.model.game.Game;
import com.pa.model.puzzle.PuzzleData;

import java.awt.Image;

public class GameCreator {

    private Image image;

    private int rows;
    private int columns;

    private PieceShape pieceShape;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setPieceShape(PieceShape pieceShape) {
        this.pieceShape = pieceShape;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Game buildGame() {
        if (validateGameParameters()) {
            return new Game(generatePuzzleData());
        }
        throw new IllegalStateException("Cannot create the game - incorrect parameters.");
    }

    private boolean validateGameParameters() {
        return image != null && rows >= 1 && columns >= 1 && rows * columns > 1;
    }

    public PuzzleData generatePuzzleData() {
        PuzzleFactory factory = PuzzleFactory.getFactory(pieceShape);
        return factory.generatePuzzle(rows, columns, image);
    }

}
