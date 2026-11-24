package com.example.app

class Knight(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.KNIGHT

    override fun canMoveTo(): ArrayList<Pair<Int, Int>> {
        val validMoves = ArrayList<Pair<Int,Int>>()

        val candidateMoves = arrayListOf(
            Pair(position.first+2,position.second+1),
            Pair(position.first+2,position.second-1),
            Pair(position.first+1,position.second+2),
            Pair(position.first+1,position.second-2),
            Pair(position.first-2,position.second+1),
            Pair(position.first-2,position.second-1),
            Pair(position.first-1,position.second+2),
            Pair(position.first-1,position.second-2)
        )

        for (move in candidateMoves) {
            // If move out of bounds, skip it
            if (move.first !in 0..7 || move.second !in 0..7) {
                continue
            }
            if (board.spotIsOccupied(move)) {
                val otherPiece: Piece? = board.pieceAtPosition(move)
                if (otherPiece != null) {
                    if (otherPiece.color != this.color) {
                        validMoves.add(move)
                    }
                }
            }
            validMoves.add(move)
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

        val candidateMoves = arrayListOf(
            Pair(position.first+2,position.second+1),
            Pair(position.first+2,position.second-1),
            Pair(position.first+1,position.second+2),
            Pair(position.first+1,position.second-2),
            Pair(position.first-2,position.second+1),
            Pair(position.first-2,position.second-1),
            Pair(position.first-1,position.second+2),
            Pair(position.first-1,position.second-2)
        )

        for (move in candidateMoves) {
            // If move out of bounds, skip it
            if (move.first !in 0..7 || move.second !in 0..7) {
                continue
            }
            threatens.add(move)
        }

        return threatens
    }
}