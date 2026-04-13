package com.pa.model.creator.factory;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class RectangularPiecesFactory extends RightAngleBasedPiecesFactory {

    @Override
    protected Path2D generatePuzzlePieceEdge(Point2D start, Point2D end, boolean isFlat) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());

        path.lineTo(end.getX(), end.getY());
        return path;
    }

}
