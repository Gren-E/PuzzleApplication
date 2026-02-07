package com.pa.game;

import java.awt.Image;

public class Game {

    private final Image image;

    private final int rows;
    private final int columns;

    public Game(Image image, int rows, int columns) {
        this.image = image;
        this.rows = rows;
        this.columns = columns;
    }

    public Image getImage() {
        return image;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfPieces() {
        return rows * columns;
    }

}
