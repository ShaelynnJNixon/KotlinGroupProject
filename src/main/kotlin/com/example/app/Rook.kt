package com.example.app

class Rook(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.ROOK

    var hasMoved = false // This is needed for determining if the king can castle with this rook
    val directions = arrayListOf(
        Pair(1, 0),     // Right
        Pair(-1, 0),    // Left
        Pair(0, 1),     // Up
        Pair(0, -1),    // Down
    )

    override fun canMoveTo(): ArrayList<Pair<Int, Int>> {
        val validMoves = ArrayList<Pair<Int,Int>>()

        for ((dirX, dirY) in directions) {
            for (i in 1..7) {
                val move = Pair((position.first + (dirX * i)),(position.second + (dirY * i)))

                // If move out of bounds, stop checking this direction
                if (move.first !in 0..7 || move.second !in 0..7) {
                    break
                }
                if (board.spotIsOccupied(move)) {
                    val otherPiece: Piece? = board.pieceAtPosition(move)
                    if (otherPiece != null) {
                        if (otherPiece.color != this.color) {
                            validMoves.add(move)
                            break // Cannot move behind enemy piece
                        }
                    } else {
                        break // Cannot move to or behind ally piece
                    }
                }
                validMoves.add(move)
            }
        }

        return validMoves
    }

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
        board.updatePosition(this, position)
        hasMoved = true
    }

    override fun threatens(): ArrayList<Pair<Int, Int>> {
        val threatens = ArrayList<Pair<Int,Int>>()

        for ((dirX, dirY) in directions) {
            for (i in 1..7) {
                val move = Pair((position.first + (dirX * i)),(position.second + (dirY * i)))

                // If move out of bounds, stop checking this direction
                if (move.first !in 0..7 || move.second !in 0..7) {
                    break
                }
                if (board.spotIsOccupied(move)) {
                    val otherPiece: Piece? = board.pieceAtPosition(move)
                    if (otherPiece != null) {
                        if (otherPiece.color != this.color) {
                            threatens.add(move)
                            break // Cannot threaten behind enemy piece
                        }
                    } else {
                        threatens.add(move)
                        break // Cannot threaten behind ally piece
                    }
                }
                threatens.add(move)
            }
        }

        return threatens
    }

//    /**
//     * Returns a boolean based on whether the rook has made any moves yet
//     * @return true if it has moved, false otherwise
//     */
//    fun getHasMoved(): Boolean {
//        return hasMoved
//    }
//
//    /**
//     * Function for setting the hasMoved property of this rook.
//     * This will only ever be set to true and likely only is used by the king's castle move.
//     * @param hasMoved a boolean of what this rooks hasMoved should be set to
//     */
//    fun setHasMoved(hasMoved: Boolean) {
//        this.hasMoved = hasMoved
//    }

    /**
     * Function for setting the position property of this rook
     * This will only ever be used because of a king's castle move.
     * @param position the position to set this rook to
     */
    fun updatePosition(position: Pair<Int, Int>) {
        this.position = position
    }
}