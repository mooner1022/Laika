/*
 * Message.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 3:54
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity

import dev.mooner.laika.entity.room.ChatRoom
import dev.mooner.laika.entity.sender.Sender

@kotlinx.serialization.Serializable
data class Message(
    //@kotlinx.serialization.Serializable(with = ChatRoomSerializer::class)
    val room: ChatRoom,
    val sender: Sender,
    val content: String,
    val packageName: String
)