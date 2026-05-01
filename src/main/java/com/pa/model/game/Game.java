package com.pa.model.game;

import com.pa.model.puzzle.PuzzleData;

public class Game {

    private final PuzzleData puzzleData;

    public Game(PuzzleData puzzleData) {
        this.puzzleData = puzzleData;
    }

    public PuzzleData getPuzzleData() {
        return puzzleData;
    }

}
