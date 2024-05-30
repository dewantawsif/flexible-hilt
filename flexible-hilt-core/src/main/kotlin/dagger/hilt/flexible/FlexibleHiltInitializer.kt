/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import dagger.hilt.flexible.internal.isHiltAndroidApp

private val LOG_TAG = FlexibleHiltInitializer::class.simpleName

private const val NOT_HILT_APP_MSG = "[Application] is missing [@HiltAndroidApp] annotation, " +
    "automatic initialization of [FlexibleHilt] being skipped. " +
    "To initialize manually, execute FlexibleHilt.init(applicationContext)"

@Suppress("UNUSED")
class FlexibleHiltInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (!context.isHiltAndroidApp) {
            Log.w(LOG_TAG, NOT_HILT_APP_MSG)
            return
        }
        FlexibleHilt.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
