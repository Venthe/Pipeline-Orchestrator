import gradle.kotlin.dsl.accessors._f314d0e8863d95e0b70df46fc3aa0ac7.testRuntimeOnly

plugins {
    java
    id("io.freefair.lombok")
}

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


val versionsTestcontainers = "1.17.6"

enum class DependencyVersion(val version: String) {
    SPRING_FRAMEWORK("6.1.4"),
    TEST_CONTAINERS("1.17.6"),
    AWAITILITY("4.2.0"),
    SLF4J("2.0.12"),
    JACKSON("2.16.2"),
    JUNIT_PLATFORM_LAUNCHER("1.9.1"),
}

dependencies {
    constraints {
        implementation("com.fasterxml.jackson.core:jackson-databind:${DependencyVersion.JACKSON.version}")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${DependencyVersion.JACKSON.version}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersion.JACKSON.version}")
        implementation("org.springframework:spring-web:${DependencyVersion.SPRING_FRAMEWORK.version}")
        testImplementation("org.testcontainers:junit-jupiter:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:testcontainers:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:kafka:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:mockserver:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:mongodb:${DependencyVersion.TEST_CONTAINERS.version}")
        // TODO: Synchronize with application?
        implementation("org.springframework.boot:spring-boot-autoconfigure:3.2.3")
        // FIXME: Swap for logback
        testImplementation("org.slf4j:slf4j-simple:${DependencyVersion.SLF4J.version}")
    }
    implementation("org.springframework:spring-context:${DependencyVersion.SPRING_FRAMEWORK.version}")
    implementation("org.slf4j:slf4j-api:${DependencyVersion.SLF4J.version}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
    }
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.awaitility:awaitility:${DependencyVersion.AWAITILITY.version}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allprojects {
    layout.buildDirectory = File(rootProject.projectDir, "build/${project.name}")
}
