/*
 * GatewayState.kt created by Minki Moon(mooner1022) on 22. 2. 18. 오후 10:00
 * Copyright (c) mooner1022. all rights reserved.
 * This code is licensed under the GNU General Public License v3.0.
 */

package dev.mooner.laika.gateway

sealed class GatewayState {
    object Running: GatewayState()
    object Stopped: GatewayState()
}