/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.tawsif.hilt.sample

import dagger.hilt.flexible.FlexibleHiltItem
import dagger.hilt.flexible.MakeFlexible
import javax.inject.Inject

@MakeFlexible
class Pet @Inject constructor() : FlexibleHiltItem {
    val type = "dog"
}
