plugins {
    kotlin("jvm") version "1.7.10"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

application {
    mainClass.set("io.github.dasheditor.Dash")
}

group = "io.github.dasheditor"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.ow2.asm:asm:9.3")
}

javafx {
    version = "12"
    modules("javafx.controls")
}