package dev.mooner

import dev.mooner.laika.core.Laika
import dev.mooner.laika.core.on
import dev.mooner.laika.event.message.MessageReceiveEvent
import io.ktor.util.network.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class ApplicationTest {

    /*
    @Test
    fun `test laika server`() = runBlocking {
        val laika = Laika {
            hostname = "192.168.0.11"
            port = 2323
            maxRetryAttempt = 3
        }

        laika.on<MessageReceiveEvent> {

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

     */
}