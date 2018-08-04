package com.example.tictactoe

import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.ServerResponse


class GameRouter(private val gameHandler: GameHandler) {


    fun route(): RouterFunction<ServerResponse> =
            nest(
                path("/game"),
                    route(GET("/new"), HandlerFunction { gameHandler.init(it) }) .
                    andRoute(POST("/player"), HandlerFunction { gameHandler.addPlayer(it) }) .
                    andRoute(POST("/mark"), HandlerFunction { gameHandler.mark(it) })
            )

}