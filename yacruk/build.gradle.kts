import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.githook.action
import dev.yaghm.plugin.internal.core.dsl.githook.doFirst
import dev.yaghm.plugin.internal.core.dsl.githook.doLast
import dev.yaghm.plugin.internal.core.dsl.githook.gradle
import dev.yaghm.plugin.internal.core.dsl.githook.preCommit
import dev.yaghm.plugin.internal.core.dsl.githook.useShebang

plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.yaghm)
    id("org.jmailen.kotlinter") version "4.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("org.jetbrains.dokka") version "1.9.20"
}

android {
    namespace = "dev.yacruk.io"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

yaghm {
    gitHook {
        preCommit {
            doFirst {
                gradle("lintKotlin")
            }
            doLast {
                gradle("detekt")
            }
            useShebang {
                Interpreter.BASH
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.foundation.android)

    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")
}