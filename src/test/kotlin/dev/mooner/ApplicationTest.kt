package dev.mooner

import dev.mooner.laika.core.Laika
import dev.mooner.laika.core.on
import dev.mooner.laika.event.message.MessageReceiveEvent
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun `test laika server`() {
        val laika = Laika(2333)

        laika.on<MessageReceiveEvent> {
            message.content
        }

        //laika.start()
    }
}