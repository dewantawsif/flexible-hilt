plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "me.tawsif.hilt.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.tawsif.hilt.sample"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.compose.compiler.get().version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.flexibleHiltCore)
    ksp(projects.flexibleHiltCompiler)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
}
