// TODO: Gradle enforcer plugin

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }

    includeBuild("plugins/generate-openapi-client")
}

rootProject.name = "orchestrator"
