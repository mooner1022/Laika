/*
 * RetryImpl.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 9:26
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway.internal

import dev.mooner.laika.gateway.Retry
import mu.KotlinLogging
import java.util.concurrent.atomic.AtomicInteger

private val retryImplLogger = KotlinLogging.logger {  }

internal class RetryImpl(
    override val maxAttempt: Int
): Retry {

    private var attemptCount = AtomicInteger(if (maxAttempt == -1) -1 else 0)

    override val canRetry: Boolean
        get() = attemptCount.get() < maxAttempt

    override suspend fun retry() {
        require(canRetry) { "Max retry attempt exceeded" }

        retryImplLogger.debug { "Retrying ${attemptCount.get()} times..." }
        attemptCount.incrementAndGet()
    }

    override fun resetAttempt() {
        attemptCount.set(0)
    }

}