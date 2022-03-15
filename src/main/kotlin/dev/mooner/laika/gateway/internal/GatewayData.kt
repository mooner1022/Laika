/*
 * GatewayData.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 9:19
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway.internal

import dev.mooner.laika.gateway.Retry

internal data class GatewayData (
    val hostname: String,
    val port: Int,
    private val maxRetryAttempt: Int = -1
) {
    val retry: Retry by lazy {
        RetryImpl(maxRetryAttempt)
    }
}
