package com.example.app

class BottomPawn(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.PAWN

    /**
     * Returns an ArrayList of spots (Pair(Int, Int)) that the piece could move to.
     */
    override fun canMoveTo(): ArrayList<Pair<Int, Int>> {
        val validMoves = ArrayList<Pair<Int,Int>>()
        if (position.second == 1) { // If it hasn't moved, has option to move 2 spaces
            if (board.spotIsFree(Pair(position.first, position.second+2))) {
                validMoves.add(Pair(position.first, position.second+2))
            }
        }
        if (board.spotIsFree(Pair(position.first, position.second+1))) {
            validMoves.add(Pair(position.first, position.second+1))
        }
        // Possible pawn diagonal moves (captures)
        if (board.spotIsOccupied(Pair(position.first-1, position.second+1))) {
            val otherPiece: Piece? = board.pieceAtPosition(position)
            if (otherPiece != null) {
                if (otherPiece.color != this.color) {
                    validMoves.add(Pair(position.first - 1, position.second + 1))
                }
            }
        }
        if (board.spotIsOccupied(Pair(position.first+1, position.second+1))) {
            val otherPiece: Piece? = board.pieceAtPosition(position)
            if (otherPiece != null) {
                if (otherPiece.color != this.color) {
                    validMoves.add(Pair(position.first + 1, position.second + 1))
                }
            }
        }
        return validMoves
    }

    /**
     * Boolean of whether this piece has any valid moves.
     */
    override fun canMove(): Boolean {
        val validMoves = canMoveTo()
        return validMoves.isNotEmpty()
    }

    /**
     * Updates a piece's position with new position. If it is an occupied spot it will capture the occupying piece.
     * @param position The position to move to
     */
    override fun moveTo(position: Pair<Int, Int>) {
        if (position.first !in 0..7 || position.second !in 0..7) {
            error("Position out of bounds")
        }
        if (board.spotIsOccupied(position)) {
            val otherPiece = board.pieceAtPosition(position)
            if (otherPiece != null) {
                if (otherPiece.color != this.color) {
                    board.capture(otherPiece)
                } else {
                    error("Collision with ally piece")
                }
            } else {
                error("Unexpected null piece return from pieceAtPosition()")
            }
        }
        this.position = position
    }
}