/*
 * MessageReceiveEvent.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 3:54
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.event.message

import dev.mooner.laika.core.Laika
import dev.mooner.laika.core.LaikaObject
import dev.mooner.laika.entity.Message
import dev.mooner.laika.event.Event
import dev.mooner.laika.core.laikaEventScope
import kotlinx.coroutines.CoroutineScope

class MessageReceiveEvent(
    override val laika: Laika,
    val message: Message,
    val coroutineScope: CoroutineScope = laikaEventScope(laika)
): Event, CoroutineScope by coroutineScope, LaikaObject