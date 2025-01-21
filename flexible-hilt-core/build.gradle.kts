/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.maven.publish)
    id("flexiblehilt.lint")
}

android {
    namespace = "dagger.hilt.flexible"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.androidx.startup.runtime)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
