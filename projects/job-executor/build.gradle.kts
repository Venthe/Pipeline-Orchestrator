plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":job-executor-api"))
    implementation(project(":plugins:job-executor-plugin-api"))
}
