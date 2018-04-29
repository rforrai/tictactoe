package com.example.tictactoe

import java.util.*

data class Board(
        private val bound: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MAX_VALUE),
        private val tiles: List<Tile> = ArrayList()) {


    fun markTile(position: Pair<Int, Int>, mark: String): Board =
            when {
                isInBounds(position) ->
                    getMark(position)?.let { this } ?: Board(bound, tiles + Tile(position, mark))

                else ->
                    throw IllegalArgumentException("Marking tile out of bounds is illegal")
            }


    fun getMark(position: Pair<Int, Int>): String? =
            getTile(position, tiles)?.mark


    fun getScore(mark: String): Int =
            getTilesMarkedAs(mark).map { getPatternMatchesCount(it, getTilesMarkedAs(mark)) }.sum()


    fun getBound(): Pair<Int, Int> =
            bound


    fun getTiles(): List<Tile> =
            tiles


    private fun isInBounds(position: Pair<Int, Int>): Boolean =
            position.first < bound.first
                    && position.second < bound.second
                    && position.first >= 0
                    && position.second >= 0


    private fun getTile(position: Pair<Int, Int>, tiles: List<Tile>): Tile? =
            tiles.find { it.position == position }


    private fun getTilesMarkedAs(mark: String): List<Tile> =
            tiles.filter { it.isMarkedAs(mark) }


    private fun getPatternMatchesCount(startTile: Tile, forTiles: List<Tile>): Int =
            getPatternMatchesCount(3, startTile, forTiles)


    private fun getPatternMatchesCount(patternLength: Int, startTile: Tile, forTiles: List<Tile>): Int =
            getWinningPatterns()
                    .map { matchesPattern(patternLength, startTile, forTiles, it) }
                    .map { if (it) 1 else 0 }
                    .sum()


    private fun getWinningPatterns(): List<Pair<Int, Int>> =
            listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(-1, 1))


    private fun plus(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Pair<Int, Int> =
            Pair(pair1.first + pair2.first, pair1.second + pair2.second)


    private fun matchesPattern(patternLength: Int, startTile: Tile, forTiles: List<Tile>, pattern: Pair<Int, Int>): Boolean =
            when {
                patternLength > 1 ->
                    getTile(plus(startTile.position, pattern), forTiles)
                            ?.let { matchesPattern(patternLength - 1, it, forTiles, pattern) } ?: false

                patternLength == 1 ->
                    true

                else ->
                    throw IllegalArgumentException("Size less than one is not supported")
            }


    fun isEmpty(): Boolean =
            tiles.isEmpty()
}


class Tile(val position: Pair<Int, Int>, val mark: String) {

    fun isMarkedAs(mark: String): Boolean =
            this.mark == mark
}