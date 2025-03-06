import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.intelliJPlatform)
}

repositories {
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("251.23774.16")

        pluginVerifier()
        zipSigner()

        bundledModule("intellij.platform.jewel.foundation")
        bundledModule("intellij.platform.jewel.ui")
        bundledModule("intellij.platform.jewel.ideLafBridge")
        bundledModule("intellij.libraries.compose.foundation.desktop")
        bundledModule("intellij.libraries.skiko")
    }

    implementation(projects.composeApp) {
        // We need to explicitly exclude all deps that would be on the classpath in IJP already,
        // although a better option would be to extract a common module with compileOnly deps,
        // and two separate frontends (ijplugin and standalone)
        exclude("androidx.annotation")
        exclude("androidx.arch.core")
        exclude("androidx.compose")
        exclude("androidx.lifecycle")
        exclude("org.jetbrains.compose")
        exclude("org.jetbrains.compose.foundation")
        exclude("org.jetbrains.compose.runtime")
        exclude("org.jetbrains.compose.ui")
        exclude("org.jetbrains.jewel")
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.jetbrains.skiko")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes =
            """
            |Initial version
            """.trimMargin()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    withType<KotlinCompile> {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }
}
