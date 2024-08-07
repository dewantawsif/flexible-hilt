/*
 * Copyright (c) 2024 Dewan Tawsif
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
@file:Suppress(
    "ktlint:standard:property-naming",
    "TopLevelPropertyNaming",
)

package dagger.hilt.flexible.compiler

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclarationContainer
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val DaggerBindsClassName = ClassName("dagger", "Binds")
private val DaggerModuleClassName = ClassName("dagger", "Module")
private val DaggerHiltInstallInClassName = ClassName("dagger.hilt", "InstallIn")
private val DaggerHiltSingletonComponentClassName = ClassName("dagger.hilt.components", "SingletonComponent")
private val DaggerMultiBindingsIntoMapClassName = ClassName("dagger.multibindings", "IntoMap")

private val FlexibleHiltItemClassName = ClassName("dagger.hilt.flexible", "FlexibleHiltItem")
private val FlexibleHiltItemKeyClassName = ClassName("dagger.hilt.flexible", "FlexibleHiltItemKey")

private const val TClassFormat = "%T::class"

private val GeneratedDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

private const val OutputPackagePrefix = "flexible_hilt"

private const val FormattedGeneratedMessage = "Generated %L by flexible-hilt. DO NOT EDIT!"

private const val EmptyClassName = "`_`"

class DecoratorProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val flexibleHiltItemType = resolver
            .getClassDeclarationByName(FlexibleHiltItemClassName.canonicalName)
            ?.asType(emptyList())

        resolver.getNewFiles().flatMap { file ->
            file.getAllClassDeclarations().filter { declaration ->
                declaration.superTypes.any { it.resolve() == flexibleHiltItemType }
            }
        }
            .forEach { declaration ->
                val itemPackageName = declaration.packageName.asString()
                val itemClassName = declaration.toClassName()
                val interfaceName = itemClassName.simpleNames.joinToString("_")

                FileSpec.builder("$OutputPackagePrefix.$itemPackageName", interfaceName)
                    .addFileComment(
                        FormattedGeneratedMessage,
                        LocalDateTime.now().format(GeneratedDateTimeFormatter),
                    )
                    .addHiltModule(interfaceName, itemClassName, declaration.containingFile!!)
                    .build()
                    .writeTo(
                        codeGenerator = codeGenerator,
                        aggregating = false,
                    )
            }

        return emptyList()
    }

    private fun KSDeclarationContainer.getAllClassDeclarations(): Sequence<KSClassDeclaration> {
        return declarations.flatMap {
            if (it is KSClassDeclaration) it.getAllClassDeclarations() + it else sequenceOf()
        }
    }

    private fun FileSpec.Builder.addHiltModule(
        interfaceName: String,
        itemClassName: ClassName,
        itemClassFile: KSFile,
    ) = addType(
        TypeSpec.interfaceBuilder(interfaceName)
            .addAnnotation(DaggerModuleClassName)
            .addAnnotation(
                AnnotationSpec.builder(DaggerHiltInstallInClassName)
                    .addMember(TClassFormat, DaggerHiltSingletonComponentClassName)
                    .build(),
            )
            .addFunction(
                FunSpec.builder(EmptyClassName)
                    .addAnnotation(DaggerBindsClassName)
                    .addAnnotation(DaggerMultiBindingsIntoMapClassName)
                    .addAnnotation(
                        AnnotationSpec.builder(FlexibleHiltItemKeyClassName)
                            .addMember(TClassFormat, itemClassName)
                            .build(),
                    )
                    .addParameter(EmptyClassName, itemClassName)
                    .returns(FlexibleHiltItemClassName)
                    .addModifiers(KModifier.ABSTRACT)
                    .build(),
            )
            .addOriginatingKSFile(itemClassFile)
            .build(),
    )

    class Provider : SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            return DecoratorProcessor(environment.codeGenerator)
        }
    }
}
