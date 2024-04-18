plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":plugins:source-plugin-api"))

    implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
}
