package com.example.app

enum class PieceColor { WHITE, BLACK}
enum class PieceType { KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN }

abstract class Piece(open val board: Board, open val color: PieceColor, open var position: Pair<Int, Int>) {

    // Values
    abstract val type: PieceType

    // Functions
    /**
     * Returns an ArrayList of spots (Pair(Int, Int)) that the piece could move to.
     */
    abstract fun canMoveTo(): ArrayList<Pair<Int, Int>> // Returns an array of Int pairs

    /**
     * Returns a boolean that is whether or not this piece can move
     * @return true if the piece can move, false otherwise
     */
    fun canMove(): Boolean {
        val validMoves = canMoveTo()
        return validMoves.isNotEmpty()
    }

    /**
     * Updates a piece's position with new position. If it is an occupied spot it will capture the occupying piece.
     * @param position The position to move to
     */
    open fun moveTo(position: Pair<Int, Int>) {
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
        board.updatePosition(this, position)
    }

    /**
     * Returns an ArrayList of spots that this piece threatens (can capture). A piece does not need to be on the spot
     * for it to count (that way we can calculate spots that might put a king in check if it moved there for example)
     * @return ArrayList of spots that this piece threatens (can capture)
     */
    abstract open fun threatens(): ArrayList<Pair<Int, Int>>

    override fun toString(): String {
        return "Color: $color\nType: $type\nPosition: $position"
    }
}