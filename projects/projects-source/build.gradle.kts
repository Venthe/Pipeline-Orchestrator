plugins {
    id("buildlogic.java-library-conventions")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins-plugin-api"))
    implementation(project(":shared-kernel"))
    implementation(project(":projects-projects-api"))
    implementation(project(":projects-source-api"))
    implementation(project(":infrastructure-message-broker-api"))
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation(project(":plugins-plugin-api-test"))
}
