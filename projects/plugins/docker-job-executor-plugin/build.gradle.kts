plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins:job-executor-plugin-api"))

    implementation("com.github.docker-java:docker-java-transport-zerodep")
    implementation("com.github.docker-java:docker-java")
}
