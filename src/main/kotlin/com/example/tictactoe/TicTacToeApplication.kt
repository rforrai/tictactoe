package com.example.tictactoe

import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RouterFunctions
import reactor.ipc.netty.NettyContext
import reactor.ipc.netty.http.server.HttpServer

/**
 * Less Framework More Library :)
 */
class TicTacToeFunctionalApplication


fun main(args: Array<String>) {
    startReactorServer(getHttpHandler())?.onClose()?.block()
}


@Throws(InterruptedException::class)
fun startReactorServer(httpHandler: HttpHandler): NettyContext? {
    val adapter = ReactorHttpHandlerAdapter(httpHandler)
    val server = HttpServer.create("localhost", 18080)
    return server.newHandler(adapter).block()
}


fun getHttpHandler(): HttpHandler {
    val healthHandler = HealthHandler()
    val gameHandler = GameHandler()

    return RouterFunctions.toHttpHandler(Router(healthHandler, gameHandler).route())
}