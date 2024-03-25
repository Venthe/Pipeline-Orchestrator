fun includeNestedProject(vararg projectNames: String, prefix: String = "projects") {
    projectNames.forEach {
        val sanitizedName = it.replace("/", ":")
        val projectDirectory = file("${prefix}/${it}")
        val fullProjectName = ":$sanitizedName"

        include(sanitizedName)
        project(fullProjectName).projectDir = projectDirectory
    }
}

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }

    includeBuild("plugins/json-schema-to-pojo")
    includeBuild("plugins/generate-openapi-client")
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

rootProject.name = "orchestrator"

includeNestedProject(
        "application",
        "infrastructure/git",
        "infrastructure/in-memory-message-broker",
        "infrastructure/in-memory-repository",
        "infrastructure/message-broker-api",
        "job-executor",
        "job-executor-api",
        "plugins/docker-job-executor-plugin",
        "plugins/gerrit-source-plugin",
        "plugins/jenkins-job-executor-plugin",
        "plugins/plugin-api",
        "plugins/plugin-api-test",
        "plugins/test-job-executor-plugin",
        "projects-source",
        "projects-source-api",
        "projects/projects",
        "projects/projects-api",
        // FIXME: https://github.com/gradle/gradle/issues/847
        "projects/projects-shared-kernel",
        "projects/workflow-executions",
        "projects/workflow-executions-api",
        "security",
        "shared-kernel",
        "task-scheduler",
        "task-scheduler-api",
        "utilities",
)
