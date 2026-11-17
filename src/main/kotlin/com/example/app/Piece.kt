package com.example.app

enum class PieceColor { WHITE, BLACK}
enum class PieceType { KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN }

//abstract class Piece(val owner: Player, val color: PieceColor, val position: Pair(Int, Int)) {
abstract class Piece(open val board: Board, open val color: PieceColor, open var position: Pair<Int, Int>) {

    // Values
    abstract val type: PieceType

    // Functions
    abstract fun canMoveTo(): ArrayList<Pair<Int, Int>> // Returns an array of Int pairs
    abstract fun canMove(): Boolean
    abstract fun moveTo(position: Pair<Int, Int>)

    override fun toString(): String {
        return "Color: $color\nType: $type\nPosition: $position"
    }
}