package com.example.app

class Queen(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.QUEEN

    val directions = arrayListOf(
        Pair(0, 1),     // Up
        Pair(1, 1),     // Up right
        Pair(1, 0),     // Right
        Pair(1, -1),    // Down right
        Pair(0, -1),    // Down
        Pair(-1, -1),   // Down left
        Pair(-1, 0),    // Left
        Pair(-1, 1),    // Up left
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
}