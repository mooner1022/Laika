import org.jetbrains.dokka.gradle.DokkaTask

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val atomicfu_version = "0.17.1"

plugins {
    `java-library`
    kotlin("jvm") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
    id("maven-publish")
}

group = "dev.mooner"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.6.10")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}