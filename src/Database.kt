package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val jdbcURL: String = System.getenv("JDBC_URL")

val dataSource: HikariDataSource by lazy {
    val config = HikariConfig().apply {
        jdbcUrl = jdbcURL
    }
    HikariDataSource(config)
}

val jdbi: Jdbi by lazy {
    Jdbi.create(dataSource).installPlugins()
}

@UseExperimental(ExperimentalTime::class)
fun insertLog() {
    val uuid = UUID.randomUUID()
    println("[$uuid] begin insertLog")

    val duration = measureTime {
        jdbi.inTransaction<Unit, Exception> { handle ->
            handle.createUpdate(
                """
            CREATE TABLE IF NOT EXISTS access_log (
                uuid uuid NOT NULL PRIMARY KEY,
                created_at timestamptz NOT NULL
            )
        """.trimIndent()
            ).execute()

            handle.createUpdate(
                """
            INSERT INTO access_log
                (uuid, created_at)
            VALUES
                (:uuid, NOW())
        """.trimIndent()
            )
                .bind("uuid", UUID.randomUUID())
                .execute()
        }
    }

    println("[$uuid] End insertLog in ${duration.inMilliseconds.roundToInt()}ms")
}
