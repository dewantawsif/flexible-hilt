plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "dagger.hilt.flexible"
    compileSdk = 34

    defaultConfig {
        minSdk = 1
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
