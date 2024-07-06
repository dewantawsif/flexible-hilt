/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
plugins {
    `kotlin-dsl`
}

group = "flexiblehilt.buildlogic"

dependencies {
    implementation(libs.detekt.gradle)
    implementation(libs.spotless.gradle)

    // These allow us to reference the dependency catalog inside of our compiled plugins
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidLint") {
            id = "flexiblehilt.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
    }
}
