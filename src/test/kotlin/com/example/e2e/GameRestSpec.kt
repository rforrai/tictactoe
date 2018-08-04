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
                                "{\n" +
                                        "  \"board\": {\n" +
                                        "    \"bound\": {\n" +
                                        "      \"first\": 2147483647,\n" +
                                        "      \"second\": 2147483647\n" +
                                        "    },\n" +
                                        "    \"tiles\": [],\n" +
                                        "    \"empty\": true\n" +
                                        "  },\n" +
                                        "  \"players\": [],\n" +
                                        "  \"activePlayer\": null,\n" +
                                        "  \"empty\": true\n" +
                                        "}"
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
                                "{\n" +
                                        "  \"board\": {\n" +
                                        "    \"bound\": {\n" +
                                        "      \"first\": 2147483647,\n" +
                                        "      \"second\": 2147483647\n" +
                                        "    },\n" +
                                        "    \"tiles\": [],\n" +
                                        "    \"empty\": true\n" +
                                        "  },\n" +
                                        "  \"players\": [\n" +
                                        "    {\n" +
                                        "      \"mark\": \"X\",\n" +
                                        "      \"id\": 0\n" +
                                        "    }\n" +
                                        "  ],\n" +
                                        "  \"activePlayer\": {\n" +
                                        "    \"mark\": \"X\",\n" +
                                        "    \"id\": 0\n" +
                                        "  },\n" +
                                        "  \"empty\": true\n" +
                                        "}"
                        )
                        .post("/game/mark?x=1&y=2")
                        .then()
                        .extract().response().asString()

                assertThat(JsonPath.from(response).getList<Any>("board.tiles")).isNotEmpty()
            }
        }
    }
})