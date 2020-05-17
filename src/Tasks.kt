package com.example

import com.google.cloud.tasks.v2.*
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

val queuePath: String = System.getenv("QUEUE_PATH")
val taskURL: String = System.getenv("TASK_URL")

@UseExperimental(ExperimentalTime::class)
fun createTask() {
    val uuid = UUID.randomUUID()
    println("[$uuid] begin createTask")

    val duration = measureTime {
        val taskBuilder: Task.Builder = Task.newBuilder()
                .setHttpRequest(
                        HttpRequest.newBuilder()
                                .setUrl(taskURL)
                                .setHttpMethod(HttpMethod.GET)
                                .build())

        CloudTasksClient.create().use { client ->
            try {
                val task = client.createTask(queuePath, taskBuilder.build())
                println("[$uuid] Task created: ${task.name}")
            } catch (ex: Exception) {
                println("[$uuid] Failed to create task")
                throw ex
            }
        }
    }

    println("[$uuid] End createTask in ${duration.inMilliseconds.roundToInt()}ms")
}