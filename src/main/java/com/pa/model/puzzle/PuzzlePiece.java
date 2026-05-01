package com.pa.model.puzzle;

import java.awt.Point;
import java.awt.Shape;

public class PuzzlePiece {

    private int ordinal;

    private final Shape shape;

    private boolean isPositionFinal;

    public PuzzlePiece(Shape shape) {
        this.shape = shape;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }


    public Shape getShape() {
        return shape;
    }
    public Point getNWCorner() {
        return new Point(getShape().getBounds().x, getShape().getBounds().y);
    }

    public void markAsSet() {
        this.isPositionFinal = true;
    }

    public boolean isSet() {
        return isPositionFinal;
    }

    @Override
    public String toString() {
        return String.format("PuzzlePiece{ordinal=%d}", ordinal);
    }

}
