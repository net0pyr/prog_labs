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
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        implementation(kotlin("stdlib"))
        implementation(group = "com.thoughtworks.xstream", name = "xstream", version = "1.4.15")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
        implementation("org.apache.logging.log4j:log4j-api:2.23.1")
        implementation("org.apache.logging.log4j:log4j-core:2.23.1")
        //implementation("postgresql:postgresql:9.1-901-1.jdbc4")
        implementation("com.jcraft:jsch:0.1.55")
        //implementation("com.squareup.sqldelight:jdbc-driver:1.5.5")
        implementation("org.postgresql:postgresql:42.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
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
