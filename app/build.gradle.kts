import java.util.Properties

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jhkim.runningtracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jhkim.runningtracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }

    flavorDimensions += "environment"
    flavorDimensions += "map"
    productFlavors {
        // environment
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("staging") {
            dimension = "environment"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        create("prod") {
            dimension = "environment"
        }

        // map
        create("google") {
            dimension = "map"
            manifestPlaceholders["MAP_KEY_NAME"] = "com.naver.maps.map.NCP_KEY_ID"
            manifestPlaceholders["MAP_KEY_VALUE"] = localProperties.getProperty("NAVER_CLIENT_ID") ?: ""
        }
        create("naver") {
            dimension = "map"
            manifestPlaceholders["MAP_KEY_NAME"] = "com.naver.maps.map.NCP_KEY_ID"
            manifestPlaceholders["MAP_KEY_VALUE"] = localProperties.getProperty("NAVER_CLIENT_ID") ?: ""
        }
        create("noMap") {
            dimension = "map"
            manifestPlaceholders["MAP_KEY_NAME"] = "com.naver.maps.map.NCP_KEY_ID"
            manifestPlaceholders["MAP_KEY_VALUE"] = localProperties.getProperty("NAVER_CLIENT_ID") ?: ""
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.service)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // icon
    implementation(libs.androidx.compose.material.icons.extended)

    // naver map
    add("naverImplementation", "com.naver.maps:map-sdk:3.23.2")

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}