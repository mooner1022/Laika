/*
 * Retry.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 9:23
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway

interface Retry {

    val maxAttempt: Int

    val canRetry: Boolean

    suspend fun retry()

    fun resetAttempt()
}