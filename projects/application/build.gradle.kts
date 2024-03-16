plugins {
    id("buildlogic.java-application-conventions")

    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"

    // id("org.graalvm.buildtools.native") version "0.9.18"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class DependencyVersion(val version: String) {
    BSON_4_JACKSON("2.13.1"),
    COMMONS_CODEC("1.15"),
    COMMONS_IO("2.15.1"),
    GUAVA("33.0.0-jre"),
    HIBERNATE_VALIDATOR("8.0.1.Final"),
    JGRAPHT_CORE("1.5.2"),
    JUNIT_PLATFORM_LAUNCHER("1.9.1"),
    MOCKSERVER_CLIENT_JAVA("5.15.0"),
    TOGGLZ_SPRING_BOOT_STARTER("4.4.0"),
}

dependencies {
    implementation(project(":plugins:plugin-api"))
    implementation(project(":projects-api"))
    implementation(project(":projects"))
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
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.google.guava:guava:${DependencyVersion.GUAVA.version}")
    implementation("commons-codec:commons-codec:${DependencyVersion.COMMONS_CODEC.version}")
    implementation("commons-io:commons-io:${DependencyVersion.COMMONS_IO.version}")
    implementation("de.undercouch:bson4jackson:${DependencyVersion.BSON_4_JACKSON.version}")
    implementation("org.hibernate.validator:hibernate-validator:${DependencyVersion.HIBERNATE_VALIDATOR.version}")
    implementation("org.jgrapht:jgrapht-core:${DependencyVersion.JGRAPHT_CORE.version}")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-web")
    implementation("org.togglz:togglz-spring-boot-starter:${DependencyVersion.TOGGLZ_SPRING_BOOT_STARTER.version}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.mock-server:mockserver-client-java:${DependencyVersion.MOCKSERVER_CLIENT_JAVA.version}")
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
