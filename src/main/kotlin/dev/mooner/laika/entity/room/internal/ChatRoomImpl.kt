/*
 * ChatRoomImpl.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 6:06
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity.room.internal

import dev.mooner.laika.commands.GatewayCommand
import dev.mooner.laika.entity.SendMessageDataPayload
import dev.mooner.laika.entity.room.ChatRoom
import dev.mooner.laika.gateway.Gateway
import dev.mooner.laika.gateway.GatewayObject
import dev.mooner.laika.gateway.internal.GatewayImpl
import java.util.UUID

internal data class ChatRoomImpl(
    override val gateway: Gateway,
    override val name: String,
    override val isGroupChat: Boolean
): ChatRoom, GatewayObject {

    override suspend fun send(message: String) {
        val session = UUID.randomUUID().toString()
        val payload = GatewayCommand.SendMessageCommand(
            session = session,
            data = SendMessageDataPayload(
                room = this.name,
                text = message
            )
        )

        (gateway as GatewayImpl).send(payload)
    }

    override suspend fun sendKakaoLink() {
        TODO("Not yet implemented")
    }
}