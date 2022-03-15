/*
 * KakaoLinkConfigBuilder.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 7:14
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.kakaolink.config

import kotlin.properties.Delegates.notNull

class KakaoLinkConfigBuilder {

    var email: String by notNull()

    var password: String by notNull()

    var key: String by notNull()

    var host: String by notNull()

    internal fun build(): KakaoLinkConfig = KakaoLinkConfig(email, password, key, host)
}