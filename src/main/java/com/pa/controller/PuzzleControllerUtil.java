package com.pa.controller;

import com.pa.model.puzzle.PuzzleData;
import com.pa.model.puzzle.PuzzlePiece;
import com.pa.view.PuzzleIcon;

import java.awt.Point;

public class PuzzleControllerUtil {

    public static void adjustPiecePosition(PuzzleData puzzleData, PuzzleIcon icon, Point position, Point boardOffset) {
        int pieceOrdinal = icon.getPiece().getOrdinal();
        puzzleData.setPiecePosition(pieceOrdinal, position.x, position.y);
        icon.setLocation(new Point(position.x - boardOffset.x, position.y - boardOffset.y));
    }

    public static boolean arePointsEqual(Point first, Point second, int tolerance) {
        return Math.abs(first.x - second.x) <= tolerance && Math.abs(first.y - second.y) <= tolerance;
    }

}
