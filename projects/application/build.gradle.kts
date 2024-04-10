plugins {
    id("buildlogic.java-application-conventions")

    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"

    // id("org.graalvm.buildtools.native") version "0.9.18"

    `jvm-test-suite`
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    // Project modules
    implementation(project(":plugins:plugin-api"))
    implementation(project(":plugins:gerrit-source-plugin"))
    implementation(project(":projects:projects-api"))
    implementation(project(":projects:projects"))
    implementation(project(":projects-source-api"))
    implementation(project(":projects:projects-shared-kernel"))
    implementation(project(":projects-source"))
    implementation(project(":plugins:plugin-api-test"))
    implementation(project(":shared-kernel"))
    implementation(project(":infrastructure:in-memory-repository"))
    implementation(project(":infrastructure:in-memory-message-broker"))
    implementation(project(":infrastructure:message-broker-api"))
    implementation(project(":shared-kernel"))

    // Web API
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.togglz:togglz-spring-security")

    // Libraries
    implementation("com.google.guava:guava")
    implementation("commons-codec:commons-codec")
    implementation("org.togglz:togglz-spring-boot-starter")

    // Development
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Persistence
    implementation("de.undercouch:bson4jackson")
    implementation("org.hibernate.validator:hibernate-validator")
    // implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Observability
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // implementation("org.springframework.kafka:spring-kafka")

    // Static analysis
    // implementation("com.google.code.findbugs:jsr305:3.0.2")
    // implementation("io.vertx:vertx-json-schema:4.5.1")
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by getting(JvmTestSuite::class) { useJUnitJupiter() }

        register<JvmTestSuite>("integrationTest") {
            useJUnitJupiter()

            dependencies {
                implementation(platform("io.cucumber:cucumber-bom:7.16.1"))
                implementation(project())
                implementation(project(":application"))
                implementation("org.springframework:spring-web")
                implementation("io.cucumber:cucumber-java") {}
                implementation("io.cucumber:cucumber-junit")
                implementation("io.cucumber:cucumber-spring")
                implementation("io.cucumber:cucumber-junit-platform-engine")
                implementation("org.junit.platform:junit-platform-suite")
                implementation("org.assertj:assertj-core")
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("org.slf4j:slf4j-api")
                //implementation("org.mock-server:mockserver-client-java:5.15.0")
                implementation("org.testcontainers:testcontainers")
                implementation("org.testcontainers:junit-jupiter")
                implementation("com.fasterxml.jackson.core:jackson-databind")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
                implementation("com.github.dasniko:testcontainers-keycloak:3.3.0")
                // implementation("org.springframework.kafka:spring-kafka-test")// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
                implementation("io.jsonwebtoken:jjwt-api:0.12.5")// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
                runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
                implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
            }

            targets {
                all {
                    testTask.configure {
                        useJUnitPlatform()
                        shouldRunAfter(test)
                        systemProperty("cucumber.junit-platform.naming-strategy", "long")
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn("integrationTest")
    shouldRunAfter("integrationTest")
}
