/*
 * Command.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 4:39
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.commands

import dev.mooner.laika.entity.ReceivedMessagePayload
import dev.mooner.laika.entity.SendMessageDataPayload
import dev.mooner.laika.entity.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonClassDiscriminator
import java.util.UUID

@kotlinx.serialization.Serializable
@JsonClassDiscriminator("event")
internal sealed class GatewayCommand {

    @kotlinx.serialization.Serializable
    @SerialName("sendText")
    data class SendMessageCommand(
        val session: String,
        val data: SendMessageDataPayload
    ): GatewayCommand()

    @kotlinx.serialization.Serializable
    @SerialName("chat")
    data class ReceiveMessageCommand(
        val data: ReceivedMessagePayload
    ): GatewayCommand()

    @kotlinx.serialization.Serializable
    @SerialName("reply")
    data class ReplyResultCommand(
        @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
        val session: UUID,
        val success: Boolean,
        val data: Map<String, String> = mapOf()
    ): GatewayCommand()
}