package com.example

import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@UseExperimental(ExperimentalTime::class)
fun calc() {
    val uuid = UUID.randomUUID()
    println("[$uuid] begin calc")

    val duration = measureTime {
        repeat(100000) {
            UUID.randomUUID()
        }
    }

    println("[$uuid] End calc in ${duration.inMilliseconds.roundToInt()}ms")
}