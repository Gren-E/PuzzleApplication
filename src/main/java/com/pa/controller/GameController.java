package com.pa.controller;

import com.pa.model.game.Game;
import com.pa.model.puzzle.PuzzlePiece;

import java.awt.Image;

public class GameController {

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public int getRows() {
        return game != null ? game.getRows() : 0;
    }

    public int getColumns() {
        return game != null ? game.getColumns() : 0;
    }

    public PuzzlePiece[][] getPieces() {
        return game != null ? game.getPieces() : new PuzzlePiece[0][0];
    }

    public Image getImage() {
        return game != null ? game.getImage() : null;
    }

    public void regularizePieces() {
        if (game != null) {
            game.regularizePieces();
        }
    }

}
