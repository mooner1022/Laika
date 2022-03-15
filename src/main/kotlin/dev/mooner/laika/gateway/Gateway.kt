/*
 * Gateway.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 4:36
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway

import io.ktor.util.network.*

interface Gateway {

    val remoteAddress: NetworkAddress

    val isConnected: Boolean

    suspend fun start()

    suspend fun shutdown()

    suspend fun send(data: String)
}