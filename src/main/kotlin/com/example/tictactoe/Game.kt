package com.example.tictactoe

data class Game(
        private val board: Board = Board(),
        private val players: List<Player> = ArrayList(),
        private val activePlayer: Player? = null) {

    fun start(): Game =
            Game(Board())


    fun isEmpty(): Boolean =
            board.isEmpty()


    fun addPlayer(player: Player): Game =
            when {
                players.find { it.id == player.id } == null && players.find { it.mark == player.mark } == null ->
                    Game(board, players + player, (players + player).sortedBy { it.mark }.first())

                else ->
                    throw IllegalArgumentException("Cannot add same player twice")
            }


    fun getPlayer(mark: String): Player? =
            players.find { it.mark == mark }


    fun getActivePlayer(): Player? =
            activePlayer


    fun getPlayers(): List<Player> =
            players


    fun getBoard(): Board =
            board


    fun hasMark(position: Pair<Int, Int>, mark: String): Boolean =
            board.getMark(position) == mark


    fun mark(position: Pair<Int, Int>): Game =
            Game(
                    getActivePlayer()?.let { board.markTile(position, it.mark) } ?: board,
                    players,
                    getNextPlayer())


    private fun getNextPlayer(): Player? {
        if(players.isEmpty()) {
            throw IllegalArgumentException("No players in game!")
        }

        val sorted = players.sortedBy { it.mark }
        val index = sorted.indexOf(activePlayer) + 1

        return sorted[if (index >= sorted.size) 0 else index]
    }
}


data class Player(val mark: String, val id: Int)