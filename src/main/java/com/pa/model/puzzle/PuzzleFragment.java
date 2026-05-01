package com.pa.model.puzzle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PuzzleFragment {

    private final int id;
    private final Set<PuzzlePiece> pieces;

    public PuzzleFragment(int id) {
        this.id = id;
        pieces = new HashSet<>();
    }

    public void addPiece(PuzzlePiece piece) {
        pieces.add(piece);
    }

    public PuzzlePiece[] getPieces() {
        return pieces.toArray(new PuzzlePiece[0]);
    }

    public boolean hasPiece(PuzzlePiece piece) {
        return pieces.contains(piece);
    }

    public int countPieces() {
        return pieces.size();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PuzzleFragment other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
