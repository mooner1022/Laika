/*
 * Laika.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 3:56
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

@file:OptIn(ExperimentalContracts::class)

package dev.mooner.laika.core

import dev.mooner.laika.event.Event
import dev.mooner.laika.gateway.Gateway
import dev.mooner.laika.gateway.internal.GatewayData
import dev.mooner.laika.gateway.internal.GatewayImpl
import dev.mooner.laika.kakaolink.config.KakaoLinkConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class Laika: CoroutineScope {

    val laikaLogger = KotlinLogging.logger {  }

    private lateinit var _gateway: Gateway
    val gateway get() = _gateway

    private var hostname: String by Delegates.notNull()
    private var port: Int by Delegates.notNull()
    private var maxRetryAttempt: Int = -1

    private var kakaoLinkConfig: KakaoLinkConfig? = null

    constructor(hostname: String, port: Int) {
        this.hostname = hostname
        this.port = port
    }

    constructor(builder: LaikaBuilder.() -> Unit) {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        val config = LaikaBuilder().apply(builder)

        kakaoLinkConfig = config.kakaoLinkConfig
        maxRetryAttempt = config.maxRetryAttempt
        hostname = config.hostname
        port = config.port
    }

    val jsonSerializer: Json = Json {
        ignoreUnknownKeys = true
    }

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Default

    private var eventPublisher: MutableSharedFlow<Event> =
        MutableSharedFlow(extraBufferCapacity = Channel.UNLIMITED)
    val eventFlow: SharedFlow<Event>
        get() = eventPublisher.asSharedFlow()

    suspend fun fireEvent(event: Event) = eventPublisher.emit(event)

    fun fireEventWithScope(event: Event, scope: CoroutineScope = this) = scope.launch {
        eventPublisher.emit(event)
    }

    suspend fun start() {
        require(!this::_gateway.isInitialized) { "Laika server must be started exactly once." }
        //require(port != null) { "Server not initialized" }

        val data = GatewayData(
            hostname = hostname,
            port = port,
            maxRetryAttempt = maxRetryAttempt
        )
        _gateway = GatewayImpl(this, data)
        _gateway.start()
    }
}

fun laikaEventScope(laika: Laika): CoroutineScope = CoroutineScope(laika.coroutineContext + SupervisorJob(laika.coroutineContext.job))

public inline fun <reified T: Event> Laika.on(
    scope: CoroutineScope = this,
    noinline callback: suspend T.() -> Unit
): Job = eventFlow.buffer(Channel.UNLIMITED)
    .filterIsInstance<T>()
    .onEach { event ->
        scope.launch(event.coroutineContext) {
            runCatching {
                callback(event)
            }.onFailure {
                laikaLogger.error(it) {}
            }
        }
    }
    .launchIn(scope)