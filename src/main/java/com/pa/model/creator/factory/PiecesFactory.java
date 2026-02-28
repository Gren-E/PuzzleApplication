package com.pa.model.creator.factory;

import com.pa.model.puzzle.PuzzlePiece;

public abstract class PiecesFactory {

    public static PiecesFactory getFactory() {
        return new RectangularPiecesFactory();
    }

    public abstract PuzzlePiece[][] generatePieces(int rows, int columns, int width, int height);

}
