package com.pa.model.creator.factory;

import com.pa.model.puzzle.PuzzlePiece;

public abstract class PiecesFactory {


    public static PiecesFactory getFactory(PieceShape shape) {
        return switch (shape) {
            case RECTANGULAR -> new RectangularPiecesFactory();
            default -> new RightAngleBasedPiecesFactory();
        };
    }

    public abstract PuzzlePiece[][] generatePieces(int rows, int columns, int width, int height);

}
