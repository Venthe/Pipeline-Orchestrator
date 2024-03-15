plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class DependencyVersion(val version: String) {
    DOCKER_JAVA("3.3.6")
}

dependencies {
    implementation(project(":plugins:plugin-api"))
    implementation("org.springframework:spring-context")
    implementation("com.github.docker-java:docker-java-transport-zerodep:${DependencyVersion.DOCKER_JAVA.version}")
    implementation("com.github.docker-java:docker-java:${DependencyVersion.DOCKER_JAVA.version}")
}
