package com.pa.gui;

import com.pa.puzzle.PuzzlePiece;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PuzzleIcon extends JLabel {

    private final PuzzlePiece piece;

    public PuzzleIcon(Image image, PuzzlePiece piece) {
        this.piece = piece;

        Rectangle rectangle = piece.getRectangle();
        setIcon(new ImageIcon(((BufferedImage) image).getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height)));
    }

    public PuzzlePiece getPiece() {
        return piece;
    }
}
