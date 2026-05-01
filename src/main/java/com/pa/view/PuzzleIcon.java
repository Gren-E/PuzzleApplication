package com.pa.view;

import com.pa.model.puzzle.PuzzlePiece;

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

    private final PuzzlePiece piece;
    private final Image image;

    public PuzzleIcon(Image image, PuzzlePiece piece) {
        this.piece = piece;
        this.image = image;
    }

    public PuzzlePiece getPiece() {
        return piece;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (piece == null) {
            return;
        }

        Point nwCorner = piece.getNWCorner();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.translate(-nwCorner.x, -nwCorner.y);

        g2.setPaint(new TexturePaint((BufferedImage) image, new Rectangle(image.getWidth(null), image.getHeight(null))));
        g2.fill(piece.getShape());

        if (!piece.isSet()) {
            g2.setColor(Color.WHITE);
            g2.draw(piece.getShape());
        }

        g2.translate(nwCorner.x, nwCorner.y);
    }


}
