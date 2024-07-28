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
    private var entryPoint: FlexibleHiltEntryPoint? = null

    fun init(context: Context) {
        if (entryPoint != null) return
        if (!context.isHiltAndroidApp) error(NOT_HILT_APP_MSG)
        entryPoint = EntryPointAccessors.fromApplication<FlexibleHiltEntryPoint>(context)
    }

    inline fun <reified T : FlexibleHiltItem> get(): T {
        return getItems()[T::class.java]?.get() as? T
            ?: error(
                "Couldn't find provided class in 'FlexibleHiltGraph'. " +
                    "Make sure that it inherits 'FlexibleHiltItem'",
            )
    }

    inline fun <reified T : FlexibleHiltItem> getLazy(): Lazy<T> = lazy { get() }

    fun getItems(): Map<Class<out FlexibleHiltItem>, Provider<FlexibleHiltItem>> {
        return requireNotNull(entryPoint) { UNINITIALIZED_MESSAGE }.items()
    }
}
