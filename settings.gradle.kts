// TODO: Gradle enforcer plugin

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }

    includeBuild("plugins/generate-openapi-client")
//    includeBuild("generate-openapi-client")
//    project(":generate-openapi-client").projectDir = file("plugins/generate-openapi-client")
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

rootProject.name = "orchestrator"
