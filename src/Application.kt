package com.example

import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import kotlinx.coroutines.delay

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)

    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }
        post("/calc") {
            calc()
            call.respondText("OK", status = HttpStatusCode.Created)
        }
        post("/insert") {
            insertLog()
            call.respondText("OK", status = HttpStatusCode.Created)
        }
        post("/task") {
            createTask()
            call.respondText("OK", status = HttpStatusCode.Created)
        }
    }

    intercept(ApplicationCallPipeline.Call) {
        proceed()
        if (call.request.httpMethod == HttpMethod.Post) {
            when (call.request.uri) {
                "/calc" -> {
                    calc()
                    delay(1000)
                    calc()
                }
                "/insert" -> {
                    insertLog()
                    delay(1000)
                    insertLog()
                }
                "/task" -> {
                    createTask()
                    delay(1000)
                    createTask()
                }
            }
        }
    }
}

