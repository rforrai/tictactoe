package com.example.tictactoe

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class GameHandler {

    fun init(request: ServerRequest): Mono<ServerResponse> {
        val game = Game()

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(game))
    }


    fun addPlayer(request: ServerRequest): Mono<ServerResponse> {
        val game = request.bodyToMono(Game::class.java)
        val playerMark = request.queryParam("mark")
        val playerId = request.queryParam("id")

        return game.flatMap {
            when {
                playerMark.isPresent.not() ->
                    ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN)
                            .body(BodyInserters.fromObject("Player mark is required"))

                playerId.isPresent.not() ->
                    ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN)
                            .body(BodyInserters.fromObject("Player id is required"))

                else -> {
                    val newGame = it.addPlayer(Player(playerMark.get(), playerId.get().toInt()))

                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(BodyInserters.fromObject(newGame))
                }
            }
        }
    }


    fun mark(request: ServerRequest): Mono<ServerResponse> {
        val game = request.bodyToMono(Game::class.java)
        val x = request.queryParam("x")
        val y = request.queryParam("y")

        return game.flatMap {
            when {
                x.isPresent.not() ->
                    ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN)
                            .body(BodyInserters.fromObject("X position of mark is required"))

                y.isPresent.not() ->
                    ServerResponse.badRequest().contentType(MediaType.TEXT_PLAIN)
                            .body(BodyInserters.fromObject("Y position of mark is required"))

                else -> {
                    val newGame = it.mark(Pair(x.get().toInt(), y.get().toInt()))

                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(BodyInserters.fromObject(newGame))
                }
            }
        }
    }
}