rootProject.name = "gerrit_mediator"
pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.3"
    }
}
plugins {
    id("de.fayard.refreshVersions")
}