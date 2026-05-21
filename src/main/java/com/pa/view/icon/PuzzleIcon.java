package com.pa.view.icon;

import com.pa.model.puzzle.PuzzleFragment;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

public class PuzzleIcon extends JLabel {

    private final PuzzleFragment fragment;

    private final Image image;
    private final Rectangle imageRectangle;

    private boolean drawBorder;
    private boolean allowMovement;

    public PuzzleIcon(Image image, PuzzleFragment fragment) {
        this.fragment = fragment;
        this.image = image;
        this.imageRectangle = new Rectangle(image.getWidth(null), image.getHeight(null));
        this.drawBorder = true;
        this.allowMovement = true;
    }

    public void enableDrawingBorder(boolean shouldEnable) {
        drawBorder = shouldEnable;
    }

    public void enableMovement(boolean shouldMove) {
        allowMovement = shouldMove;
    }

    public boolean canBeMoved() {
        return allowMovement;
    }

    public PuzzleFragment getFragment() {
        return fragment;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fragment == null) {
            return;
        }

        Rectangle fragmentBounds = fragment.getShape().getBounds();
        Point fragmentNWCorner = new Point(fragmentBounds.x, fragmentBounds.y);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.translate(-fragmentNWCorner.x, -fragmentNWCorner.y);

        g2.setPaint(new TexturePaint((BufferedImage) image, imageRectangle));
        g2.fill(fragment.getShape());

        if (drawBorder) {
            g2.setColor(Color.WHITE);
            g2.draw(fragment.getShape());
        }

        g2.translate(fragmentNWCorner.x, fragmentNWCorner.y);
    }

    @Override
    public String toString() {
        return "PuzzleIcon{fragment=" + fragment + "}";
    }

}
