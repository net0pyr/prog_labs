plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.net0pyr"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {    jvmToolchain(21)
}