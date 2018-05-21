package com.example.tictactoe

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*

class Router {
    fun route(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.GET("/health"), HandlerFunction {
            return@HandlerFunction ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8) //
                    .body(BodyInserters.fromObject("{status: OK}"))
        })
    }


}