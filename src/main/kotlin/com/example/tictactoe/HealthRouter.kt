package com.example.tictactoe

import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse


class HealthRouter(private val healthHandler: HealthHandler) {


    fun route(): RouterFunction<ServerResponse> =
            route(GET("/health"), HandlerFunction { healthHandler.health(it) })

}