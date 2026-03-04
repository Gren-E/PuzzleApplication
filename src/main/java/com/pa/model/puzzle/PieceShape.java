package com.pa.model.puzzle;

import java.awt.Point;
import java.awt.Shape;

public class PieceShape {

    private final Point nwCorner;
    private final Shape shape;

    public PieceShape(Point nwCorner, Shape shape) {
        this.nwCorner = nwCorner;
        this.shape = shape;
    }

    public Point getNWCorner() {
        return nwCorner;
    }

    public Shape getShape() {
        return shape;
    }

}
