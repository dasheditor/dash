plugins {
    id("application")
}

application {
    mainClass.set("io.github.dasheditor.Bootstrap")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":compiler"))
}