package com.pa.model.puzzle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PuzzleFragment {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleFragment.class);

    private final int id;
    private final Set<PuzzlePiece> pieces;
    private final Set<Integer> neighbouringOrdinals;
    private Area shape;
    private boolean isFinalized;

    public PuzzleFragment(int id) {
        this.id = id;
        pieces = new HashSet<>();
        neighbouringOrdinals = new HashSet<>();
        reshape();
    }

    public void reshape() {
        shape = new Area();
        for (PuzzlePiece piece : pieces) {
            shape.add(new Area(piece.getShape()));
        }
    }

    public Shape getShape() {
        return shape;
    }

    public void addPiece(PuzzlePiece piece) {
        pieces.add(piece);
        neighbouringOrdinals.remove(piece.getOrdinal());
        for (int newNeighbour : piece.getNeighbouringOrdinals()) {
            if (!hasPiece(newNeighbour)) {
                neighbouringOrdinals.add(newNeighbour);
            }
        }

        LOG.info("Piece {} added to the fragment {}.", piece, this);
        reshape();
    }

    public PuzzlePiece[] getPieces() {
        return pieces.toArray(new PuzzlePiece[0]);
    }

    public boolean hasPiece(PuzzlePiece piece) {
        return pieces.contains(piece);
    }

    public boolean hasPiece(int ordinal) {
        return pieces.stream().mapToInt(PuzzlePiece::getOrdinal).anyMatch(o -> o == ordinal);
    }

    public int countPieces() {
        return pieces.size();
    }

    public void removeAllPieces() {
        pieces.clear();
    }

    public int[] getBorderingPiecesOrdinals() {
        return neighbouringOrdinals.stream().mapToInt(n -> n).toArray();
    }

    public void markAsFinalized() {
        isFinalized = true;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PuzzleFragment other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PuzzleFragment{id=" + id + "}";
    }

}
