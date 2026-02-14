package com.pa.puzzle;

import java.awt.Point;
import java.awt.Rectangle;

public class PuzzlePiece {

    private final int id;

    private final Rectangle rectangle;

    private Point currentPosition;

    private boolean isMatchedOnBoard;

    public PuzzlePiece(int id, Rectangle rectangle) {
        this.id = id;
        this.rectangle = rectangle;
    }

    public void setCurrentPosition(int x, int y) {
        currentPosition = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMatchedOnBoard() {
        return isMatchedOnBoard;
    }

    @Override
    public String toString() {
        return String.format("PuzzlePiece{id=%d, rectangle=%s, currentPosition=%s}", id, rectangle, currentPosition);
    }

}
