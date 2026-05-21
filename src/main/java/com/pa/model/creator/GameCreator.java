package com.pa.model.creator;

import com.gutil.gui.ImageUtil;
import com.gutil.gui.ResizeQuality;
import com.pa.model.creator.factory.PieceShape;
import com.pa.model.creator.factory.PuzzleFactory;
import com.pa.model.game.Game;
import com.pa.model.puzzle.PuzzleData;

import java.awt.Image;
import java.awt.Point;

public class GameCreator {

    private Image image;

    private int rows;
    private int columns;

    private int puzzleWidth;
    private int puzzleHeight;

    private PieceShape pieceShape;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setPieceShape(PieceShape pieceShape) {
        this.pieceShape = pieceShape;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Game buildGame() {
        if (validateGameParameters()) {
            return new Game(generatePuzzleData());
        }
        throw new IllegalStateException("Cannot create the game - incorrect parameters.");
    }

    private boolean validateGameParameters() {
        return image != null && rows >= 1 && columns >= 1 && rows * columns > 1;
    }

    public PuzzleData generatePuzzleData() {
        if (image != null && (puzzleWidth > 0 || puzzleHeight > 0)) {
            int targetHeight = 0;
            int targetWidth = 0;
            double puzzleProportion = image.getWidth(null) / (double) image.getHeight(null);

            if (puzzleWidth / (double) puzzleHeight > puzzleProportion) {
                targetHeight = puzzleHeight;
                targetWidth = (int) (puzzleHeight * puzzleProportion);
            } else {
                targetWidth = puzzleWidth;
                targetHeight = (int) (puzzleWidth / puzzleProportion);
            }

            image = ImageUtil.resize(image, targetWidth, targetHeight, ResizeQuality.HIGH);
        }

        PuzzleFactory factory = PuzzleFactory.getFactory(pieceShape);
        return factory.generatePuzzle(rows, columns, image);
    }

    public void setMaxPuzzleWidth(int width) {
        puzzleWidth = width;
    }

    public void setMaxPuzzleHeight(int height) {
        puzzleHeight = height;
    }

}
