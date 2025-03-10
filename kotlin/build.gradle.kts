plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.slf4j:slf4j-api:2.0.12")
    testImplementation("org.slf4j:slf4j-log4j12:2.0.12")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    testImplementation("org.json:json:20240303")
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("de.mkammerer:argon2-jvm:2.11")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}