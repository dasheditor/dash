plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "io.github.dasheditor"
version = "0.1.0"

application {
    mainClass.set("io.github.dasheditor.main.Dash")
    mainModule.set("dash.main")
}

repositories {
    mavenCentral()
}

dependencies {
}

javafx {
    version = "17.0.1"
    modules = mutableListOf("javafx.controls")
}