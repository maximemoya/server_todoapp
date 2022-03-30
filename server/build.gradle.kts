/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("gradle_project_start.kotlin-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":todoapp"))
    implementation ("org.http4k:http4k-core:4.25.5.1")
    implementation ("org.http4k:http4k-format-jackson:4.25.5.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    testImplementation ("org.http4k:http4k-testing-strikt:4.25.5.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation(kotlin("stdlib-jdk8"))
}

application {
    // Define the main class for the application.
    mainClass.set("gradle_project_start.app.AppKt")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
