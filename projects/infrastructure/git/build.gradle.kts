plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class DependencyVersion(val version: String) {
    JGIT("6.8.0.202311291450-r"),
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:${DependencyVersion.JGIT.version}")
}
