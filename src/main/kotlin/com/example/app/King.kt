package com.example.app

class King(override val board: Board, override val color: PieceColor, override var position: Pair<Int, Int>) : Piece(board, color, position) {
    override val type: PieceType = PieceType.KING

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
    val oppositeColor = if (this.color == PieceColor.WHITE) {
        PieceColor.BLACK
    } else {
        PieceColor.WHITE
    }
    var hasMoved = false

    override fun canMoveTo(): ArrayList<Pair<Int, Int>> {
        val validMoves = ArrayList<Pair<Int,Int>>()

        // Check standard moves
        for ((dirX, dirY) in directions) {
            val move = Pair((position.first + (dirX)),(position.second + (dirY)))

            // If move out of bounds, continue to next
            if (move.first !in 0..7 || move.second !in 0..7) {
                continue
            }

            // Check if spot is treatened by any other pieces
            if (board.spotIsThreatenedByColor(move, oppositeColor)) {
                continue
            }

            // Check if enemy (can capture, valid move)
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

        // Check for possible castling moves
        if (!hasMoved) {
            val (x, y) = position
            if ( // If the two spots to the left of the king are empty and not threatened
                (board.spotIsFree(Pair(x-1, y)) && board.spotIsFree(Pair(x-2, y))) &&
                (!board.spotIsThreatenedByColor(Pair(x-1, y), oppositeColor) && !board.spotIsThreatenedByColor(Pair(x-2, y), oppositeColor))
                ) {
                if (board.spotIsOccupied(Pair(x-3, y)) && board.pieceAtPosition(Pair(x-3, y))!!.type == PieceType.ROOK) { // If the spot third to the left of the king is a rook
                    val rook = board.pieceAtPosition(Pair(x-3, y))
                    if (!rook.hasMoved()) {
                        validMoves.add(Pair(x-2, y))
                    }
                } else if (board.spotIsFree(Pair(x-3, y))) { // If the third spot to the left of the king is empty
                    if (board.spotIsOccupied(Pair(x-4, y)) && board.pieceAtPosition(Pair(x-4, y))!!.type == PieceType.ROOK) { // If the spot fourth to the left of the king is a rook
                        val rook = board.pieceAtPosition(Pair(x-4, y))
                        if (!rook.hasMoved()) {
                            validMoves.add(Pair(x-2, y))
                        }
                    }
                }
            }
            if ( // If the two spots to the right of the king are empty and not threatened
                (board.spotIsFree(Pair(x+1, y)) && board.spotIsFree(Pair(x+2, y))) &&
                (!board.spotIsThreatenedByColor(Pair(x+1, y), oppositeColor) && !board.spotIsThreatenedByColor(Pair(x+2, y), oppositeColor))
            ) {
                if (board.spotIsOccupied(Pair(x+3, y)) && board.pieceAtPosition(Pair(x+3, y))!!.type == PieceType.ROOK) { // If the spot third to the right of the king is a rook
                    val rook = board.pieceAtPosition(Pair(x+3, y))
                    if (!rook.hasMoved()) {
                        validMoves.add(Pair(x+2, y))
                    }
                } else if (board.spotIsFree(Pair(x+3, y))) { // If the third spot to the right of the king is empty
                    if (board.spotIsOccupied(Pair(x+4, y)) && board.pieceAtPosition(Pair(x+4, y))!!.type == PieceType.ROOK) { // If the spot fourth to the right of the king is a rook
                        val rook = board.pieceAtPosition(Pair(x+4, y))
                        if (!rook.hasMoved()) {
                            validMoves.add(Pair(x+2, y))
                        }
                    }
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

        if (position.first == this.position.first-2) { // Castle left
            val rook = if (
                board.spotIsOccupied(Pair(this.position.first-3,this.position.second)) &&
                board.pieceAtPosition(Pair(this.position.first-3,this.position.second))!!.type == PieceType.ROOK)
            {
                rook = board.pieceAtPosition(Pair(this.position.first-3,this.position.second))!!
            } else {
                rook = board.pieceAtPosition(Pair(this.position.first-4,this.position.second))!!
            }
            rook.setPosition(Pair(this.position.first-1,this.position.second))
            board.updatePosition(rook, Pair(this.position.first-1,this.position.second))
            rook.setHasMoved(true)
        } else if (position.first == this.position.first+2) { // Castle right
            val rook = if (
                board.spotIsOccupied(Pair(this.position.first+3,this.position.second)) &&
                board.pieceAtPosition(Pair(this.position.first+3,this.position.second))!!.type == PieceType.ROOK) {
                board.pieceAtPosition(Pair(this.position.first+3,this.position.second))!!
            } else {
                board.pieceAtPosition(Pair(this.position.first+4,this.position.second))!!
            }
            rook.setPosition(Pair(this.position.first+1,this.position.second))
            board.updatePosition(rook, Pair(this.position.first+1,this.position.second))
            rook.setHasMoved(true)
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
}