package com.example.tictactoe

import org.assertj.core.api.Assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*

class BoardSpec : Spek({
    describe("Board") {

        it("adds mark on empty tile") {
            val board = Board()

            val tile = board.markTile(Pair(1, 2), "X").getMark(Pair(1, 2))

            assertThat(tile).isEqualTo("X")
        }


        it("doesn't add mark on non empty tile") {
            val board = Board()

            val tile = board
                    .markTile(Pair(1, 2), "O")
                    .markTile(Pair(1, 2), "X")
                    .getMark(Pair(1, 2))

            assertThat(tile).isEqualTo("O")
        }


        it("doesn't allow insert mark out of set bound") {
            assertThatThrownBy {
                Board(Pair(3, 3))
                        .markTile(Pair(3, 0), "X")
            }
            assertThatThrownBy {
                Board(Pair(3, 3))
                        .markTile(Pair(0, 3), "X")
            }
            assertThatThrownBy {
                Board(Pair(3, 3))
                        .markTile(Pair(-1, 2), "X")
            }
            assertThatThrownBy {
                Board(Pair(3, 3))
                        .markTile(Pair(1, -2), "X")
            }
        }


        it("returns mark when there is one on tile") {
            val board = Board()
                    .markTile(Pair(1, 2), "O")

            val tile = board.getMark(Pair(1, 2))

            assertThat(tile).isEqualTo("O")
        }


        it("returns null when there is no mark on tile") {
            val board = Board()
                    .markTile(Pair(1, 2), "O")

            val tile = board.getMark(Pair(3, 4))

            assertThat(tile).isNull()
        }


        it("returns current score for horizontal line") {
            val board = Board()
                    .markTile(Pair(0, 0), "O")
                    .markTile(Pair(1, 0), "O")
                    .markTile(Pair(2, 0), "O")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(1)
            assertThat(scoreX).isEqualTo(0)
        }


        it("returns current score for vertical line") {
            val board = Board()
                    .markTile(Pair(0, 0), "O")
                    .markTile(Pair(0, 1), "O")
                    .markTile(Pair(0, 2), "O")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(1)
            assertThat(scoreX).isEqualTo(0)
        }


        it("returns score of 2 for vertical line of legth 4") {
            val board = Board()
                    .markTile(Pair(0, 0), "O")
                    .markTile(Pair(0, 1), "O")
                    .markTile(Pair(0, 2), "O")
                    .markTile(Pair(0, 3), "O")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(2)
            assertThat(scoreX).isEqualTo(0)
        }


        it("returns current score for diagonal line 1") {
            val board = Board()
                    .markTile(Pair(0, 0), "O")
                    .markTile(Pair(1, 1), "O")
                    .markTile(Pair(2, 2), "O")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(1)
            assertThat(scoreX).isEqualTo(0)
        }


        it("returns current score for diagonal line 2") {
            val board = Board()
                    .markTile(Pair(3, 0), "O")
                    .markTile(Pair(2, 1), "O")
                    .markTile(Pair(1, 2), "O")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(1)
            assertThat(scoreX).isEqualTo(0)
        }


        it("returns current score for all lines combined") {
            val board = Board()
                    .markTile(Pair(2, 0), "O")
                    .markTile(Pair(1, 1), "O")
                    .markTile(Pair(0, 2), "O")

                    .markTile(Pair(1, 2), "O")
                    .markTile(Pair(2, 2), "O")

                    .markTile(Pair(0, 1), "O")
                    .markTile(Pair(1, 0), "O")

                    .markTile(Pair(10, 10), "X")
                    .markTile(Pair(9, 11), "X")
                    .markTile(Pair(8, 12), "X")

                    .markTile(Pair(11, 11), "X")
                    .markTile(Pair(12, 12), "X")

                    .markTile(Pair(10, 11), "X")
                    .markTile(Pair(10, 12), "X")

                    .markTile(Pair(11, 10), "X")
                    .markTile(Pair(12, 10), "X")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(3)
            assertThat(scoreX).isEqualTo(6)
        }


        it("returns score zero if there is no pattern") {
            val board = Board()
                    .markTile(Pair(0, 0), "O")
                    .markTile(Pair(0, 1), "O")
                    .markTile(Pair(0, 2), "X")

            val scoreO = board.getScore("O")
            val scoreX = board.getScore("X")

            assertThat(scoreO).isEqualTo(0)
            assertThat(scoreX).isEqualTo(0)
        }
    }


    describe("Tile") {

    }
})