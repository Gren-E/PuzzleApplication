package com.pa.controller;

import com.pa.model.creator.GameCreator;
import com.pa.model.creator.factory.PieceShape;
import com.pa.model.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;

public class GameCreatorController {

    private static final Logger LOG = LoggerFactory.getLogger(GameCreatorController.class);

    private final GameCreator creator;

    public GameCreatorController() {
        creator = new GameCreator();
    }

    public Game buildGame() throws PAControllerException {
        try {
            return creator.buildGame();
        } catch (Exception e) {
            LOG.error("Cannot build a game.", e);
            throw new PAControllerException("Cannot build a game.", e);
        }
    }

    public void setRows(int rows) {
        creator.setRows(rows);
    }

    public void setColumns(int columns) {
        creator.setColumns(columns);
    }

    public void setShape(PieceShape pieceShape) {
        creator.setPieceShape(pieceShape);
    }

    public void setImage(Image image) {
        creator.setImage(image);
    }

    public void setMaxPuzzleWidth(int width) {
        creator.setMaxPuzzleWidth(width);
    }

    public void setMaxPuzzleHeight(int height) {
        creator.setMaxPuzzleHeight(height);
    }

}
