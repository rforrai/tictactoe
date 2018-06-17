package com.example.tictactoe

import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.route

class Router(private val healthHandler: HealthHandler,
             private val gameHandler: GameHandler) {


    fun route(): RouterFunction<ServerResponse> =
            route(GET("/health"), HandlerFunction { healthHandler.health(it) })

                    .andRoute(GET("/game/new"), HandlerFunction { gameHandler.init(it) })
                    .andRoute(POST("/game/player"), HandlerFunction { gameHandler.addPlayer(it) })
                    .andRoute(POST("/game/mark"), HandlerFunction { gameHandler.mark(it) })

}