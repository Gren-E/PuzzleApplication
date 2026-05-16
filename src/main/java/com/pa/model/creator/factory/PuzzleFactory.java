package com.pa.model.creator.factory;

import com.pa.model.puzzle.PuzzleData;

import java.awt.Image;

public abstract class PuzzleFactory {

    public static PuzzleFactory getFactory(PieceShape shape) {
        return switch (shape) {
            case RECTANGULAR -> new RectangularPuzzleFactory();
            default -> new RightAngleBasedPuzzleFactory();
        };
    }

    public abstract PuzzleData generatePuzzle(int rows, int columns, Image image);

}
