package com.example.app

class Pawn(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.PAWN

    // Determine the direction the pawn should move based on where it starts on the board
    val verticalDirection = if (position.second > 4) 1 else -1
    var hasMoved = false // Used when determining if the pawn can move 2 spots for its first move

    override fun canMoveTo(): ArrayList<Pair<Int, Int>> {
        val validMoves = ArrayList<Pair<Int,Int>>()
        if (!hasMoved) { // If it hasn't moved, has option to move 2 spaces
            if (board.spotIsFree(Pair(position.first, position.second+(2*verticalDirection)))) {
                validMoves.add(Pair(position.first, position.second+(2*verticalDirection)))
            }
        }
        if (board.spotIsFree(Pair(position.first, position.second+(1*verticalDirection)))) {
            validMoves.add(Pair(position.first, position.second+(1*verticalDirection)))
        }
        // Possible pawn diagonal moves (captures)
        if (board.spotIsOccupied(Pair(position.first-1, position.second+(1*verticalDirection)))) {
            val otherPiece: Piece? = board.pieceAtPosition(position)
            if (otherPiece != null) {
                if (otherPiece.color != this.color) {
                    validMoves.add(Pair(position.first - 1, position.second + (1*verticalDirection)))
                }
            }
        }
        if (board.spotIsOccupied(Pair(position.first+1, position.second+(1*verticalDirection)))) {
            val otherPiece: Piece? = board.pieceAtPosition(position)
            if (otherPiece != null) {
                if (otherPiece.color != this.color) {
                    validMoves.add(Pair(position.first + 1, position.second + (1*verticalDirection)))
                }
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
        if (position.second == 7) {
            promote()
        }
    }

    override fun threatens(): ArrayList<Pair<Int, Int>> {
        val threatens = ArrayList<Pair<Int,Int>>()
        threatens.add(Pair(position.first - 1, position.second + (1*verticalDirection)))
        threatens.add(Pair(position.first + 1, position.second + (1*verticalDirection)))
    }

    /**
     * Promotes this pawn after it has reached the end of the board
     */
    fun promote() {
        board.promote(this)
    }
}