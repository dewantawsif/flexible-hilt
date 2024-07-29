/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import flexiblehilt.buildlogic.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType

private const val kotlinFiles = "**/*.kt"
private const val resourceFiles = "**/resources/**"
private const val buildFiles = "**/build/**"
private const val generatedFiles = "**/generated/**"
private const val scriptsFiles = "**/*.kts"

@Suppress("UNUSED")
class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(SpotlessPlugin::class.java)
            pluginManager.apply(DetektPlugin::class.java)

            extensions.findByType(SpotlessExtension::class)?.apply {
                kotlin {
                    target("**/*.kt", "**/*.kts")
                    targetExclude("**/build/**/*.kt")
                    ktlint(libs.ktlint.core.get().version)
                        .editorConfigOverride(mapOf(
                            "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                            "ktlint_standard_discouraged-comment-location" to "disabled"
                        ))
                    trimTrailingWhitespace()
                    endWithNewline()
                }
                format("kt") {
                    target("**/*.kt")
                    licenseHeaderFile(rootProject.file("build-logic/spotless/copyright.kt"), "(package |@file|import )")
                }
                format("kts") {
                    target("**/*.kts")
                    licenseHeaderFile(rootProject.file("build-logic/spotless/copyright.kt"), "(^(?![\\/ ]\\*).*$)")
                }
                format("misc") {
                    target("**/.gitignore", "**/*.xml")
                    targetExclude("**/build/**/*.xml")
                    trimTrailingWhitespace()
                    endWithNewline()
                }
            }
            extensions.findByType(DetektExtension::class)?.apply {
                parallel = true
                config.setFrom(rootProject.file("build-logic/detekt/config.yml"))
                buildUponDefaultConfig = true
                ignoreFailures = false
            }

            tasks.withType<Detekt>().configureEach {
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles, generatedFiles, scriptsFiles)
                reports {
                    html.required.set(true)
                    xml.required.set(false)
                    txt.required.set(false)
                }
            }

            listOf(
                "preBuild", // Android modules
                "compileKotlin", // Non-Android modules
            ).forEach { name ->
                tasks.findByName(name)?.apply {
                    dependsOn("detekt")
                    dependsOn("spotlessCheck")
                }
            }
        }
    }
}
