package com.pa.model.puzzle;

import java.awt.Point;
import java.awt.Shape;

public class PieceShape {

    private final Shape shape;

    public PieceShape(Shape shape) {
        this.shape = shape;
    }

    public Point getNWCorner() {
        return new Point(getShape().getBounds().x, getShape().getBounds().y);
    }

    public Shape getShape() {
        return shape;
    }

}
