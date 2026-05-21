package com.pa.view.icon;

import com.pa.model.puzzle.PuzzleFragment;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class PuzzleIconFactory {

    public static List<PuzzleIcon> createPuzzleIcons(PuzzleFragment[] fragments, Image image) {
        List<PuzzleIcon> icons = new ArrayList<>();
        for (PuzzleFragment fragment : fragments) {
            PuzzleIcon icon = new PuzzleIcon(image, fragment);
            if (fragment.isFinalized()) {
                icon.enableMovement(false);
                icon.enableDrawingBorder(false);
            }

            icons.add(icon);
        }

        return icons;
    }

}
