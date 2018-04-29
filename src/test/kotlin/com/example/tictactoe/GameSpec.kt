package com.example.tictactoe

import org.assertj.core.api.Assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class GameSpec : Spek({
    describe("Game") {

        describe("start") {

            it("starts new empty game") {
                val game = Game().start()

                assertThat(game.isEmpty()).isTrue()
            }
        }

        describe("addPlayer") {

            it("adds new player to game") {
                val game = Game().start()
                        .addPlayer(Player("O", 123))

                assertThat(game.getPlayer("O")?.id).isEqualTo(123)
            }

            it("throws when player with given id is already in game") {
                assertThatThrownBy {
                    Game().start()
                            .addPlayer(Player("O", 123))
                            .addPlayer(Player("X", 123))
                }
            }

            it("throws when player with given mark is already in game") {
                assertThatThrownBy {
                    Game().start()
                            .addPlayer(Player("O", 123))
                            .addPlayer(Player("O", 124))
                }
            }
        }


        describe("getActivePlayer") {

            it("returns first player - defaults to first player sorted by mark") {

                val player = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("O", 124))
                        .getActivePlayer()

                assertThat(player?.mark).isEqualTo("O")
            }

            it("returns second player after one move") {
                val player = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("O", 124))
                        .mark(Pair(1, 1))
                        .getActivePlayer()

                assertThat(player?.mark).isEqualTo("X")
            }

            it("returns first player after two moves") {
                val player = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("O", 124))
                        .mark(Pair(1, 1))
                        .mark(Pair(1, 2))
                        .getActivePlayer()

                assertThat(player?.mark).isEqualTo("O")
            }
        }


        describe("hasMark") {

            it("returns true if given mark is on position") {
                val game = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("Y", 124))
                        .mark(Pair(1, 1))

                assertThat(game.hasMark(Pair(1, 1), "X")).isTrue()
            }

            it("returns false if given mark is not on position") {
                val game = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("Y", 124))
                        .mark(Pair(1, 1))

                assertThat(game.hasMark(Pair(1, 1), "O")).isFalse()
            }

            it("returns false if on given position is nothing") {
                val game = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("Y", 124))
                        .mark(Pair(1, 2))

                assertThat(game.hasMark(Pair(1, 1), "O")).isFalse()
            }
        }


        describe("mark") {

            it("adds mark to board for active player") {
                val game = Game().start()
                        .addPlayer(Player("X", 123))
                        .addPlayer(Player("Y", 124))
                        .mark(Pair(1, 1))

                assertThat(game.hasMark(Pair(1, 1), "X")).isTrue()
            }
        }
    }
})