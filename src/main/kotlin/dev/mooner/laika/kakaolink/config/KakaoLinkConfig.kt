/*
 * KakaoLinkConfig.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 7:14
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.kakaolink.config

data class KakaoLinkConfig(
    val email: String,
    val pw: String,
    val key: String,
    val host: String
)