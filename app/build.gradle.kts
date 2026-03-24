@file:Suppress("UnstableApiUsage")

import java.io.File
import java.util.Properties

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use(::load)
    }
}

fun localSigningProperty(name: String): String? {
    val localValue = localProperties.getProperty(name)?.takeIf(String::isNotBlank)
    if (localValue != null) return localValue
    val gradleValue = providers.gradleProperty(name).orNull?.takeIf(String::isNotBlank)
    if (gradleValue != null) return gradleValue
    return System.getenv(name.replace('.', '_').uppercase())?.takeIf(String::isNotBlank)
}

val sharedKeystorePath = localSigningProperty("hcx.keystore.file") ?: "/home/hcx/Codes/Android/hcx.jks"
val sharedKeystorePassword = localSigningProperty("hcx.keystore.password")
val sharedKeyAlias = localSigningProperty("hcx.key.alias")
val sharedKeyPassword = localSigningProperty("hcx.key.password")
val hasSharedKeystoreSigning = File(sharedKeystorePath).exists() &&
    !sharedKeystorePassword.isNullOrBlank() &&
    !sharedKeyAlias.isNullOrBlank() &&
    !sharedKeyPassword.isNullOrBlank()

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "moe.ice.systemtweaks"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    signingConfigs {
        create("localShared") {
            if (hasSharedKeystoreSigning) {
                storeFile = file(sharedKeystorePath)
                storePassword = sharedKeystorePassword
                keyAlias = sharedKeyAlias
                keyPassword = sharedKeyPassword
            } else {
                initWith(getByName("debug"))
            }
        }
    }

    defaultConfig {
        applicationId = "moe.ice.systemtweaks"
        minSdk = 35
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("localShared")
        }

        create("dev") {
            initWith(getByName("debug"))
            signingConfig = signingConfigs.getByName("localShared")
            isDebuggable = true
            versionNameSuffix = "-dev"
            matchingFallbacks += listOf("debug")
        }

        release {
            signingConfig = signingConfigs.getByName("localShared")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    androidResources {
        localeFilters += listOf("en", "zh-rCN")
    }
    packaging {
        resources {
            excludes += setOf(
                "DebugProbesKt.bin",
                "META-INF/androidx/**/LICENSE.txt",
            )
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.yukihook.api)
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)
    compileOnly(libs.xposed.api)
    ksp(libs.yukihook.ksp.xposed)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
