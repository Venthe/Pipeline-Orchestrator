plugins {
    id("buildlogic.java-application-conventions")

    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"

    // id("org.graalvm.buildtools.native") version "0.9.18"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins:plugin-api"))
    implementation(project(":projects:projects-api"))
    implementation(project(":projects:projects"))
    implementation(project(":projects-source-api"))
    implementation(project(":projects-source"))
    implementation(project(":shared-kernel"))
    implementation(project(":infrastructure:in-memory-repository"))
    implementation(project(":infrastructure:in-memory-message-broker"))
    implementation(project(":infrastructure:message-broker-api"))
    implementation(project(":shared-kernel"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.google.guava:guava")
    implementation("commons-codec:commons-codec")
    implementation("de.undercouch:bson4jackson")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-web")
    implementation("org.togglz:togglz-spring-boot-starter")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")

    // implementation("com.google.code.findbugs:jsr305:3.0.2")
    // implementation("io.vertx:vertx-json-schema:4.5.1")
    // implementation("javax.validation:validation-api:2.0.1.Final")
    // implementation("org.jetbrains:annotations:23.0.0")
    // implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    // implementation("org.springframework.kafka:spring-kafka")
    // testImplementation("org.springframework.kafka:spring-kafka-test")
}
