/*
 * GatewayImpl.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 5:40
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway.internal

import dev.mooner.laika.commands.GatewayCommand
import dev.mooner.laika.core.Laika
import dev.mooner.laika.event.message.MessageReceiveEvent
import dev.mooner.laika.gateway.Gateway
import dev.mooner.laika.gateway.GatewayState
import dev.mooner.laika.utils.decodeFromStringOrNull
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.util.network.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicReference

private val gatewayImplLogger = KotlinLogging.logger {  }

internal class GatewayImpl(
    private val laika: Laika,
    private val data: GatewayData
): Gateway {

    private lateinit var server: BoundDatagramSocket

    private var _remoteAddress: NetworkAddress? = null
    override val remoteAddress get() = _remoteAddress!!

    private var state: AtomicReference<GatewayState> = AtomicReference(GatewayState.Stopped)

    override val isConnected: Boolean
        get() = _remoteAddress != null && state.get() == GatewayState.Running

    override suspend fun start() = withContext(Dispatchers.Default) {
        val hostname = data.hostname
        val port = data.port

        gatewayImplLogger.debug { "Starting gateway with port $port" }

        val selector = ActorSelectorManager(Dispatchers.IO)
        state.set(GatewayState.Running)

        while (data.retry.canRetry && state.get() == GatewayState.Running) {
            try {
                val address = InetSocketAddress(hostname, port)
                server = aSocket(selector).udp().bind(address)
                gatewayImplLogger.debug { "Started gateway server on ${server.localAddress}" }
            } catch (e: Exception) {
                gatewayImplLogger.error(e)
                data.retry.retry()
                continue
            }

            try {
                receiveSocketData()
            } catch (e: Exception) {
                gatewayImplLogger.error(e)
            }

            shutdown()
        }

        if (!data.retry.canRetry) {
            gatewayImplLogger.warn { "Gateway stopped due to retry limit exceed" }
        }
    }

    private suspend fun receiveSocketData() {
        server.incoming.receiveAsFlow()
            .buffer(Channel.UNLIMITED)
            .collect {
                _remoteAddress = it.address
                //gatewayImplLogger.debug { it.packet.readText(Charsets.UTF_8) }
                val rawText = it.packet.readText(Charsets.UTF_8)
                gatewayImplLogger.trace { "gateway <<<\n$rawText" }
                when(val input: GatewayCommand? = laika.jsonSerializer.decodeFromStringOrNull(rawText)) {
                    is GatewayCommand.ReceiveMessageCommand -> {
                        val event = MessageReceiveEvent(
                            laika = laika,
                            message = input.data.toMessage(this)
                        )
                        laika.fireEventWithScope(event)
                    }
                    is GatewayCommand.ReplyResultCommand -> {
                        gatewayImplLogger.debug { "result for session ${input.session}: success= ${input.success}, data= ${input.data}" }
                    }
                    null -> {
                        gatewayImplLogger.debug { "Failed to decode command, data= $rawText" }
                    }
                    else -> { /* Ignore */ }
                }
            }
    }

    override suspend fun shutdown() {
        requireGatewayRunning()

        gatewayImplLogger.debug { "Closing gateway server" }

        try {
            server.close()
            state.set(GatewayState.Stopped)
        } catch (e: Exception) {
            gatewayImplLogger.error(e)
        }
    }

    override suspend fun send(data: String) {
        requireGatewayRunning()

        gatewayImplLogger.trace { "gateway >>>\n$data" }

        val buffer = BytePacketBuilder().apply {
            writeText(data)
        }.build()
        val packet = Datagram(buffer, remoteAddress)
        server.send(packet)
    }

    internal suspend fun send(payload: GatewayCommand) {
        val encodedPayload = laika.jsonSerializer.encodeToString(payload)
        send(encodedPayload)
    }

    private fun requireGatewayRunning() {
        require(this::server.isInitialized) { "Send called before gateway init" }
        require(state.get() == GatewayState.Running) { "Gateway server not running" }
    }
}