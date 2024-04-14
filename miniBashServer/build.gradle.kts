plugins {
    kotlin("jvm") version "1.9.23"
    application
    kotlin("plugin.serialization") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.10"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

project(":server") {
    apply(plugin = "kotlinx-serialization")
    apply(plugin = "application")
    application {
        mainClass = "com.net0pyr.MainKt"
    }
    dependencies {
        implementation(kotlin("stdlib"))
        implementation(group = "com.thoughtworks.xstream", name = "xstream", version = "1.4.15")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
    }
    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }
    tasks.jar {
        manifest {
            attributes["Main-Class"] = "com.net0pyr.MainKt"
        }
    }
}

project(":client") {
    apply(plugin = "kotlinx-serialization")
    apply(plugin = "application")
    application {
        mainClass = "com.net0pyr.MainKt"
    }
    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

    tasks.jar {
        manifest {
            attributes["Main-Class"] = "com.net0pyr.MainKt"
        }
    }
}

project(":client2") {
    apply(plugin = "kotlinx-serialization")
    apply(plugin = "application")
    application {
        mainClass = "com.net0pyr.MainKt"
    }
    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

    tasks.jar {
        manifest {
            attributes["Main-Class"] = "com.net0pyr.MainKt"
        }
    }
}