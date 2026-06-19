plugins {
    kotlin("jvm") version "2.4.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
    application
}

repositories {
    mavenCentral()
}

javafx {
    version = "21.0.2"
    modules = listOf("javafx.controls", "javafx.graphics")
}

application {
    mainClass.set("com.example.app3d.AppKt")
}

dependencies {
    implementation(kotlin("stdlib"))
}