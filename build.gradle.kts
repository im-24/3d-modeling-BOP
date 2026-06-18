plugins {
    kotlin("jvm") version "1.9.22"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

javafx {
    version = "17.0.6"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.base")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "SolidBOPViewerKt"
}

dependencies {
    implementation(kotlin("stdlib"))
}