plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":projects:projects-api"))
    implementation(project(":projects-source-api"))
    implementation(project(":projects:projects-shared-kernel"))
    implementation(project(":plugins:source-plugin-api"))
    implementation(project(":infrastructure:message-broker-api"))
    implementation(project(":utilities"))

    implementation("org.springframework:spring-web")
    implementation("com.google.guava:guava")
    implementation("org.jgrapht:jgrapht-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("commons-io:commons-io")
}
