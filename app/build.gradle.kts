/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.11.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls")
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)

    // Add the required JavaFX modules from Maven Central
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-graphics:21")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Define the main class for the application.
    mainClass = "HelloFX"
}

tasks.named<JavaExec>("run") {
    notCompatibleWithConfigurationCache("JavaExec tasks are not fully compatible with Gradle's configuration cache.")
}

tasks.register<JavaExec>("runApp") {
    //group = "application"
    description = "Runs the JavaFX application."
    mainClass.set("HelloFX") // Replace with your main class
    classpath = sourceSets["main"].runtimeClasspath

    // Add JavaFX runtime arguments
    jvmArgs = listOf(
        "--module-path", configurations.runtimeClasspath.get().asPath,
        "--add-modules", "javafx.controls,javafx.graphics"
    )
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

// Make `runApp` the default task
defaultTasks("runApp")
