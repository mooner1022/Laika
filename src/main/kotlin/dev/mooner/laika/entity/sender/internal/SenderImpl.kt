/*
 * SenderImpl.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 6:59
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.entity.sender.internal

import dev.mooner.laika.entity.sender.Sender

data class SenderImpl(
    override val name: String,
    override val profileBase64: String
): Sender