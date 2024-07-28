/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible

@Deprecated(
    message = "Use FlexibleHilt.get instead",
    replaceWith = ReplaceWith(
        expression = "FlexibleHilt.get<T>()",
        imports = arrayOf("dagger.hilt.flexible.FlexibleHilt"),
    ),
)
inline fun <reified T : FlexibleHiltItem> getFromFlexibleHilt(): T {
    return FlexibleHilt.get<T>()
}

@Deprecated(
    message = "Use FlexibleHilt.getLazy instead",
    replaceWith = ReplaceWith(
        expression = "FlexibleHilt.getLazy<T>()",
        imports = arrayOf("dagger.hilt.flexible.FlexibleHilt"),
    ),
)
inline fun <reified T : FlexibleHiltItem> lazyFromFlexibleHilt(): Lazy<T> {
    return FlexibleHilt.getLazy<T>()
}
