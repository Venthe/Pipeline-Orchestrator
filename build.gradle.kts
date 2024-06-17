plugins {
    id("buildlogic.java-application-conventions")

    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("eu.venthe.gradle.generate_openapi_client") version "internal"

    // id("org.graalvm.buildtools.native") version "0.9.18"

    `jvm-test-suite`
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.github.docker-java:docker-java")
    implementation("com.github.docker-java:docker-java-transport-zerodep")
    implementation("com.google.guava:guava")
    implementation("commons-io:commons-io")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.swagger.core.v3:swagger-models")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.eclipse.jgit:org.eclipse.jgit")
    implementation("org.jgrapht:jgrapht-core")
    implementation("org.openapitools:jackson-databind-nullable")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework:spring-web")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("javax.json:javax.json-api:1.1.4")
    testImplementation("org.glassfish:javax.json:1.1.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")

    // Web API
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Security
    //implementation("org.springframework.boot:spring-boot-starter-security")
    //implementation("org.togglz:togglz-spring-security")

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

openApiGenerate {
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    templateDir.set("$projectDir/src/main/resources/template/gerrit/")
    inputSpec.set("$projectDir/src/main/resources/schemas/gerrit.openapi.yaml")
    validateSpec = true
    targetPackage = "eu.venthe.pipeline.gerrit"
}

java.sourceSets["main"].java {
    srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
}
