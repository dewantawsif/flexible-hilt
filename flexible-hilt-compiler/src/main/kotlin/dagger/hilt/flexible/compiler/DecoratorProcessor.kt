/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
@file:Suppress("ktlint:standard:property-naming")

package dagger.hilt.flexible.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

private val DaggerBindsClassName = ClassName("dagger", "Binds")
private val DaggerModuleClassName = ClassName("dagger", "Module")
private val DaggerHiltInstallInClassName = ClassName("dagger.hilt", "InstallIn")
private val DaggerHiltSingletonComponentClassName = ClassName("dagger.hilt.components", "SingletonComponent")
private val DaggerMultiBindingsIntoMapClassName = ClassName("dagger.multibindings", "IntoMap")

private val FlexibleHiltItemClassName = ClassName("dagger.hilt.flexible", "FlexibleHiltItem")
private val FlexibleHiltItemKeyClassName = ClassName("dagger.hilt.flexible", "FlexibleHiltItemKey")
private const val IncludeInFlexibleHiltGraphQualifiedName = "dagger.hilt.flexible.MakeFlexible"

private const val TClassFormat = "%T::class"

class DecoratorProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val (toProcess, unprocessed) = resolver
            .getSymbolsWithAnnotation(IncludeInFlexibleHiltGraphQualifiedName)
            .partition { it is KSClassDeclaration && it.validate() }

        toProcess.forEach { annotated ->
            val targetClassDeclaration = annotated as KSClassDeclaration
            val targetPackageName = targetClassDeclaration.packageName.asString()
            val targetClassName = targetClassDeclaration.toClassName()
            val outputHiltModuleName = "${targetClassDeclaration.simpleName.asString()}HiltModule"

            val hiltModuleInterface = TypeSpec.interfaceBuilder(outputHiltModuleName)
                .addAnnotation(DaggerModuleClassName)
                .addAnnotation(
                    AnnotationSpec.builder(DaggerHiltInstallInClassName)
                        .addMember(TClassFormat, DaggerHiltSingletonComponentClassName)
                        .build(),
                )
                .addFunction(
                    FunSpec.builder("addItemToGraph")
                        .addAnnotation(DaggerBindsClassName)
                        .addAnnotation(DaggerMultiBindingsIntoMapClassName)
                        .addAnnotation(
                            AnnotationSpec.builder(FlexibleHiltItemKeyClassName)
                                .addMember(TClassFormat, targetClassName)
                                .build(),
                        )
                        .addParameter("item", targetClassName)
                        .returns(FlexibleHiltItemClassName)
                        .addModifiers(KModifier.ABSTRACT)
                        .build(),
                )
                .addOriginatingKSFile(targetClassDeclaration.containingFile!!)
                .build()

            FileSpec.builder(targetPackageName, outputHiltModuleName)
                .addType(hiltModuleInterface)
                .build()
                .writeTo(
                    codeGenerator = codeGenerator,
                    aggregating = true,
                )
        }

        return unprocessed
    }

    class Provider : SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            return DecoratorProcessor(environment.codeGenerator)
        }
    }
}
