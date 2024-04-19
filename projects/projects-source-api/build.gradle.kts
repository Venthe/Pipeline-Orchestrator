plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":projects:projects-api"))
    implementation(project(":projects:projects-shared-kernel"))
    implementation(project(":plugins:source-plugin-api"))
}
