package com.pa.model.puzzle;

import java.awt.Point;
import java.awt.Shape;

public class PuzzlePiece {

    private final int id;

    private final PieceShape shape;

    private Point currentPosition;
    private boolean isPositionFinal;

    public PuzzlePiece(int id, PieceShape shape) {
        this.id = id;
        this.shape = shape;
    }

    public void setCurrentPosition(int x, int y) {
        currentPosition = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public Shape getShape() {
        return shape.getShape();
    }
    public Point getNWCorner() {
        return shape.getNWCorner();
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void markAsSet() {
        this.isPositionFinal = true;
    }

    public boolean isSet() {
        return isPositionFinal;
    }

    @Override
    public String toString() {
        return String.format("PuzzlePiece{id=%d, currentPosition=%s}", id, currentPosition);
    }

}
