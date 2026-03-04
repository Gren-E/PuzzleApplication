package com.pa.view;

import com.gutil.gui.adapters.DragMouseAdapter;
import com.pa.model.puzzle.PuzzlePiece;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class PuzzleIconFactory {

    public static List<PuzzleIcon> createPuzzleIcons(PuzzlePiece[][] pieces, Image image) {
        List<PuzzleIcon> icons = new ArrayList<>();
        for (PuzzlePiece[] row : pieces) {
            for (PuzzlePiece piece : row) {
                PuzzleIcon icon = new PuzzleIcon(image, piece);
                DragMouseAdapter adapter = new DragMouseAdapter(icon);
                icon.addMouseListener(adapter);
                icon.addMouseMotionListener(adapter);
                icons.add(icon);
            }
        }

        return icons;
    }
}
