/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible

inline fun <reified T : FlexibleHiltItem> getFromFlexibleHilt(): T {
    return FlexibleHilt.getItems()[T::class.java]?.get() as? T
        ?: error(
            "Couldn't find ${T::class.java} in 'FlexibleHiltGraph'. Make sure you have annotated " +
                "the base class/interface with [MakeFlexible] and it inherits [FlexibleHiltItem]",
        )
}

inline fun <reified T : FlexibleHiltItem> lazyFromFlexibleHilt(): Lazy<T> {
    return lazy { getFromFlexibleHilt() }
}
