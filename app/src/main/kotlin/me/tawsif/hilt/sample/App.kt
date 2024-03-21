/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.tawsif.hilt.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.flexible.FlexibleHilt

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FlexibleHilt.init(this)
    }
}
