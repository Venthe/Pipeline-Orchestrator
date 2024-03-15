fun includeNestedProject(vararg projectNames: String, prefix: String = "projects") {
    projectNames.forEach {
        val sanitizedName = it.replace("/", ":")
        include(sanitizedName)
        project(":$sanitizedName").projectDir = file("${prefix}/${it}")
    }
}

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }

    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

rootProject.name = "orchestrator"

includeNestedProject(
        "application",
        "plugins/plugin-api",
        "plugins/docker-job-executor-plugin",
        "plugins/gerrit-source-plugin",
        "projects",
        "projects-source",
        "job-executor",
        "security",
        "shared-kernel",
        "task-scheduler",
        "utilities",
)
