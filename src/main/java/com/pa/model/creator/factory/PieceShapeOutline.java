package com.pa.model.creator.factory;

import com.pa.model.puzzle.PieceShape;

import java.awt.Point;
import java.awt.geom.Path2D;

public class PieceShapeOutline {

    private Point nwCorner;

    private Path2D north;
    private Path2D east;
    private Path2D south;
    private Path2D west;

    private final boolean inverseDirection;

    public PieceShapeOutline(boolean inverseDirection) {
        this.inverseDirection = inverseDirection;
    }

    public void setNWCorner(Point corner) {
        this.nwCorner = corner;
    }

    public void setNorth(Path2D north) {
        this.north = north;
    }

    public void setEast(Path2D east) {
        this.east = east;
    }

    public void setSouth(Path2D south) {
        this.south = south;
    }

    public void setWest(Path2D west) {
        this.west = west;
    }

    public PieceShape createShape() {
        Path2D result = new Path2D.Double();
        if (inverseDirection) {
            result.append(west, true);
            result.append(south, true);
            result.append(east, true);
            result.append(north, true);
        } else {
            result.append(north, true);
            result.append(east, true);
            result.append(south, true);
            result.append(west, true);
        }

        result.closePath();
        return new PieceShape(nwCorner, result);
    }

}
