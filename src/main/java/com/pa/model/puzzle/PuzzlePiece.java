package com.pa.model.puzzle;

import java.awt.Point;
import java.awt.Shape;
import java.util.Objects;

public class PuzzlePiece {

    private int ordinal;
    private int[] neighbouringOrdinals;

    private final Shape shape;

    public PuzzlePiece(Shape shape) {
        this.shape = shape;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setNeighbouringOrdinals(int[] array) {
        neighbouringOrdinals = array;
    }

    public int[] getNeighbouringOrdinals() {
        return neighbouringOrdinals;
    }

    public Shape getShape() {
        return shape;
    }
    public Point getNWCorner() {
        return new Point(getShape().getBounds().x, getShape().getBounds().y);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PuzzlePiece other && ordinal == other.ordinal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ordinal);
    }

    @Override
    public String toString() {
        return String.format("PuzzlePiece{ordinal=%d}", ordinal);
    }

}
