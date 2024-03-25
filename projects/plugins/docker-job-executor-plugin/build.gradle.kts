plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins:plugin-api"))

    implementation("com.github.docker-java:docker-java-transport-zerodep")
    implementation("com.github.docker-java:docker-java")
}
