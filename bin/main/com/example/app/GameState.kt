package com.example.app

fun PieceColor.opposite(): PieceColor =
    if (this == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE


class GameState(val board: Board = Board()) {

    //only this class can change the turn
    var currentTurn: PieceColor = PieceColor.WHITE
        private set

    // Moves in algebraic notation
    private val moveHistory = mutableListOf<String>()

    // Read-only view for outside code
    val history: List<String>
        get() = moveHistory

    private val inCheck = mutableMapOf(
        PieceColor.WHITE to false,
        PieceColor.BLACK to false
    )

    fun isInCheck(color: PieceColor): Boolean = inCheck.getValue(color)

    fun setCheck(color: PieceColor, value: Boolean) {
        inCheck[color] = value
    }

    // Records a finished move and passes the turn to the other player
    fun recordMove(notation: String) {
        moveHistory.add(notation)
        currentTurn = currentTurn.opposite()
    }
}

