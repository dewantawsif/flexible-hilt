/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible.internal

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

internal val Context.isHiltAndroidApp: Boolean
    get() = this is Application && javaClass.annotations.any { it is HiltAndroidApp }
