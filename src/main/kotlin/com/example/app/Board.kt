package com.example.app

class Board {
    // These are some functions I used in the Piece class
    /**
     * Initializes the pieces onto the board
     */
    private val positions: Array<Array<Piece?>> = Array(8) { arrayOfNulls<Piece>(8) } // 8*8 board
    fun initBoard() {
        for (i in 0..7) {
            val tempPawn = Pawn(this, PieceColor.WHITE, Pair(i, 6))
            positions[6][i] = tempPawn
        }
        for (i in 0..7) {
            val tempPawn = Pawn(this, PieceColor.BLACK, Pair(i, 1))
            positions[1][i] = tempPawn
        }
        val firstWhiteRook = Rook(this, PieceColor.WHITE, Pair(0, 7))
        positions[0][7] = firstWhiteRook
        val firstWhiteKnight = Knight(this, PieceColor.WHITE, Pair(1, 7))
        positions[1][7] = firstWhiteKnight
        val firstWhiteBishop = Bishop(this, PieceColor.WHITE, Pair(2, 7))
        positions[2][7] = firstWhiteBishop
        val whiteQueen = Queen(this, PieceColor.WHITE, Pair(3, 7))
        positions[3][7] = whiteQueen
        val whiteKing = King(this, PieceColor.WHITE, Pair(4, 7))
        positions[4][7] = whiteKing
        val secondWhiteBishop = Bishop(this, PieceColor.WHITE, Pair(5, 7))
        positions[5][7] = secondWhiteBishop
        val secondWhiteKnight = Knight(this, PieceColor.WHITE, Pair(6, 7))
        positions[6][7] = secondWhiteKnight
        val secondWhiteRook = Rook(this, PieceColor.WHITE, Pair(7, 7))
        positions[7][7] = secondWhiteRook
        val firstBlackRook = Rook(this, PieceColor.BLACK, Pair(0, 0))
        positions[0][0] = firstBlackRook
        val firstBlackKnight = Knight(this, PieceColor.BLACK, Pair(1, 0))
        positions[1][0] = firstBlackKnight
        val firstBlackBishop = Bishop(this, PieceColor.BLACK, Pair(2, 0))
        positions[2][0] = firstBlackBishop
        val blackQueen = Queen(this, PieceColor.BLACK, Pair(3, 0))
        positions[3][0] = blackQueen
        val blackKing = King(this.PieceColor.BLACK, Pair(4, 0))
        positions[4][0] = BlackKing
        val secondBlackBishop = Bishop(this, PieceColor.BLACK, Pair(5, 0))
        positions[5][0] = secondBlackBishop
        val secondBlackKnight = Knight(this, PieceColor.BLACK, Pair(6, 0))
        positions[6][0] = secondBlackKnight
        val secondBlackRook = Rook(this, PieceColor.BLACK, Pair(7, 0))
        positions[7][0] = secondBlackRook
    }

    /**
     * Checks whether a given spot is free
     * @param position The position to check
     * @return true if spot is free, false otherwise
     */
    fun spotIsFree(position: Pair<Int, Int>): Boolean {
        return positions[position.first][position.second] == null
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
        return positions[position.first][position.second]
    }

    /**
     * Captures the piece at the given position.
     * @param position The position of the piece to capture
     */
    fun capture(position: Pair<Int, Int>) {
        positions[position.first][position.second] = null
    }

    /**
     * Captures the piece at the given position.
     * @param tempPiece The piece to replace after nulling the captured piece
     * @param position The position of the piece to capture
     */
    fun updatePosition(tempPiece: Piece, position: Pair<Int, Int>) {
        positions[position.first][position.second] = tempPiece
    }

    /**
     * Captures a specific piece
     * @param piece The piece to be captured
     */
    fun capture(piece: Piece) {
        capture(piece.position)
    }
}