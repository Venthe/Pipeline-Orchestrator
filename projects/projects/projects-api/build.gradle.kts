plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":projects:shared-kernel"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.google.guava:guava")
}
