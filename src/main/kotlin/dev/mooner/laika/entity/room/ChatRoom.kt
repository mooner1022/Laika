/*
 * ChatRoom.kt created by Minki Moon(mooner1022) on 22. 2. 19. 오전 2:36
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity.room

interface ChatRoom {

    val name: String

    val isGroupChat: Boolean

    suspend fun send(message: String)

    suspend fun sendKakaoLink() // TODO
}