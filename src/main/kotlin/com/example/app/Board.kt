package com.example.app

class Board {
    // These are some functions I used in the Piece class
    /**
     * Checks whether a given spot is free
     * @param position The position to check
     * @return true if spot is free, false otherwise
     */
    fun spotIsFree(position: Pair<Int, Int>): Boolean {
        //TODO:
        return true
    }

    /**
     * Checks whether a given spot is occupied
     * @param position The position to check
     * @return true if spot is taken, false otherwise
     */
    fun spotIsOccupied(position: Pair<Int, Int>): Boolean {
        return !spotIsFree(position)
    }

    /**
     * Returns a piece at a given position (Pair(Int, Int))
     * @param position The position to check for a piece
     * @return The piece at the position, or null if none
     */
    fun pieceAtPosition(position: Pair<Int, Int>): Piece? {
        //TODO:
        return null
    }

    /**
     * Captures the piece at the given position.
     * @param position The position of the piece to capture
     */
    fun capture(position: Pair<Int, Int>) {
        //TODO:
    }

    /**
     * Captures a specific piece
     * @param piece The piece to be captured
     */
    fun capture(piece: Piece) {
        capture(piece.position)
    }
}