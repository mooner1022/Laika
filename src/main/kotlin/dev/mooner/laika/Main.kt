/*
 * Main.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 8:00
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika

import dev.mooner.laika.core.Laika
import dev.mooner.laika.core.on
import dev.mooner.laika.event.message.MessageReceiveEvent
import io.ktor.util.network.*
import mu.KotlinLogging

private val mainLogger = KotlinLogging.logger {  }

private const val PREFIX = "!"

suspend fun main() {
    val laika = Laika {
        hostname = "192.168.0.11"
        port = 2323
        maxRetryAttempt = 3
    }

    laika.on<MessageReceiveEvent> {
        mainLogger.debug { message.content }

        if (!message.content.startsWith(PREFIX)) return@on

        when(val msg = message.content.drop(1)) {
            "test" -> {
                message.room.send("Hello, World!")
            }
            "clientInfo" -> {
                message.room.send(
                    """
                    |Client Info
                    |-------
                    |hostname: ${laika.gateway.remoteAddress.hostname}
                    |port: ${laika.gateway.remoteAddress.port}
                    """.trimMargin()
                )
            }
        }
    }

    laika.start()
}