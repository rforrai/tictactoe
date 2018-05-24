package com.example.tictactoe

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class HealthHandler {

    fun health(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject("{ \"status\": \"OK\" }"))

}