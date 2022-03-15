/*
 * ReceivedMessagePayload.kt created by Minki Moon(mooner1022) on 22. 2. 19. 오전 2:34
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity

import dev.mooner.laika.entity.room.internal.ChatRoomImpl
import dev.mooner.laika.entity.sender.internal.SenderImpl
import dev.mooner.laika.gateway.Gateway

@kotlinx.serialization.Serializable
data class ReceivedMessagePayload(
    val room: String,
    val sender: String,
    val content: String,
    val profileImage: String,
    val isGroupChat: Boolean,
    val packageName: String
) {

    fun toMessage(gateway: Gateway): Message {
        return Message(
            room = ChatRoomImpl(
                gateway = gateway,
                name = room,
                isGroupChat = isGroupChat
            ),
            sender = SenderImpl(
                name = sender,
                profileBase64 = profileImage
            ),
            content = content,
            packageName = packageName
        )
    }
}