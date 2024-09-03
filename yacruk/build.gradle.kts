import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.githook.doFirst
import dev.yaghm.plugin.internal.core.dsl.githook.doLast
import dev.yaghm.plugin.internal.core.dsl.githook.gradle
import dev.yaghm.plugin.internal.core.dsl.githook.preCommit
import dev.yaghm.plugin.internal.core.dsl.githook.useShebang
import java.util.Properties

plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.yaghm)
    id("org.jmailen.kotlinter") version "4.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("org.jetbrains.dokka") version "1.9.20"
    id("maven-publish")
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
    implementation(libs.androidx.material)
    implementation(libs.androidx.foundation)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.foundation.android)
    dokkaPlugin(libs.android.documentation.plugin)
    implementation(libs.rebugger)
    implementation(libs.kotlin.faker)
}

publishing {
    publications {
        create<MavenPublication>("release") {

            groupId = "io.github.andrew-malitchuk"
            artifactId = "yacruk"
            version = "0.0.1-a.3"

            pom {
                name.set("YACRUK")
                description.set("Designed to emulate the look and feel of classic digital screens")
                url.set("https://github.com/andrew-malitchuk/yet-another-compose-retro-ui-kit")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://spdx.org/licenses/MIT.html")
                    }
                }
                developers {
                    developer {
                        id.set("andrew-malitchuk")
                        name.set("Andrew Malitchuk")
                        email.set("andrew.malitchuk@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/andrew-malitchuk/yet-another-compose-retro-ui-kit.git")
                    developerConnection.set("scm:git:ssh://github.com/andrew-malitchuk/yet-another-compose-retro-ui-kit.git")
                    url.set("https://github.com/andrew-malitchuk/yet-another-compose-retro-ui-kit")
                }
            }
        }
    }

    repositories {
        maven {
            url =
                uri("https://maven.pkg.github.com/andrew-malitchuk/yet-another-compose-retro-ui-kit")
            credentials {
                username = getLocalProperty("username", project)
                password = getLocalProperty("githubToken", project)
            }
        }
    }
}

fun getLocalProperty(propertyName: String, project: Project): String {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    } else {
        throw GradleException("local.properties file not found!")
    }

    return properties.getProperty(propertyName)
        ?: throw GradleException("Property $propertyName not found in local.properties")
}