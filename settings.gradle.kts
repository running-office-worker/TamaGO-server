pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "tamago-server"

include("tamago-core")
include("tamago-gateway")
include("tamago-notification")
include("tamago-oauth")
include("tamago-aws")
