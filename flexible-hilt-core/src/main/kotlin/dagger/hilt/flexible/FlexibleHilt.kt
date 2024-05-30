/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible

import android.content.Context
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.flexible.internal.isHiltAndroidApp
import javax.inject.Provider

private const val NOT_HILT_APP_MSG = "[Application] is missing [@HiltAndroidApp] annotation, " +
    "failed to initialize [FlexibleHilt]"
private const val UNINITIALIZED_MESSAGE = "FlexibleHilt not initialized"

object FlexibleHilt {
    private var graph: FlexibleHiltGraphEntryPoint? = null

    fun init(context: Context) {
        if (graph != null) return
        if (!context.isHiltAndroidApp) error(NOT_HILT_APP_MSG)
        graph = EntryPointAccessors.fromApplication<FlexibleHiltGraphEntryPoint>(context)
    }

    fun getItems(): Map<Class<out FlexibleHiltItem>, Provider<FlexibleHiltItem>> {
        return requireNotNull(graph) { UNINITIALIZED_MESSAGE }.items()
    }
}
