/*
 * SerializationUtils.kt created by Minki Moon(mooner1022) on 22. 2. 19. 오후 1:46
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

inline fun <reified T> Json.decodeFromStringOrNull(string: String): T? {
    return try {
        decodeFromString(string)
    } catch (e: Exception) {
        null
    }
}