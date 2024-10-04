plugins {
    java

    id("io.freefair.lombok") version "8.6"
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("eu.venthe.gradle.generate_openapi_client") version "internal"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"


enum class DependencyVersion(val version: String) {
    ASSERTJ("3.25.3"),
    AWAITILITY("4.2.0"),
    COMMONS_TEXT("1.11.0"),
    JACKSON("2.17.0"),
    JUNIT("5.10.2"),
    GUAVA("33.0.0-jre"),
    JUNIT_PLATFORM_LAUNCHER("1.9.1"),
    COMMONS_IO("2.15.1"),
    SLF4J("2.0.12"),
    SPRING_BOOT("3.2.3"),
    JGIT("6.8.0.202311291450-r"),
    SPRING_FRAMEWORK("6.1.4"),
    TEST_CONTAINERS("1.17.6"),
    SWAGGER("2.2.20"),
    JACKSON_DATABIND_NULLABLE("0.2.6"),
    JAKARTA_ANNOTATION_API("3.0.0-M1"),
    JGRAPHT_CORE("1.5.2"),
    BSON_4_JACKSON("2.13.1"),
    COMMONS_CODEC("1.15"),
    HIBERNATE_VALIDATOR("8.0.1.Final"),
    MOCKSERVER_CLIENT_JAVA("5.15.0"),
    TOGGLZ_SPRING_BOOT_STARTER("4.4.0"),
    DOCKER_JAVA("3.3.6")
}

dependencies {

    constraints {
        implementation("com.fasterxml.jackson.core:jackson-databind:${DependencyVersion.JACKSON.version}")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${DependencyVersion.JACKSON.version}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersion.JACKSON.version}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${DependencyVersion.JACKSON.version}")
        implementation("com.github.docker-java:docker-java-transport-zerodep:${DependencyVersion.DOCKER_JAVA.version}")
        implementation("com.github.docker-java:docker-java:${DependencyVersion.DOCKER_JAVA.version}")
        implementation("com.google.guava:guava:${DependencyVersion.GUAVA.version}")
        implementation("commons-codec:commons-codec:${DependencyVersion.COMMONS_CODEC.version}")
        implementation("commons-io:commons-io:${DependencyVersion.COMMONS_IO.version}")
        implementation("de.undercouch:bson4jackson:${DependencyVersion.BSON_4_JACKSON.version}")
        implementation("io.swagger.core.v3:swagger-annotations:${DependencyVersion.SWAGGER.version}")
        implementation("io.swagger.core.v3:swagger-models:${DependencyVersion.SWAGGER.version}")
        implementation("jakarta.annotation:jakarta.annotation-api:${DependencyVersion.JAKARTA_ANNOTATION_API.version}")
        implementation("org.apache.commons:commons-text:${DependencyVersion.COMMONS_TEXT.version}")
        implementation("org.eclipse.jgit:org.eclipse.jgit:${DependencyVersion.JGIT.version}")
        implementation("org.hibernate.validator:hibernate-validator:${DependencyVersion.HIBERNATE_VALIDATOR.version}")
        implementation("org.jgrapht:jgrapht-core:${DependencyVersion.JGRAPHT_CORE.version}")
        implementation("org.openapitools:jackson-databind-nullable:${DependencyVersion.JACKSON_DATABIND_NULLABLE.version}")
        implementation("org.slf4j:slf4j-api:${DependencyVersion.SLF4J.version}")
        implementation("org.springframework.boot:spring-boot-autoconfigure:${DependencyVersion.SPRING_BOOT.version}") // TODO: Synchronize with application?
        implementation("org.springframework.boot:spring-boot-configuration-processor:${DependencyVersion.SPRING_BOOT.version}")
        implementation("org.springframework:spring-context:${DependencyVersion.SPRING_FRAMEWORK.version}")
        implementation("org.springframework:spring-web:${DependencyVersion.SPRING_FRAMEWORK.version}")
        implementation("org.togglz:togglz-spring-boot-starter:${DependencyVersion.TOGGLZ_SPRING_BOOT_STARTER.version}")
        implementation("org.togglz:togglz-spring-security:${DependencyVersion.TOGGLZ_SPRING_BOOT_STARTER.version}")
        testImplementation("org.assertj:assertj-core:${DependencyVersion.ASSERTJ.version}")
        testImplementation("org.awaitility:awaitility:${DependencyVersion.AWAITILITY.version}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${DependencyVersion.JUNIT.version}")
        testImplementation("org.junit.jupiter:junit-jupiter-params:${DependencyVersion.JUNIT.version}")
        testImplementation("org.mock-server:mockserver-client-java:${DependencyVersion.MOCKSERVER_CLIENT_JAVA.version}")
        implementation("org.mock-server:mockserver-client-java:${DependencyVersion.MOCKSERVER_CLIENT_JAVA.version}")
        testImplementation("org.slf4j:slf4j-simple:${DependencyVersion.SLF4J.version}") // FIXME: Swap for logback
        testImplementation("org.testcontainers:junit-jupiter:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:kafka:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:mockserver:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:mongodb:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.testcontainers:testcontainers:${DependencyVersion.TEST_CONTAINERS.version}")
        testImplementation("org.mockito:mockito-core:5.11.0")
        implementation("com.github.f4b6a3:uuid-creator:5.3.3")
    }

    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-context")
    implementation("org.apache.commons:commons-text")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.awaitility:awaitility")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-core")
    testImplementation(platform("org.junit:junit-bom:${DependencyVersion.JUNIT.version}"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
    }
    implementation("com.github.f4b6a3:uuid-creator")
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
    testImplementation("com.tngtech.archunit:archunit:1.3.0")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")

}

openApiGenerate {
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    templateDir.set("$projectDir/src/main/resources/template/gerrit/")
    inputSpec.set("$projectDir/src/main/resources/schemas/gerrit.openapi.yaml")
    validateSpec = true
    targetPackage = "eu.venthe.platform.gerrit"

    generatorName = "java"

    configOptions = mapOf(
        "useJakartaEe" to "true",
        "library" to "resttemplate"
    )
}

java.sourceSets["main"].java {
    srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
}

allprojects {
    val path = project.path.replace("^:".toRegex(), "").replace(":", "/")
    val file = File(rootProject.projectDir, "build/$path")
    layout.buildDirectory = file

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
