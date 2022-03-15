/*
 * LaikaBuilder.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 7:25
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

@file:OptIn(ExperimentalContracts::class)

package dev.mooner.laika.core

import dev.mooner.laika.kakaolink.config.KakaoLinkConfig
import dev.mooner.laika.kakaolink.config.KakaoLinkConfigBuilder
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.properties.Delegates.notNull

class LaikaBuilder {

    var hostname: String by notNull()

    var port: Int by notNull()

    var maxRetryAttempt: Int = -1

    private val usesKakaoLink: Boolean
        get() = kakaoLinkConfig != null

    internal var kakaoLinkConfig: KakaoLinkConfig? = null

    fun kakaoLink(builder: KakaoLinkConfigBuilder.() -> Unit) {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        kakaoLinkConfig = KakaoLinkConfigBuilder().apply(builder).build()
    }
}