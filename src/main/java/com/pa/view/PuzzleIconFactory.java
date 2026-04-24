package com.pa.view;

import com.pa.model.puzzle.PuzzlePiece;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class PuzzleIconFactory {

    public static List<PuzzleIcon> createPuzzleIcons(PuzzlePiece[][] pieces, Image image) {
        List<PuzzleIcon> icons = new ArrayList<>();
        for (PuzzlePiece[] row : pieces) {
            for (PuzzlePiece piece : row) {
                PuzzleIcon icon = new PuzzleIcon(image, piece);
                icons.add(icon);
            }
        }

        return icons;
    }
}
