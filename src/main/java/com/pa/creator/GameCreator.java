package com.pa.creator;

import com.pa.game.Game;

import java.awt.Image;

public class GameCreator {

    private Image image;

    private int rows;
    private int columns;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Image getImage() {
        return image;
    }

    public Game buildGame() {
        if (validateGameParameters()) {
            return new Game(image, rows, columns);
        }
        throw new IllegalStateException("Cannot create the game - incorrect parameters.");
    }

    private boolean validateGameParameters() {
        return image != null && rows >= 1 && columns >= 1 && rows * columns > 1;
    }

}
