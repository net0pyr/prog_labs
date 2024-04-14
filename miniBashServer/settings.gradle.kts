plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "miniBashServer"
include("client")
include("server")
include("common")
include("client2")
