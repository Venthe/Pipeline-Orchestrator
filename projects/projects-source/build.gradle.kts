plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins:source-plugin-api"))
    implementation(project(":shared-kernel"))
    implementation(project(":security"))
    implementation(project(":projects:projects-api"))
    implementation(project(":projects-source-api"))
    implementation(project(":infrastructure:message-broker-api"))
    testImplementation(project(":plugins:plugin-api-test"))

    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}
