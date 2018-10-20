package com.example.e2e

import io.restassured.RestAssured.*
import io.restassured.path.json.JsonPath
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.springframework.http.MediaType


class GameRestSpec : Spek({


    describe("/game") {


        describe("/new") {
            it("returns empty game") {
                val response = get("/game/new")
                        .then()
                        .extract().response().asString()

                assertThat(JsonPath.from(response).getList<Any>("players")).isEmpty()
                assertThat(JsonPath.from(response).getList<Any>("board.tiles")).isEmpty()
            }
        }


        describe("/player") {
            it("adds new player to the game") {
                val response = given()
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .body(
                                """
                                    {
                                      "board": {
                                        "bound": {
                                          "first": 2147483647,
                                          "second": 2147483647
                                        },
                                        "tiles": [],
                                        "empty": true
                                      },
                                      "players": [],
                                      "activePlayer": null,
                                      "empty": true
                                    }
                                """
                        )
                        .post("/game/player?mark=X&id=0")
                        .then()
                        .extract().response().asString()

                assertThat(JsonPath.from(response).getList<Any>("players")).isNotEmpty()
            }
        }


        describe("/mark") {
            it("marks tile in the game") {
                val response = given()
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .body(
                                """
                                    {
                                      "board": {
                                        "bound": {
                                          "first": 2147483647,
                                          "second": 2147483647
                                        },
                                        "tiles": [],
                                        "empty": true
                                      },
                                      "players": [
                                        {
                                          "mark": "X",
                                          "id": 0
                                        }
                                      ],
                                      "activePlayer": {
                                        "mark": "X",
                                        "id": 0
                                      },
                                      "empty": true
                                    }
                                """
                        )
                        .post("/game/mark?x=1&y=2")
                        .then()
                        .extract().response().asString()

                assertThat(JsonPath.from(response).getList<Any>("board.tiles")).isNotEmpty()
            }
        }
    }
})