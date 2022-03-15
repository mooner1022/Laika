/*
 * SendMessageDataPayload.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 5:33
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity

@kotlinx.serialization.Serializable
data class SendMessageDataPayload(
    val room: String,
    val text: String
)