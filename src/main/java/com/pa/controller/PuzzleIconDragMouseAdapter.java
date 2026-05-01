package com.pa.controller;

import com.gutil.gui.adapters.DragMouseAdapter;
import com.pa.view.PuzzleIcon;

import java.awt.event.MouseEvent;

public class PuzzleIconDragMouseAdapter extends DragMouseAdapter {

    private final PuzzleIcon icon;
    private final PuzzleController controller;

    public PuzzleIconDragMouseAdapter(PuzzleIcon icon, PuzzleController controller) {
        super(icon);
        this.icon = icon;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (icon.canBeMoved()) {
            super.mousePressed(event);
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (icon.canBeMoved()) {
            super.mouseDragged(event);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (icon.canBeMoved()) {
            super.mouseReleased(event);
            controller.handlePuzzleIconPositionChange(icon);
        }
    }

}
