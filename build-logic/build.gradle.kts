plugins {
    `kotlin-dsl`
}

group = "flexiblehilt.buildlogic"

dependencies {
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
