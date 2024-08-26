import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.protobuf)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlinx.serialization)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "me.ks.chan.pica.plus"
    compileSdk = 35

    defaultConfig {
        applicationId = namespace
        minSdk = 21
        targetSdk = compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
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

    kotlin {
        jvmToolchain(17)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
        sourceSets.all {
            languageSettings.enableLanguageFeature("ExplicitBackingFields")
            languageSettings.enableLanguageFeature("ContextReceivers")
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        languageVersion = "2.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

protobuf {
    protoc {
        artifact = libs.google.protobuf.compiler.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by creating {
                    option("lite")
                }
                id("kotlin")
            }
        }
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    api(libs.bundles.app)
    ksp(libs.bundles.ksp)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.debug)
}