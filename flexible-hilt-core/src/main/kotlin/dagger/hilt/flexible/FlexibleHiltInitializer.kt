/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dagger.hilt.flexible

import android.content.Context
import androidx.startup.Initializer

class FlexibleHiltInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        FlexibleHilt.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
