import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("jvm") version "1.8.20"
    id("application")
}

group = "io.github.dasheditor"
version = "0.1.0"

application {
    mainClass.set("io.github.dasheditor.AppMain")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val os: OperatingSystem = OperatingSystem.current()
val targetOs = when {
    os.isMacOsX -> "macos"
    os.isWindows -> "windows"
    os.isLinux -> "linux"
    else -> error("Unsupported OS: ${os.name}")
}
var targetArch = when (val arch = System.getProperty("os.arch")) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $arch")
}
val target = "${targetOs}-${targetArch}"

dependencies {
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:0.7.58")
}

kotlin {
    jvmToolchain(17)
}