plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class DependencyVersion(val version: String) {
    JGRAPHT_CORE("1.5.2"),
}

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":projects-api"))
    implementation(project(":plugins:plugin-api"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.jgrapht:jgrapht-core:${DependencyVersion.JGRAPHT_CORE.version}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
}
