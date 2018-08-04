package com.example.tictactoe

import org.springframework.web.reactive.function.server.*

class Router(private val healthHandler: HealthHandler,
             private val gameHandler: GameHandler) {


    fun route(): RouterFunction<ServerResponse> =
            HealthRouter(healthHandler).route() .
            and(GameRouter(gameHandler).route())

}