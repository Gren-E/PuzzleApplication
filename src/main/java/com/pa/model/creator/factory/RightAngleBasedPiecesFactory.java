package com.pa.model.creator.factory;

import com.gutil.ArrayUtil;
import com.pa.model.puzzle.PieceShape;
import com.pa.model.puzzle.PuzzlePiece;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class RightAngleBasedPiecesFactory extends PiecesFactory {

    private static final Random random = new Random();

    @Override
    public PuzzlePiece[][] generatePieces(int rows, int columns, int width, int height) {
        Point[][] pointsGrid = generatePointsOnGrid(rows, columns, width, height);
        PieceShape[][] pieceShapes = generatePieceAreas(pointsGrid);

        PuzzlePiece[][] pieces = new PuzzlePiece[rows][columns];
        ArrayUtil.setEach(pieces, (row, column) -> new PuzzlePiece(row * columns + column, pieceShapes[row][column]));
        return pieces;
    }

    protected Point[][] generatePointsOnGrid(int rows, int columns, int width, int height) {
        if (rows >= width) {
            throw new IllegalArgumentException(String.format("Too many rows (%s) for given width (%s).", rows, width));
        }

        if (columns >= height) {
            throw new IllegalArgumentException(String.format("Too many columns (%s) for given height (%s).", columns, height));
        }

        double cellWidth = width / (double) columns;
        double cellHeight = height / (double) rows;

        double currentX = 0;
        double currentY = 0;
        
        Point[][] pointsGrid = new Point[rows + 1][columns + 1];
        for (int row = 0 ; row < rows + 1; row++) {
            for (int column = 0 ; column < columns + 1; column++) {
                pointsGrid[row][column] = new Point((int) currentX, (int) currentY);
                currentX += cellWidth;
            }

            currentX = 0;
            currentY += cellHeight;
        }

        return pointsGrid;
    }

    protected PieceShape[][] generatePieceAreas(Point[][] pointsGrid) {
        PieceShapeOutline[][] outlines = new PieceShapeOutline[pointsGrid.length - 1][pointsGrid[0].length - 1];
        ArrayUtil.setEach(outlines, (row, column) -> new PieceShapeOutline((row + column) % 2 == 1));

        for (int i = 0; i < pointsGrid.length; ++i) {
            for (int j = 0; j < pointsGrid[i].length; ++j) {
                if (i < pointsGrid.length - 1) {
                    Point start = pointsGrid[i][j];
                    Point end = pointsGrid[i + 1][j];

                    Path2D pathVertical = generatePuzzlePieceEdge((i + j) % 2 == 0 ? end : start, (i + j) % 2 == 0 ? start : end, j == 0 || j == pointsGrid[i].length - 1);

                    PieceShapeOutline left = j != 0 ? outlines[i][j - 1] : null;
                    if (left != null) {
                        left.setEast(pathVertical);
                    }

                    PieceShapeOutline right = j != pointsGrid[i].length - 1 ? outlines[i][j] : null;
                    if (right != null) {
                        right.setWest(pathVertical);
                    }
                }

                if (j < pointsGrid[i].length - 1) {
                    Point start = pointsGrid[i][j];
                    Point end = pointsGrid[i][j + 1];

                    Path2D pathHorizontal = generatePuzzlePieceEdge((i + j) % 2 == 0 ? start : end, (i + j) % 2 == 0 ? end :start, i == 0 || i == pointsGrid.length - 1);

                    PieceShapeOutline up = i != 0 ? outlines[i - 1][j] : null;
                    if (up != null) {
                        up.setSouth(pathHorizontal);
                    }

                    PieceShapeOutline down = i != pointsGrid.length - 1 ? outlines[i][j] : null;
                    if (down != null) {
                        down.setNorth(pathHorizontal);
                    }
                }
            }
        }

        PieceShape[][] shapes = new PieceShape[pointsGrid.length - 1][pointsGrid[0].length - 1];
        ArrayUtil.setEach(shapes, (row, column) -> outlines[row][column].createShape());
        return shapes;
    }

    protected Path2D generatePuzzlePieceEdge(Point2D start, Point2D end, boolean isFlat) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());

        if (isFlat) {
            path.lineTo(end.getX(), end.getY());
            return path;
        }

        double diffX = end.getX() - start.getX();
        double diffY = end.getY() - start.getY();
        double startEndDistance = Math.hypot(diffX, diffY);

        if (startEndDistance == 0) {
            return path;
        }

        double bumpDepth = random.nextDouble(0.05, 0.14);
        boolean isInward = random.nextBoolean();
        if (isInward) {
            bumpDepth *= -1; // inward
        }

        double bumpStart = random.nextDouble(0.16, 0.35);
        double bumpMidStart = random.nextDouble(0.35, 0.45);
        double bumpMidEnd = random.nextDouble(0.55, 0.65);
        double bumpEnd = random.nextDouble(0.65, 0.84);

        double bumpStartX = start.getX() + bumpStart * diffX;
        double bumpStartY = start.getY() + bumpStart * diffY;

        double bumpMidStartX = start.getX() + bumpMidStart * diffX + bumpDepth * diffY;
        double bumpMidStartY = start.getY() + bumpMidStart * diffY + bumpDepth * diffX;

        double bumpMidEndX = start.getX() + bumpMidEnd * diffX + bumpDepth * diffY;
        double bumpMidEndY = start.getY() + bumpMidEnd * diffY + bumpDepth * diffX;

        double bumpEndX = start.getX() + bumpEnd * diffX;
        double bumpEndY = start.getY() + bumpEnd * diffY;

        path.lineTo(bumpStartX, bumpStartY);
        path.lineTo(bumpMidStartX, bumpMidStartY);
        path.lineTo(bumpMidEndX, bumpMidEndY);
        path.lineTo(bumpEndX, bumpEndY);
        //path.quadTo(bumpMidEndX, bumpMidEndY, bumpEndX, bumpEndY);
        path.lineTo(end.getX(), end.getY());

        return path;
    }

}
